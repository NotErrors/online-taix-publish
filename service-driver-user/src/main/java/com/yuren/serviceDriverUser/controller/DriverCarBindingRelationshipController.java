package com.yuren.serviceDriverUser.controller;


import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.dto.DriverCarBindingRelationship;
import com.yuren.serviceDriverUser.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 拉丁
 * @since 2023-09-09
 */
//@RestController(value = "/driver-car-binding-relationship")
@RestController
@RequestMapping(("/driver-car-binding-relationship"))
public class DriverCarBindingRelationshipController {

    @Autowired
    private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }

    @PostMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
        return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);
    }
}