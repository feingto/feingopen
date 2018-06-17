package com.feingto.cloud.oauth2.service.impl;

import com.feingto.cloud.cache.provider.RedisProvider;
import com.feingto.cloud.core.json.JSON;
import com.feingto.cloud.oauth2.domain.ClientDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author longfei
 */
@Slf4j
@Service
public class ClientDetailCacheService extends ClientDetailService {
    private final static String prefix = "feingto-uaa:client:";

    @Autowired
    private RedisProvider redisProvider;

    @Override
    public ClientDetail findByClientId(String clientId) {
        String key = prefix + "id:" + clientId;
        if (redisProvider.has(key)) {
            return JSON.build().object2pojo(redisProvider.get(key), ClientDetail.class);
        } else {
            return Optional.ofNullable(super.findByClientId(clientId))
                    .map(clientDetail -> {
                        log.debug("Cache >>> put key >>> {}", key);
                        redisProvider.put(key, clientDetail);
                        return clientDetail;
                    })
                    .orElse(null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientDetail save(ClientDetail clientDetail) {
        String key = prefix + "id:" + clientDetail.getClientId();
        clientDetail = super.save(clientDetail);
        log.debug("Cache >>> put key >>> {}", key);
        redisProvider.put(key, clientDetail);
        return clientDetail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByClientId(String clientId) {
        super.deleteByClientId(clientId);

        String idKey = prefix + "id:" + clientId;
        if (redisProvider.has(idKey)) {
            log.debug("Cache >>> delete key >>> {}", idKey);
            redisProvider.remove(idKey);
        }
    }
}
