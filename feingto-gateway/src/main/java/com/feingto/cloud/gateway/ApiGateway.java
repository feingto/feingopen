package com.feingto.cloud.gateway;

import com.feingto.cloud.config.annotation.ExcludeComponentScan;
import com.feingto.cloud.config.annotation.WebMvcAutoConfiguration;
import com.feingto.cloud.config.redis.EnableRedisAutoConfiguration;
import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.orm.jpa.repository.MyRepositoryImpl;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 网关
 *
 * @author longfei
 */
@ComponentScan(value = {Constants.BASE_REMOTE_PACKAGES, "com.feingto.cloud.gateway"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeComponentScan.class)})
@EnableJpaRepositories(basePackages = "com.feingto.cloud.gateway.store.repository", repositoryBaseClass = MyRepositoryImpl.class)
@EnableTransactionManagement
@EnableRedisAutoConfiguration
@RefreshScope
@SpringCloudApplication
public class ApiGateway extends WebMvcAutoConfiguration {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(ApiGateway.class)
                .run(args);
    }
}
