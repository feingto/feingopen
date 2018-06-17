package com.feingto.cloud.gateway.config;

import brave.http.HttpTracing;
import com.feingto.cloud.gateway.filters.BreakerFallbackProvider;
import com.feingto.cloud.gateway.filters.DynamicRouteLocator;
import com.feingto.cloud.gateway.filters.post.LoggerFilter;
import com.feingto.cloud.gateway.filters.pre.FlowLimitFilter;
import com.feingto.cloud.gateway.filters.pre.OAuthAccessFilter;
import com.feingto.cloud.gateway.filters.pre.PreDecorationFilter;
import com.feingto.cloud.gateway.filters.pre.SqlFilter;
import com.feingto.cloud.gateway.filters.route.RoutingFilter;
import com.feingto.cloud.gateway.filters.support.GwFilterConstants;
import com.feingto.cloud.gateway.listener.ApiRefreshListener;
import com.feingto.cloud.gateway.store.service.IApi;
import com.netflix.zuul.filters.FilterRegistry;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientConnectionManagerFactory;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.FiltersEndpoint;
import org.springframework.cloud.netflix.zuul.RoutesEndpoint;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.TraceProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.SimpleHostRoutingFilter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 网关服务过滤器配置
 *
 * @author longfei
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class GatewayProxyConfiguration extends GatewayServerConfiguration {
    @Autowired
    private DiscoveryClient discovery;

    @Bean("zuulFeature")
    public HasFeatures discoveryFeatures() {
        return HasFeatures.namedFeature(GwFilterConstants.GATEWAY_SERVLET_NAME + " (Discovery)", GatewayProxyConfiguration.class);
    }

    @Bean
    @Primary
    public DynamicRouteLocator dynamicRouteLocator(IApi apiService) {
        return new DynamicRouteLocator(server.getServlet().getServletPrefix(), zuulProperties, apiService, discovery);
    }

    @Bean
    public SqlFilter sqlFilter() {
        return new SqlFilter();
    }

    @Bean
    public PreDecorationFilter preDecorationFilter(RouteLocator routeLocator, ProxyRequestHelper proxyRequestHelper) {
        return new PreDecorationFilter(routeLocator, server.getServlet().getServletPrefix(), zuulProperties, proxyRequestHelper);
    }

    @Bean
    public OAuthAccessFilter oAuthAccessFilter() {
        return new OAuthAccessFilter();
    }

    @Bean
    public FlowLimitFilter flowLimitFilter(RedisTemplate<String, String> redisTemplate) {
        return new FlowLimitFilter(redisTemplate);
    }

    @Bean
    public RoutingFilter routingFilter(LoadBalancerClient loadBalancer,
                                       RedisTemplate<String, String> redisTemplate,
                                       HttpTracing httpTracing) {
        return new RoutingFilter(loadBalancer, redisTemplate, httpTracing);
    }

    @Bean
    @ConditionalOnMissingBean({SimpleHostRoutingFilter.class, CloseableHttpClient.class})
    public SimpleHostRoutingFilter simpleHostRoutingFilter(ProxyRequestHelper helper, ZuulProperties properties,
                                                           ApacheHttpClientConnectionManagerFactory connectionManagerFactory,
                                                           ApacheHttpClientFactory httpClientFactory) {
        return new SimpleHostRoutingFilter(helper, properties, connectionManagerFactory, httpClientFactory);
    }

    @Bean
    @ConditionalOnMissingBean(SimpleHostRoutingFilter.class)
    public SimpleHostRoutingFilter simpleHostRoutingFilter2(ProxyRequestHelper helper, ZuulProperties zuulProperties,
                                                            CloseableHttpClient httpClient) {
        return new SimpleHostRoutingFilter(helper, zuulProperties, httpClient);
    }

    @Bean
    public LoggerFilter loggerFilter() {
        return new LoggerFilter();
    }

    @Bean
    public BreakerFallbackProvider breakerFallbackProvider() {
        return new BreakerFallbackProvider();
    }

    @Bean
    public ApplicationListener<ApplicationEvent> discoveryRefreshRoutesListener() {
        return new ApiRefreshListener();
    }

    @Configuration
    @ConditionalOnMissingClass("org.springframework.boot.actuate.endpoint.Endpoint")
    protected static class NoActuatorConfiguration {
        @Bean
        public ProxyRequestHelper proxyRequestHelper(ZuulProperties zuulProperties) {
            ProxyRequestHelper helper = new ProxyRequestHelper();
            helper.setIgnoredHeaders(zuulProperties.getIgnoredHeaders());
            helper.setTraceRequestBody(zuulProperties.isTraceRequestBody());
            return helper;
        }
    }

    @Configuration
    @ConditionalOnClass(Health.class)
    protected static class EndpointConfiguration {
        @Autowired(required = false)
        private HttpTraceRepository traces;

        @Bean
        @ConditionalOnEnabledEndpoint
        public RoutesEndpoint routesEndpoint(RouteLocator routeLocator) {
            return new RoutesEndpoint(routeLocator);
        }

        @ConditionalOnEnabledEndpoint
        @Bean
        public FiltersEndpoint filtersEndpoint() {
            FilterRegistry filterRegistry = FilterRegistry.instance();
            return new FiltersEndpoint(filterRegistry);
        }

        @Bean
        public ProxyRequestHelper proxyRequestHelper(ZuulProperties zuulProperties) {
            TraceProxyRequestHelper helper = new TraceProxyRequestHelper();
            if (traces != null) {
                helper.setTraces(traces);
            }
            helper.setIgnoredHeaders(zuulProperties.getIgnoredHeaders());
            helper.setTraceRequestBody(zuulProperties.isTraceRequestBody());
            return helper;
        }
    }
}
