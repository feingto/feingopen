package com.feingto.cloud.gateway.listener;

import com.feingto.cloud.core.http.client.HttpRequest;
import com.feingto.cloud.core.stream.MessageStreamProcessor;
import com.feingto.cloud.dto.message.RouteEventMessage;
import com.feingto.cloud.dto.tcc.TccRequest;
import com.feingto.cloud.dto.tcc.TccStatus;
import com.feingto.cloud.gateway.handlers.IBaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.HttpMethod;

import javax.annotation.Resource;

/**
 * 消息流监听器
 *
 * @author longfei
 */
@Slf4j
@EnableBinding(MessageStreamProcessor.class)
public class MessageStreamListener {
    @Resource(name = "routeMessageHandler")
    private IBaseHandler routeMessageHandler;

    /**
     * 路由消息
     */
    @StreamListener(MessageStreamProcessor.RECEIVE_ROUTE)
    public void receive(RouteEventMessage routeMessage) {
        routeMessageHandler.handle(routeMessage);
    }

    /**
     * 事务消息
     */
    @StreamListener(MessageStreamProcessor.RECEIVE_TCC)
    public void receive(TccRequest request) {
        request.getParticipants().stream()
                .filter(participant -> participant.getStatus().equals(TccStatus.CONFIRMED))
                .forEach(participant -> {
                    log.debug("Tcc rollback url: {}", participant.getUrl());
                    // 资源取消，发送DELETE请求
                    HttpRequest.build()
                            .method(HttpMethod.DELETE)
                            .headers(participant.getHeaders())
                            .retry(participant.getRetry())
                            .call(participant.getUrl());
                });
    }
}
