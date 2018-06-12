package com.feing.cloud.oauth2.listener;

import com.feing.cloud.core.stream.MessageStreamProcessor;
import com.feing.cloud.dto.message.RouteEventMessage;
import com.feing.cloud.oauth2.service.IClientDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * 消息流监听器
 *
 * @author longfei
 */
@Slf4j
@EnableBinding(MessageStreamProcessor.class)
public class MessageStreamListener {
    @Autowired
    private IClientDetail clientDetailService;

    /**
     * 路由消息
     */
    @StreamListener(MessageStreamProcessor.RECEIVE_ROUTE)
    public void receive(RouteEventMessage routeMessage) {
        if (RouteEventMessage.Type.REMOVE.equals(routeMessage.getType())) {
            log.debug(">>>>>> Receive REMOVE route refreshed event: {}", routeMessage.getApi().getPath());
            clientDetailService.findAll()
                    .forEach(clientDetail -> {
                        // 删除密钥绑定的API
                        clientDetail.getClientDetailApis()
                                .removeIf(cda -> routeMessage.getApi().getSn().equals(cda.getApiId())
                                        && routeMessage.getApi().getStage().equals(cda.getStage()));
                        clientDetailService.save(clientDetail);
                    });
        }
    }
}
