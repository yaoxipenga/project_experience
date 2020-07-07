package com.medcaptain.parsedata.service;

import com.medcaptain.dto.RestResult;

public interface ManufactureService {
    public RestResult bindingDeviceSN(String topic, String payload);
}
