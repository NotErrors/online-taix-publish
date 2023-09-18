package com.yuren.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.dto.PriceRule;
import com.yuren.internalcommon.request.ForecastPriceDTO;
import com.yuren.internalcommon.response.DirectionResponse;
import com.yuren.internalcommon.response.PriceResponse;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.util.BigDecimalUtils;
import com.yuren.serviceprice.mapper.PriceRuleMapper;
import com.yuren.serviceprice.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-22:54
 **/
@Service
public class ForecastPriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    /**
     * 计算起点到终点的经纬度
     * @param depLatitude 起点纬度
     * @param depLongitude 起点经度
     * @param destLatitude 终点纬度
     * @param destLongitude 终点经度
     * @return
     */
    public ResponseResult<PriceResponse> forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude, String cityCode, String vehicleType) {

        // 重新封装ForecastPriceDTO，是因为参数可能这中途需要进行计算等其他原因
        // 故controller层将参数进行拆分，然后在要请求时进行封装
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        forecastPriceDTO.setCityCode(cityCode);
        forecastPriceDTO.setVehicleType(vehicleType);
        ResponseResult<DirectionResponse> responseResult = serviceMapClient.driving(forecastPriceDTO);
        DirectionResponse data = responseResult.getData();
        // 获取计价规则
//        Map<String,Object> queryMap = new HashMap<>();
//        queryMap.put("city_code", null);
//        queryMap.put("vehicle_type", null);

        QueryWrapper<PriceRule> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", cityCode);
        wrapper.eq("vehicle_type", vehicleType);
        wrapper.orderByDesc("fare_version");
        List<PriceRule> priceRuleList = priceRuleMapper.selectList(wrapper);
        if (priceRuleList.isEmpty()) {
            return ResponseResult.fail(CommonStatusConstant.PRICE_RULE_EXIST.getCode(), CommonStatusConstant.PRICE_RULE_EXIST.getMessage());
        }
        PriceRule priceRule = priceRuleList.get(0);
        double price = getPrice(data.getDistance(), data.getDuration(), priceRule);

        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(price);
        priceResponse.setCityCode(cityCode);
        priceResponse.setVehicleType(vehicleType);
        priceResponse.setFareType(priceRule.getFareType());
        priceResponse.setFareVersion(priceResponse.getFareVersion());
        return ResponseResult.success(priceResponse);
    }

    /**
     * 通过距离和时长以及计价规则，获取该路程的总价格
     * @param distance
     * @param duration
     * @param priceRule
     * @return
     */
    private static double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        double price = 0;

        // 起步价
        price = BigDecimalUtils.add(price, priceRule.getStartFare());

        // 里程价
        double distanceMile = BigDecimalUtils.divide(distance, 1000);

        //起步距离
        double distanceSubtract = BigDecimalUtils.subtract(distanceMile, priceRule.getStartMile());
        distanceSubtract = distanceSubtract <= 0 ? 0 : distanceSubtract;

        price = BigDecimalUtils.add(price, BigDecimalUtils.multiply(distanceSubtract, priceRule.getUnitPricePerMile()));

        // 时长价
        // 时间的换算，秒换分钟
        double minute = BigDecimalUtils.divide(duration, 60);
        price = BigDecimalUtils.add(price, BigDecimalUtils.multiply(priceRule.getUnitPricePerMinute(),minute ));

        BigDecimal priceDemical = new BigDecimal(price);
        priceDemical = priceDemical.setScale(2, RoundingMode.HALF_UP);
        return priceDemical.doubleValue();
    }

    public static void main(String[] args) {
        PriceRule priceRule = new PriceRule();
        priceRule.setUnitPricePerMile(1.8);
        priceRule.setUnitPricePerMinute(0.5);
        priceRule.setStartFare(10.0);
        priceRule.setStartMile(3);
        System.out.println(getPrice(6500, 1800, priceRule));
    }

}
