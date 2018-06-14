package com.feingto.cloud.oauth2.service.impl;

import com.feingto.cloud.cache.provider.RedisProvider;
import com.feingto.cloud.core.json.JSON;
import com.feingto.cloud.oauth2.domain.ClientDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        return Optional.ofNullable(redisProvider.get(key))
                .map(map -> JSON.build().object2pojo(map, ClientDetail.class))
                .orElse(Optional.ofNullable(super.findByClientId(clientId))
                        .map(clientDetail -> {
                            log.debug("Cache >>> put key >>> {}", key);
                            redisProvider.put(key, clientDetail);
                            return clientDetail;
                        })
                        .orElse(null));
    }

    @Override
    public ClientDetail findByUsername(String username) {
        String key = prefix + "user:" + username;
        return Optional.ofNullable(redisProvider.get(key))
                .map(map -> JSON.build().object2pojo(map, ClientDetail.class))
                .orElse(Optional.ofNullable(super.findByUsername(username))
                        .map(clientDetail -> {
                            log.debug("Cache >>> put key >>> {}", key);
                            redisProvider.put(key, clientDetail);
                            return clientDetail;
                        })
                        .orElse(null));
    }

    @Override
    public List<ClientDetail> findByCreatedBy(String createdBy) {
        String key = prefix + "created:" + createdBy;
        return Optional.ofNullable(redisProvider.get(key))
                .map(map -> JSON.build().object2list(map, ClientDetail.class))
                .orElse(Optional.ofNullable(super.findByCreatedBy(createdBy))
                        .map(clientDetail -> {
                            log.debug("Cache >>> put key >>> {}", key);
                            redisProvider.put(key, clientDetail);
                            return clientDetail;
                        })
                        .orElse(null));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientDetail save(ClientDetail clientDetail) {
        String key = prefix + "id:" + clientDetail.getClientId();
        ClientDetail clientDetail1 = super.save(clientDetail);
        log.debug("Cache >>> put key >>> {}", key);
        redisProvider.put(key, clientDetail);
        return clientDetail1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByClientId(String clientId) {
        super.deleteByClientId(clientId);

        String idKey = prefix + "id:" + clientId;
        if (redisProvider.has(idKey)) {
            ClientDetail clientDetail = JSON.build().object2pojo(redisProvider.get(idKey), ClientDetail.class);
            log.debug("Cache >>> delete key >>> {}", idKey);
            redisProvider.remove(idKey);

            String userKey = prefix + "user:" + clientDetail.getUsername();
            if (redisProvider.has(userKey)) {
                log.debug("Cache >>> delete key >>> {}", userKey);
                redisProvider.remove(userKey);
            }

            String createdKey = prefix + "created:" + clientId;
            if (redisProvider.has(createdKey)) {
                log.debug("Cache >>> delete key >>> {}", createdKey);
                redisProvider.remove(createdKey);
            }
        }
    }
}
