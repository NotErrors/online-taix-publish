package com.yuren.seervicemap.service;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.seervicemap.remote.ServiceFromMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/11-23:00
 **/
@Service
public class ServiceFromMapService {

    @Autowired
    private ServiceFromMapClient serviceFromMapClient;

    public ResponseResult add(String name) {
        return serviceFromMapClient.add(name);
    }

}
