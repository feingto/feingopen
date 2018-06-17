package com.feingto.cloud.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author longfei
 */
@Configuration
@ConditionalOnMissingClass("org.springframework.boot.actuate.endpoint.Endpoint")
public class NoActuatorConfiguration {
    @Bean
    public ProxyRequestHelper proxyRequestHelper(ZuulProperties zuulProperties) {
        ProxyRequestHelper helper = new ProxyRequestHelper();
        helper.setIgnoredHeaders(zuulProperties.getIgnoredHeaders());
        helper.setTraceRequestBody(zuulProperties.isTraceRequestBody());
        return helper;
    }
}
