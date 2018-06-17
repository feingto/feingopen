package com.feingto.cloud.gateway.config;

import com.feingto.cloud.gateway.filters.StaticRouteLocator;
import com.feingto.cloud.gateway.filters.support.GwFilterConstants;
import com.feingto.cloud.gateway.filters.web.GatewayHandlerMapping;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.http.ZuulServlet;
import com.netflix.zuul.monitoring.CounterFactory;
import com.netflix.zuul.monitoring.TracerFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.netflix.zuul.ZuulFilterInitializer;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter;
import org.springframework.cloud.netflix.zuul.filters.pre.DebugFilter;
import org.springframework.cloud.netflix.zuul.filters.pre.FormBodyWrapperFilter;
import org.springframework.cloud.netflix.zuul.filters.pre.Servlet30WrapperFilter;
import org.springframework.cloud.netflix.zuul.filters.pre.ServletDetectionFilter;
import org.springframework.cloud.netflix.zuul.filters.route.SendForwardFilter;
import org.springframework.cloud.netflix.zuul.metrics.DefaultCounterFactory;
import org.springframework.cloud.netflix.zuul.metrics.EmptyCounterFactory;
import org.springframework.cloud.netflix.zuul.metrics.EmptyTracerFactory;
import org.springframework.cloud.netflix.zuul.web.ZuulController;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

/**
 * 网关服务配置
 *
 * @author longfei
 */
@Configuration
@ConditionalOnClass(ZuulServlet.class)
@EnableConfigurationProperties(ZuulProperties.class)
public class GatewayServerConfiguration {
    @Autowired
    protected ZuulProperties zuulProperties;

    @Autowired
    protected ServerProperties server;

    @Autowired(required = false)
    private ErrorController errorController;

    @Bean("zuulFeature")
    public HasFeatures simpleFeature() {
        return HasFeatures.namedFeature(GwFilterConstants.GATEWAY_SERVLET_NAME + " (Simple)", GatewayServerConfiguration.class);
    }

    @Bean
    @ConditionalOnMissingBean(StaticRouteLocator.class)
    public StaticRouteLocator primaryRouteLocator() {
        return new StaticRouteLocator(server.getServlet().getServletPrefix(), zuulProperties);
    }

    @Bean
    public ZuulController zuulController() {
        return new ZuulController();
    }

    @Bean
    @Primary
    public ZuulHandlerMapping zuulHandlerMapping(RouteLocator routes) {
        GatewayHandlerMapping mapping = new GatewayHandlerMapping(routes, zuulController());
        mapping.setErrorController(errorController);
        return mapping;
    }

    @Bean
    @ConditionalOnMissingBean(name = "zuulServlet")
    @SuppressWarnings("unchecked")
    public ServletRegistrationBean zuulServlet() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new ZuulServlet(), zuulProperties.getServletPattern());
        servlet.addInitParameter("buffer-requests", "false");
        return servlet;
    }

    @Bean
    public ServletDetectionFilter servletDetectionFilter() {
        return new ServletDetectionFilter();
    }

    @Bean
    public FormBodyWrapperFilter formBodyWrapperFilter() {
        return new FormBodyWrapperFilter();
    }

    @Bean
    public DebugFilter debugFilter() {
        return new DebugFilter();
    }

    @Bean
    public Servlet30WrapperFilter servlet30WrapperFilter() {
        return new Servlet30WrapperFilter();
    }

    @Bean
    public SendResponseFilter sendResponseFilter() {
        return new SendResponseFilter(zuulProperties);
    }

    @Bean
    public SendErrorFilter sendErrorFilter() {
        return new SendErrorFilter();
    }

    @Bean
    public SendForwardFilter sendForwardFilter() {
        return new SendForwardFilter();
    }

    @Configuration
    protected static class GatewayFilterConfiguration {
        @Autowired
        private Map<String, ZuulFilter> filters;

        @Bean
        public ZuulFilterInitializer zuulFilterInitializer(
                CounterFactory counterFactory, TracerFactory tracerFactory) {
            FilterLoader filterLoader = FilterLoader.getInstance();
            FilterRegistry filterRegistry = FilterRegistry.instance();
            return new ZuulFilterInitializer(filters, counterFactory, tracerFactory, filterLoader, filterRegistry);
        }
    }

    @Configuration
    @ConditionalOnClass(MeterRegistry.class)
    protected static class ZuulCounterFactoryConfiguration {
        @Bean
        @ConditionalOnBean(MeterRegistry.class)
        public CounterFactory counterFactory(MeterRegistry meterRegistry) {
            return new DefaultCounterFactory(meterRegistry);
        }
    }

    @Configuration
    protected static class ZuulMetricsConfiguration {
        @Bean
        @ConditionalOnMissingBean(CounterFactory.class)
        public CounterFactory counterFactory() {
            return new EmptyCounterFactory();
        }

        @Bean
        @ConditionalOnMissingBean(TracerFactory.class)
        public TracerFactory tracerFactory() {
            return new EmptyTracerFactory();
        }

    }
}
