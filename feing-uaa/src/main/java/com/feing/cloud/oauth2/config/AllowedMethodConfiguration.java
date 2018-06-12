package com.feing.cloud.oauth2.config;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;

import javax.annotation.PostConstruct;

/**
 * GET 方式获取token
 *
 * @author longfei
 */
@Configuration
public class AllowedMethodConfiguration {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @PostConstruct
    public void reconfigure() {
        tokenEndpoint.setAllowedRequestMethods(Sets.newHashSet(HttpMethod.GET, HttpMethod.POST));
    }
}
