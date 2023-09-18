package com.yuren.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuren.internalcommon.constant.CommonConstant;
import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.dto.PriceRule;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 拉丁
 * @since 2023-09-14
 */
@Service
public class PriceRuleService{

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult add(PriceRule priceRule) {

        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + "$" + vehicleType;
        priceRule.setFareType(fareType);

        QueryWrapper<PriceRule> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", cityCode);
        wrapper.eq("vehicle_type", vehicleType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        Integer fareVersion = 0;
        if (priceRules != null && !priceRules.isEmpty()) {
            return ResponseResult.fail(CommonStatusConstant.PRICE_RULE_EXISTS.getCode(), CommonStatusConstant.PRICE_RULE_EXISTS.getMessage());
        }

        priceRule.setFareVersion(fareVersion);
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

    public ResponseResult edit(PriceRule priceRule) {

        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fareType = cityCode + vehicleType;
        priceRule.setFareType(fareType);

        QueryWrapper<PriceRule> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", cityCode);
        wrapper.eq("vehicle_type", vehicleType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        Integer fareVersion = 0;
        if (priceRules != null && !priceRules.isEmpty()) {
            PriceRule lasterPriceRule = priceRules.get(0);
            Double lasteerStartFare = lasterPriceRule.getStartFare();
            Integer lasterStartMile = lasterPriceRule.getStartMile();
            Double lasterUnitPricePerMile = lasterPriceRule.getUnitPricePerMile();
            Double lasterUnitPricePerMinute = lasterPriceRule.getUnitPricePerMinute();

            if (lasteerStartFare.doubleValue() == priceRule.getStartFare().doubleValue()
            && lasterStartMile.intValue() == priceRule.getStartMile().intValue()
            && lasterUnitPricePerMile.doubleValue() == priceRule.getUnitPricePerMile().doubleValue()
            && lasterUnitPricePerMinute.doubleValue() == priceRule.getUnitPricePerMinute().doubleValue()) {
                return ResponseResult.fail(CommonStatusConstant.PRICE_RULE_NOT_EDIT.getCode(), CommonStatusConstant.PRICE_RULE_NOT_EDIT.getMessage());
            }

            fareVersion = lasterPriceRule.getFareVersion();
            fareVersion++;
        } else {
            return ResponseResult.fail(CommonStatusConstant.PRICE_RULE_EXIST.getCode(), CommonStatusConstant.PRICE_RULE_EXIST.getMessage());
        }

        priceRule.setFareVersion(fareVersion);
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

    public ResponseResult<PriceRule> getNewestVersion(String fareType) {
        QueryWrapper<PriceRule> wrapper = new QueryWrapper();
        wrapper.eq("fare_type", fareType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        if (priceRules == null || priceRules.isEmpty()) {
            return ResponseResult.fail(CommonStatusConstant.PRICE_RULE_EXIST.getCode(), CommonStatusConstant.PRICE_RULE_EXIST.getMessage());
        }
        PriceRule priceRule = priceRules.get(0);
        return ResponseResult.success(priceRule);
    }

    public ResponseResult<Boolean> isNew(String fareType, int fareVersion) {
        ResponseResult<PriceRule> responseResult = getNewestVersion(fareType);
        if (Objects.isNull(responseResult.getData())) {
//            return ResponseResult.fail("计价规则不存在");
            return ResponseResult.success(false);
        }
        PriceRule priceRule = responseResult.getData();
        Integer fareVersionDB = priceRule.getFareVersion();
        if (fareVersionDB == fareVersion) {
            return ResponseResult.success(true);
        }
        return ResponseResult.success(false);
    }

    public ResponseResult<Boolean> ifExists(PriceRule priceRule) {
        QueryWrapper<PriceRule> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", priceRule.getCityCode());
        wrapper.eq("vehicle_type", priceRule.getVehicleType());

        Integer count = priceRuleMapper.selectCount(wrapper);
        if (count > 0) {
            return ResponseResult.success(true);
        }
        return ResponseResult.success(false);
    }
}
