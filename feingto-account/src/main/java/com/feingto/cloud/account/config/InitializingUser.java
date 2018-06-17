package com.feingto.cloud.account.config;

import com.feingto.cloud.account.service.IUser;
import com.feingto.cloud.account.service.impl.RoleService;
import com.feingto.cloud.domain.account.Role;
import com.feingto.cloud.domain.account.User;
import com.feingto.cloud.domain.account.UserRole;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * 初始化用户数据
 *
 * @author longfei
 */
@Configuration
@Order(-30)
@Profile("init-db-data-user")
public class InitializingUser implements InitializingBean {
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_USER_USERNAME = "user";
    private static final String[] DEFAULT_ROLES = {"ADMIN", "USER"};

    @Resource
    private IUser userService;

    @Resource
    private RoleService roleService;

    @Override
    public void afterPropertiesSet() {
        Arrays.stream(DEFAULT_ROLES).forEach(role ->
                Optional.ofNullable(roleService.findOne(Condition.NEW().eq("sn", role)))
                        .orElseGet(() -> roleService.save(Role.builder().sn(role).name(role).enabled(true).build())));

        Optional.ofNullable(userService.findOne(Condition.NEW().eq("username", DEFAULT_ADMIN_USERNAME)))
                .orElseGet(() -> {
                    User user = User.builder()
                            .username(DEFAULT_ADMIN_USERNAME)
                            .password(DEFAULT_PASSWORD)
                            .enabled(true)
                            .build();
                    user.setCreatedBy(DEFAULT_ADMIN_USERNAME);
                    user.setCreatedDate(new Date());
                    Optional.ofNullable(roleService.findOne(Condition.NEW().eq("sn", "ADMIN")))
                            .ifPresent(role -> user.setUserRoles(Lists.newArrayList(UserRole.builder().user(user).role(role).build())));
                    return userService.save(user);
                });
        Optional.ofNullable(userService.findOne(Condition.NEW().eq("username", DEFAULT_USER_USERNAME)))
                .orElseGet(() -> {
                    User user = User.builder()
                            .username(DEFAULT_USER_USERNAME)
                            .password(DEFAULT_PASSWORD)
                            .enabled(true)
                            .build();
                    user.setCreatedBy(DEFAULT_ADMIN_USERNAME);
                    user.setCreatedDate(new Date());
                    Optional.ofNullable(roleService.findOne(Condition.NEW().eq("sn", "USER")))
                            .ifPresent(role -> user.setUserRoles(Lists.newArrayList(UserRole.builder().user(user).role(role).build())));
                    return userService.save(user);
                });
    }
}
