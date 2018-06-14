package com.feingto.cloud.account;

import com.feingto.cloud.config.annotation.ExcludeComponentScan;
import com.feingto.cloud.config.annotation.WebMvcAutoConfiguration;
import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.orm.jpa.repository.MyRepositoryImpl;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 管理平台
 *
 * @author longfei
 */
@ComponentScan(value = {Constants.BASE_REMOTE_PACKAGES, "com.feingto.cloud.account"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeComponentScan.class)})
@EnableFeignClients(Constants.BASE_REMOTE_PACKAGES)
@EnableJpaRepositories(basePackages = "com.feingto.cloud.account.repository", repositoryBaseClass = MyRepositoryImpl.class)
@EnableTransactionManagement
@EntityScan("com.feingto.cloud.domain.account")
@RefreshScope
@SpringCloudApplication
public class AccountServer extends WebMvcAutoConfiguration {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(AccountServer.class)
                .run(args);
    }
}
