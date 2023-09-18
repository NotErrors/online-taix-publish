package com.yuren.serviceprice.controller;


import com.yuren.internalcommon.dto.PriceRule;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 拉丁
 * @since 2023-09-14
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Autowired
    private PriceRuleService priceRuleService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule) {
        return priceRuleService.add(priceRule);
    }

    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRule priceRule) {
        return priceRuleService.edit(priceRule);
    }

    @GetMapping("/get-newest-version")
    public ResponseResult getNewestVersion(@RequestParam String fareType) {
        return priceRuleService.getNewestVersion(fareType);
    }

    @GetMapping("/is-new")
    public ResponseResult isNew(@RequestParam String fareType, @RequestParam Integer fareVersion) {
        try {
            fareType = URLDecoder.decode(fareType, "UTF-8");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return priceRuleService.isNew(fareType, fareVersion);
    }

    @GetMapping("/is-exists")
    public ResponseResult ifExists(@RequestBody PriceRule priceRule) {
        return priceRuleService.ifExists(priceRule);
    }

}
