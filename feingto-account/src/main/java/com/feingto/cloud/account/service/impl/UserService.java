package com.feingto.cloud.account.service.impl;

import com.feingto.cloud.account.repository.UserRepository;
import com.feingto.cloud.account.service.IResource;
import com.feingto.cloud.account.service.IUser;
import com.feingto.cloud.domain.IdEntity;
import com.feingto.cloud.domain.account.*;
import com.feingto.cloud.dto.oauth.ClientDetailApiDTO;
import com.feingto.cloud.dto.oauth.ClientDetailDTO;
import com.feingto.cloud.kit.reflection.ConvertKit;
import com.feingto.cloud.orm.jdbc.JdbcTemplateKit;
import com.feingto.cloud.orm.jdbc.model.Records;
import com.feingto.cloud.orm.jpa.BaseService;
import com.feingto.cloud.orm.jpa.IBase;
import com.feingto.cloud.orm.jpa.page.Page;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import com.feingto.cloud.orm.jpa.specification.bean.OrderSort;
import com.feingto.cloud.remote.SignClient;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author longfei
 */
@Service
public class UserService extends BaseService<User, String> implements IUser {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IResource resourceService;

    @Autowired
    @Qualifier("roleResourceButtonService")
    private IBase<RoleResourceButton, String> rrbService;

    @Autowired
    @Qualifier("roleResourceColumnService")
    private IBase<RoleResourceColumn, String> rrcService;

    @Autowired
    private SignClient signClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) {
        Assert.notNull(user.getUsername(), "用户名不能为空.");
        if (user.isNew()) {
            Assert.state(super.count(Condition.NEW().eq("username", user.getUsername())) == 0, "用户名\"" + user.getUsername() + "\"已存在.");
        } else {
            Assert.state(super.count(Condition.NEW().ne("id", user.getId()).eq("username", user.getUsername())) == 0, "用户名\"" + user.getUsername() + "\"已存在.");
        }
        if (user.isNew()) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            user.setUserRoles(user.getRoleIds().stream().map(roleId -> UserRole.builder()
                    .user(user)
                    .role(Role.builder().id(roleId).build())
                    .build())
                    .collect(Collectors.toList()));
        } else {
            userRepository.findById(user.getId()).ifPresent(u -> {
                if (!u.getPassword().equals(user.getPassword())) {
                    user.setPassword(DigestUtils.md5Hex(user.getPassword()));
                    user.getUserRoles().clear();
                    user.getRoleIds().forEach(roleId -> user.getUserRoles().add(UserRole.builder()
                            .user(user)
                            .role(Role.builder().id(roleId).build())
                            .build()));
                }
            });
        }
        return userRepository.save(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Resource> parseUserResource(String userId, String roleId) {
        List<Resource> resources = Lists.newArrayList();
        User user = userRepository.getOne(userId);
        Condition condition = Condition.NEW().isTrue("enabled").isTrue("display");
        if (user.isSystemAdmin()) {
            resources = resourceService.findAll(condition, new OrderSort("location"));
        } else {
            Set<String> roleIdList = Sets.newHashSet();
            user.getUserRoles().forEach(userRole -> roleIdList.add(userRole.getRole().getId()));
            if (roleIdList.size() > 0 || StringUtils.hasText(roleId)) {
                // 根据角色过滤资源
                if (StringUtils.hasText(roleId)) {
                    condition.eq("roles.id", roleId);
                } else {
                    condition.in("roles.id", roleIdList);
                }
                condition.distinct();
                resources = resourceService.findAll(condition, new OrderSort("location"));
                if (resources.size() > 0) {
                    // 查找角色的按钮权限和字段权限
                    condition = Condition.NEW().in("resId", resources.stream().map(IdEntity::getId).collect(Collectors.toList()));
                    if (StringUtils.hasText(roleId)) {
                        condition.eq("roleId", roleId);
                    } else {
                        condition.in("roleId", roleIdList);
                    }

                    // 查找角色按钮权限
                    List<String> buttons = ConvertKit.convertElementPropertyToList(rrbService.findAll(condition), "button.id");
                    // 查找角色字段权限
                    List<String> columns = ConvertKit.convertElementPropertyToList(rrcService.findAll(condition), "column.id");

                    // 过滤资源的按钮权限和字段权限，过滤后得到拥有的权限
                    resources.forEach(resource -> {
                        resource.getButtons().removeIf(button -> !buttons.contains(button.getId()));
                        resource.getColumns().removeIf(column -> !columns.contains(column.getId()));
                    });
                }
            }
        }
        resources.forEach(resource -> {
            Hibernate.initialize(resource.getRoles());
            Hibernate.initialize(resource.getButtons());
            Hibernate.initialize(resource.getColumns());
        });
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> findPageByUsername(Page<User> page, String username, String keyword) {
        JdbcTemplateKit jdbc = JdbcTemplateKit.builder().jdbcTemplate(jdbcTemplate).build();
        StringBuilder where = new StringBuilder(" where 1=1");
        if (StringUtils.hasText(keyword)) {
            where.append(" and (username like '%").append(keyword).append("%' or real_name like '%").append(keyword).append("%')");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select count(id) as totalElements from sy_user").append(where);
        long totalElements = jdbc.get(sql.toString()).getLong("totalElements");
        int totalPages = (int) totalElements / page.getPageSize();
        if (totalElements % page.getPageSize() != 0) {
            totalPages++;
        }

        sql = new StringBuilder("select id, username, real_name as realName from sy_user").append(where)
                .append(" order by username=? desc,")
                .append(" ").append(page.getOrderField())
                .append(" ").append(page.getOrderDirection())
                .append(" limit ?, ?");
        Records records = jdbc.list(sql.toString(), username, (page.getPageNumber() - 1) * page.getPageSize(), page.getPageNumber() * page.getPageSize());
        page.setContent(records.stream()
                .map(record -> User.builder()
                        .username(record.getString("username"))
                        .realName(record.getString("realName"))
                        .build()).collect(Collectors.toList()));
        page.setTotalElements(totalElements);
        page.setTotalPages(totalPages);
        return page;
    }

    @Override
    public List<ClientDetailApiDTO> findOAuthApisByUsername(String username) {
        List<ClientDetailApiDTO> apiIds = Lists.newArrayList();
        ClientDetailDTO clientDetail = signClient.findByUsername(username);
        if (clientDetail != null && !CollectionUtils.isEmpty(clientDetail.getApis())) {
            apiIds = Lists.newArrayList(clientDetail.getApis());
        }
        return apiIds;
    }
}
