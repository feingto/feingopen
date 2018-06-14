package com.feingto.cloud.account.config;

import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.security.GwAuthenticationEntryPoint;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务配置
 *
 * @author longfei
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails resourceDetails) {
        return new OAuth2RestTemplate(resourceDetails);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                    .permitAll()
                .antMatchers(Constants.BASE_API + "/login", Constants.BASE_API + "/account/users/*/get")
                    .permitAll()
                .antMatchers(Constants.BASE_API + "/**")
                    .access("hasAuthority('AUTH_API')")
                .anyRequest()
                    .authenticated()
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new GwAuthenticationEntryPoint())
            .and()
                .csrf().disable();
        // @formatter:on
    }
}
