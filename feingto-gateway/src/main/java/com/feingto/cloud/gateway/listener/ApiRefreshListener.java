package com.feingto.cloud.gateway.listener;

import com.feingto.cloud.gateway.filters.RouteRefreshedEvent;
import com.feingto.cloud.gateway.filters.web.GatewayHandlerMapping;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.HeartbeatMonitor;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.client.discovery.event.ParentHeartbeatEvent;
import org.springframework.cloud.consul.ConsulProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoRegistration;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 服务发现刷新事件监听器
 *
 * @author longfei
 */
public class ApiRefreshListener implements ApplicationListener<ApplicationEvent> {
    private HeartbeatMonitor heartbeatMonitor = new HeartbeatMonitor();

    @Getter
    private ConsulProperties consulProperties;

    @Getter
    private ConsulAutoRegistration registration;

    @Autowired
    private ZuulHandlerMapping handlerMapping;

    public ApiRefreshListener() {
    }

    public ApiRefreshListener(ConsulAutoRegistration registration, ConsulProperties consulProperties) {
        this.registration = registration;
        this.consulProperties = consulProperties;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent
                || event instanceof RefreshScopeRefreshedEvent
                || event instanceof RoutesRefreshedEvent
                || event instanceof InstanceRegisteredEvent) {
            reset();
        } else if (event instanceof ParentHeartbeatEvent) {
            ParentHeartbeatEvent e = (ParentHeartbeatEvent) event;
            resetIfNeeded(e.getValue());
        } else if (event instanceof HeartbeatEvent) {
            HeartbeatEvent e = (HeartbeatEvent) event;
            resetIfNeeded(e.getValue());
        } else if (event instanceof RouteRefreshedEvent) {
            ((GatewayHandlerMapping) this.handlerMapping).registerHandlers((RouteRefreshedEvent) event);
        }
    }

    private void resetIfNeeded(Object value) {
        if (this.heartbeatMonitor.update(value)) {
            reset();
        }
    }

    private void reset() {
        this.handlerMapping.setDirty(true);
    }
}
