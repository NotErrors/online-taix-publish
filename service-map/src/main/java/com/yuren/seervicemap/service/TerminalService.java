package com.yuren.seervicemap.service;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TerminalResponse;
import com.yuren.seervicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-21:18
 **/
@Service
public class TerminalService {

    @Autowired
    private TerminalClient terminalClient;

    public ResponseResult add(String name, Long desc) {
        return terminalClient.add(name, desc);
    }

    public ResponseResult<List<TerminalResponse>> aroundsearch(String center, Integer radius) {
        return terminalClient.aroundSearch(center, radius);
    }
}
