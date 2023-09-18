package com.yuren.seervicemap.controller;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TerminalResponse;
import com.yuren.seervicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-21:19
 **/
@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @PostMapping("/add")
    public ResponseResult<TerminalResponse> add(String name , Long desc) {
        return terminalService.add(name, desc);
    }

    @GetMapping("/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius) {
        return terminalService.aroundsearch(center, radius);
    }

}
