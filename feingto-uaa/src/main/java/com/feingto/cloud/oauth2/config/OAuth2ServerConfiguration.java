package com.feingto.cloud.oauth2.config;

import com.feingto.cloud.oauth2.security.GwClientDetailsService;
import com.feingto.cloud.oauth2.security.GwJwtAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * OAuth2 配置
 *
 * @author longfei
 */
@Configuration
public class OAuth2ServerConfiguration {
    /**
     * 资源服务配置
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Value("${security.oauth2.resource.id:oauth2-resource}")
        private String resourceId;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(resourceId);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/user").authorizeRequests().anyRequest().authenticated();
        }
    }

    /**
     * 授权服务配置
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Value("${security.oauth2.resource.jwt.key-value}")
        private String key;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private GwClientDetailsService clientDetailsService;

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            GwJwtAccessTokenConverter converter = new GwJwtAccessTokenConverter();
            converter.setSigningKey(key);
            return converter;
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(this.tokenStore());
            defaultTokenServices.setSupportRefreshToken(true);
            return defaultTokenServices;
        }

        @Bean
        public TokenStore tokenStore() {
            return new JwtTokenStore(this.accessTokenConverter());
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints
                    .authenticationManager(this.authenticationManager)
                    .accessTokenConverter(accessTokenConverter())
                    .addInterceptor(new HandlerInterceptorAdapter() {
                        @Override
                        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
                            if (modelAndView != null && modelAndView.getView() instanceof RedirectView) {
                                RedirectView redirect = (RedirectView) modelAndView.getView();
                                String url = redirect.getUrl();
                                if (url != null && ("code=".contains(url) || "error=".contains(url))) {
                                    HttpSession session = request.getSession(false);
                                    if (session != null) {
                                        session.invalidate();
                                    }
                                }
                            }
                        }
                    });
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            oauthServer.tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()")
                    .allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(this.clientDetailsService);
        }
    }
}
