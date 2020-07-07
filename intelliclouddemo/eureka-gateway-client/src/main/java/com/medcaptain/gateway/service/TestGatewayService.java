package com.medcaptain.gateway.service;

import org.springframework.context.ApplicationEventPublisherAware;

public interface TestGatewayService extends ApplicationEventPublisherAware {

    String save();

}
