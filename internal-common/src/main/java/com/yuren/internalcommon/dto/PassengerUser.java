package com.yuren.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/31-22:50
 **/
@Data
public class PassengerUser {

    private long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String passengerPhone;

    private String passengerName;

    private byte passengerGender;

    private byte state;

    private String profilePhoto;

}
