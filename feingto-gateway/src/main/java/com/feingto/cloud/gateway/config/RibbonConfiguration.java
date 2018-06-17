package com.feingto.cloud.gateway.config;

import com.feingto.cloud.config.annotation.ExcludeComponentScan;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon 负载均衡策略配置
 *
 * @author longfei
 */
@Configuration
@ExcludeComponentScan
public class RibbonConfiguration {
    /**
     * 负载均衡策略
     */
    @Bean
    public IRule ribbonRule() {
        return new AvailabilityFilteringRule();
    }
}
