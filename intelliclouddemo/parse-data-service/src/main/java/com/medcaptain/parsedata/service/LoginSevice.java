package com.medcaptain.parsedata.service;

import com.medcaptain.dto.RestResult;

public interface LoginSevice {
    RestResult deviceLogin(String productKey, String deviceName, String address);
}
