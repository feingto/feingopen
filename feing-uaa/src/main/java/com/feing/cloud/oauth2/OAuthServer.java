package com.feing.cloud.oauth2;

import com.feing.cloud.config.annotation.ExcludeComponentScan;
import com.feing.cloud.config.redis.EnableRedisAutoConfiguration;
import com.feing.cloud.core.web.Constants;
import com.feing.cloud.orm.jpa.repository.MyRepositoryImpl;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * UAA授权服务
 *
 * @author longfei
 */
@ComponentScan(value = {Constants.BASE_REMOTE_PACKAGES, "com.feing.cloud.oauth2"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeComponentScan.class)})
@EnableFeignClients(Constants.BASE_REMOTE_PACKAGES)
@EnableJpaRepositories(basePackages = "com.feing.cloud.oauth2.repository", repositoryBaseClass = MyRepositoryImpl.class)
@EnableTransactionManagement
@EnableRedisAutoConfiguration
@RefreshScope
@SpringCloudApplication
public class OAuthServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(OAuthServer.class)
                .run(args);
    }
}
