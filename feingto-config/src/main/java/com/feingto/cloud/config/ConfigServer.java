package com.feingto.cloud.config;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 分布式配置服务
 *
 * @author longfei
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(ConfigServer.class)
                .run(args);
    }
}
