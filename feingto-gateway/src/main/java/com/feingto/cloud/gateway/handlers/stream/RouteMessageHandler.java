package com.feingto.cloud.gateway.handlers.stream;

import com.feingto.cloud.dto.message.RouteEventMessage;
import com.feingto.cloud.gateway.filters.RouteRefreshedEvent;
import com.feingto.cloud.gateway.handlers.IBaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 路由消息处理器
 *
 * @author longfei
 */
@Slf4j
@Component
public class RouteMessageHandler implements IBaseHandler {
    @Autowired
    private ApplicationEventPublisher publisher;

    public void handle(Object message) {
        RouteEventMessage routeMessage = (RouteEventMessage) message;
        RouteRefreshedEvent refreshedEvent = null;
        switch (routeMessage.getType()) {
            case UPDATE:
                log.debug(">>>>>> Receive {} route message: {}", routeMessage.getType(), routeMessage.getApi().getPath());
                refreshedEvent = RouteRefreshedEvent.updateEvent(routeMessage.getApi());
                break;
            case REMOVE:
                log.debug(">>>>>> Receive {} route message: {}", routeMessage.getType(), routeMessage.getApi().getPath());
                refreshedEvent = RouteRefreshedEvent.removeEvent(routeMessage.getApi());
                break;
            case RESET:
                log.debug(">>>>>> Receive {} route message.", routeMessage.getType());
                refreshedEvent = RouteRefreshedEvent.resetEvent();
                break;
        }
        this.publisher.publishEvent(refreshedEvent);
    }
}
