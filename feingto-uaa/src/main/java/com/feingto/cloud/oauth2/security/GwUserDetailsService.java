package com.feingto.cloud.oauth2.security;

import com.feingto.cloud.remote.account.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户验证服务扩展
 *
 * @author longfei
 */
@Slf4j
@Service
public class GwUserDetailsService implements UserDetailsService {
    @Autowired
    private UserClient userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug(">>> loadUserByUsername: {}", username);
        return Optional.ofNullable(userService.user(username))
                .map(SecurityUser::create)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
    }
}
