package com.yuren.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.constant.OrderConstants;
import com.yuren.internalcommon.dto.OrderInfo;
import com.yuren.internalcommon.dto.PriceRule;
import com.yuren.internalcommon.request.OrderRequest;
import com.yuren.internalcommon.response.OrderDriverInfoResponse;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TerminalResponse;
import com.yuren.internalcommon.util.RedisPrefixUtils;
import com.yuren.serviceorder.client.ServiceDriverUserClient;
import com.yuren.serviceorder.client.ServiceMapClient;
import com.yuren.serviceorder.client.ServicePriceClient;
import com.yuren.serviceorder.mapper.OrderInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 拉丁
 * @since 2023-09-14
 */
@Service
@Slf4j
public class OrderInfoService{

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult add(OrderRequest orderRequest) {
        // 判断当前地区是否有提供服务
        if (!isAddressServiceSupplier(orderRequest.getFareType())) {
            return ResponseResult.fail(CommonStatusConstant.CITY_SERVICE_NOT_SERVICE.getCode(), CommonStatusConstant.CITY_SERVICE_NOT_SERVICE.getMessage());
        }

        // 判断当前城市是否有可用的司机
        if (!isAlailableDriver(orderRequest.getFareType())) {
            return ResponseResult.fail(CommonStatusConstant.CITY_DRIVER_EMPTY.getCode(), CommonStatusConstant.CITY_DRIVER_EMPTY.getMessage());
        }

        // 判断当前计价规则是否是最新的
        ResponseResult<Boolean> responseResult = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        if (Objects.isNull(responseResult.getData()) || !responseResult.getData()) {
            return ResponseResult.fail(CommonStatusConstant.PRICE_RULE_CHANGE.getCode(), CommonStatusConstant.PRICE_RULE_CHANGE.getMessage());
        }

        // 判断乘客当前是否有其他订单
        if (isPassengerOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusConstant.ORDER_GOING_ON.getCode(), CommonStatusConstant.ORDER_GOING_ON.getMessage());
        }
        // 检验设备是否是黑名单
        if (isBlackDevice(orderRequest.getDeviceCode())) {
            return ResponseResult.fail(CommonStatusConstant.DEVICE_IS_BLACK.getCode(), CommonStatusConstant.DEVICE_IS_BLACK.getMessage());
        }

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);

        orderInfo.setOrderStatus(OrderConstants.ORDER_START);
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderInfoMapper.insert(orderInfo);
        dispatchRealTimeOrder(orderInfo);
        return ResponseResult.success();
    }

    /**
     * 在用户发起订单的返回内查找司机。2km，4km，5km
     * @param orderInfo
     */
    public void dispatchRealTimeOrder(OrderInfo orderInfo) {
        String depLatitude = orderInfo.getDepLatitude();
        String depLongitude = orderInfo.getDepLongitude();
        String center = depLatitude + "," + depLongitude;

        List<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);
        ResponseResult<List<TerminalResponse>> listResponseResult = null;

        Radius:
        for (Integer radius : radiusList) {
            log.info("获取车辆终端信息");
            listResponseResult = serviceMapClient.teerminalAroundsearch(center, radius);
            if (listResponseResult.getData() != null && !listResponseResult.getData().isEmpty()) {
                log.info("可用车辆的信息", listResponseResult.getData().get(0).toString());
                List<TerminalResponse> resultData = listResponseResult.getData();
                log.info("解析终端信息");
                for (TerminalResponse terminal : resultData) {
                    Long carId = terminal.getCarId();
                    log.info("获取司机信息");
                    ResponseResult<OrderDriverInfoResponse> avaliableDriver = serviceDriverUserClient.getAvaliableDriver(carId);
                    if (avaliableDriver.getCode() == CommonStatusConstant.DRIVER_NOT_EXISTS.getCode()) {
                        continue;
                    }else {
                        OrderDriverInfoResponse data = avaliableDriver.getData();
                        Long driverId = data.getDriverId();

                        if (isDriverOrderGoingOn(driverId) > 0) {
                            continue;
                        }

                        break Radius;
                    }
                }
            }
        }
        log.info("获取车辆信息");

        log.info("获取可用车辆");
    }

    private boolean isAlailableDriver(String fareType) {
        int index = fareType.indexOf("$");
        String cityCode = fareType.substring(0, index);

        ResponseResult<Boolean> responseResult = serviceDriverUserClient.isAlailableDriver(cityCode);
        return responseResult.getData();
    }

    /**
     * 设备黑名单校验
     * @param deviceCode
     * @return
     */
    private boolean isBlackDevice(String deviceCode) {
        String deviceCodeKey =RedisPrefixUtils.generatorDeviceCodeKey(deviceCode);
        Boolean hasKey = stringRedisTemplate.hasKey(deviceCodeKey);
        if (hasKey) {
            String value = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            int count = Integer.parseInt(value);
            if (count >= 2) {
                return true;
            }
            stringRedisTemplate.opsForValue().increment(deviceCodeKey);
        } else {
            stringRedisTemplate.opsForValue().setIfAbsent(deviceCodeKey, "1", 1, TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 返回乘客当前正在进行中的订单数
     * @return
     */
    private int isPassengerOrderGoingOn(Long passengerId) {
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("passenger_id", passengerId);
        wrapper.notIn("order_status", OrderConstants.ORDER_INVALID,OrderConstants.SUCCESS_PAY, OrderConstants.ORDER_CANCEL);

        Integer count = orderInfoMapper.selectCount(wrapper);
        return count;
    }

    /**
     * 返回司机当前正在进行中的订单数
     * @return
     */
    private int isDriverOrderGoingOn(Long driverId) {
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("driver_id", driverId);
        wrapper.in("order_status", OrderConstants.DRIVER_RECEIVE_RECORD,OrderConstants.DRIVER_TO_PICK_UP_PASSENGER,
                OrderConstants.DRIVER_ARRIVED_DEPARTURE, OrderConstants.PICK_UP_PASSENGER);

        Integer count = orderInfoMapper.selectCount(wrapper);
        return count;
    }

    private boolean isAddressServiceSupplier(String fareType) {
        int index = fareType.indexOf("$");
        String cityCode = fareType.substring(0, index);
        String vehicleType = fareType.substring(index + 1);

        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);

        ResponseResult<Boolean> responseResult = servicePriceClient.ifExists(priceRule);
        return responseResult.getData();

    }
}
