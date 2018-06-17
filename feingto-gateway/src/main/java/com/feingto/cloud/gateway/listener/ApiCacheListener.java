package com.feingto.cloud.gateway.listener;

import com.feingto.cloud.core.event.IEvent;
import com.feingto.cloud.core.event.annotation.EnableEvent;
import com.feingto.cloud.gateway.filters.support.GwFilterConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * API 缓存事件监听
 *
 * @author longfei
 */
@Slf4j
@Component
@EnableEvent(GwFilterConstants.EVENT_API_CACHE)
public class ApiCacheListener implements IEvent {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Async("threadPoolTaskExecutor")
    @Override
    @SuppressWarnings("unchecked")
    public void onTrigger(Object source, Object params) {
        log.debug(">>>>>> API 缓存事件.");
        Map<String, Object> map = (Map) params;
        String cacheKey = (String) map.get("key");
        Integer expireTime = map.get("expireTime") != null ? (Integer) map.get("expireTime") : GwFilterConstants.DEFAULT_CACHE_TIMEOUT;

        log.debug("Put cache >>> key: {}", cacheKey);
        BoundHashOperations<String, String, String> opts = redisTemplate.boundHashOps(GwFilterConstants.CACHE_ROUTE_RESPONSE);
        opts.put(cacheKey, map.get("content").toString());
        opts.expire(expireTime, TimeUnit.SECONDS);
    }
}
