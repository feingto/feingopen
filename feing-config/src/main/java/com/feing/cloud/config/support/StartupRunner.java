package com.feing.cloud.config.support;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.kv.model.GetValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.consul.config.ConsulConfigProperties;

/**
 * 启动加载
 *
 * @author longfei
 */
//@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    private ConsulClient consulClient;

    @Autowired
    private ConsulConfigProperties consulConfigProperties;

    @Override
    public void run(String... args) {
        this.initializingVariate();
    }

    /**
     * 写入共用配置
     *
     * @deprecated docker 启动时写入
     */
    private void initializingVariate() {
        String key = consulConfigProperties.getPrefix() + "/application/" + consulConfigProperties.getDataKey();
        GetValue lockValue = consulClient.getKVValue(key).getValue();
        if (lockValue == null) {
            consulClient.setKVValue(key, "{CONFIG_SERVER_PASSWORD: 123456, CONSUL_HOST: consul, CONSUL_PORT: 8500, " +
                    "RABBITMQ_HOST: rabbitmq, RABBITMQ_PORT: 5672, MYSQL_HOST: mysql, MYSQL_PORT: 3306, " +
                    "REDIS_HOST: redis, REDIS_PORT: 6379, UAA_HOST: feing-uaa, UAA_PORT: 8800, " +
                    "MONITOR_HOST: feing-monitor, ZIPKIN_HOST: zipkin}");
        }
    }
}
