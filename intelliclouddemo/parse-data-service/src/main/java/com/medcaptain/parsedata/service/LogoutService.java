package com.medcaptain.parsedata.service;

import com.medcaptain.dto.RestResult;

public interface LogoutService {
    RestResult deviceLogout(String productKey, String deviceName, Integer code);
}
