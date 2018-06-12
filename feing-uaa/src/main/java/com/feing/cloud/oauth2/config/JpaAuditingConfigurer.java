package com.feing.cloud.oauth2.config;

import com.feing.cloud.domain.account.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * JPA 审计配置
 *
 * @author longfei
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfigurer {
    @Bean
    public AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    @Bean
    public AuditorAware<String> auditorAware(AuthenticationTrustResolver trustResolver) {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || trustResolver.isAnonymous(authentication)) {
                return Optional.of(User.SYSTEM_ADMIN);
            }

            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                return Optional.of((String) principal);
            } else if (principal instanceof UserDetails) {
                return Optional.of(((UserDetails) principal).getUsername());
            } else {
                return Optional.of(String.valueOf(principal));
            }
        };
    }
}
