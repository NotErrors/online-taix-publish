package com.yuren.serviceDriverUser.controller;


import com.yuren.internalcommon.dto.Car;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceDriverUser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 拉丁
 * @since 2023-09-09
 */

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }


    @GetMapping("/car")
    public ResponseResult<Car> getCarById(@RequestParam Long carId) {
        return carService.getCarById(carId);
    }

}
