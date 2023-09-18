package com.yuren.serviceDriverUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuren.internalcommon.dto.DriverUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/7-22:07
 **/
@Repository
public interface DriverUserMapper extends BaseMapper<DriverUser> {

    public Integer selectDriverUserCountByCityCode(@Param("cityCode") String cityCode);

}
