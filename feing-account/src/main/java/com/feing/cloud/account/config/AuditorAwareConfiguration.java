package com.feing.cloud.account.config;

import com.feing.cloud.domain.account.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * JPA 审计配置
 *
 * @author longfei
 */
@Configuration
@EnableJpaAuditing
public class AuditorAwareConfiguration {
    @Bean
    public AuditorAware<String> auditorAware() {
        return new UserAuditorAware();
    }

    class UserAuditorAware implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.of(User.SYSTEM_ADMIN);
        }
    }
}
