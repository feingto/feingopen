package com.feingto.cloud.account.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.feingto.cloud.account.service.IRole;
import com.feingto.cloud.account.service.impl.RoleResourceButtonService;
import com.feingto.cloud.account.service.impl.RoleResourceColumnService;
import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.core.web.WebResult;
import com.feingto.cloud.domain.IdEntity;
import com.feingto.cloud.domain.account.Role;
import com.feingto.cloud.domain.account.RoleResourceButton;
import com.feingto.cloud.domain.account.RoleResourceColumn;
import com.feingto.cloud.orm.jpa.page.ConditionPage;
import com.feingto.cloud.orm.jpa.page.Page;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import com.google.common.collect.Maps;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色管理控制器
 *
 * @author longfei
 */
@RestController
@RequestMapping(Constants.BASE_API + "/account/role")
public class RoleController {
    @Autowired
    private IRole roleService;

    @Autowired
    private RoleResourceButtonService rrbService;

    @Autowired
    private RoleResourceColumnService rrcService;

    @GetMapping("/{id}")
    public Role get(@PathVariable String id) {
        roleService.setLazyInitializer(r -> {
            r.getUserRoles().forEach(userRole -> Hibernate.initialize(userRole.getUser()));
            r.getResources().forEach(resource -> {
                Hibernate.initialize(resource.getButtons());
                Hibernate.initialize(resource.getColumns());
            });
        });
        return roleService.findById(id);
    }

    @GetMapping
    public Page list(@RequestBody(required = false) ConditionPage<Role> page) {
        if (page == null) {
            page = new ConditionPage<>();
        }
        return roleService.findByPage(page.getCondition(), page);
    }

    @PostMapping
    public JsonNode save(@RequestBody Role role) {
        if (!role.isNew()) {
            roleService.setLazyInitializer(r -> {
                r.getUserRoles().forEach(userRole -> Hibernate.initialize(userRole.getUser()));
                r.getResources().forEach(resource -> {
                    Hibernate.initialize(resource.getButtons());
                    Hibernate.initialize(resource.getColumns());
                });
            });
            Role eqRole = roleService.findById(role.getId());
            role.setResources(eqRole.getResources());
            role.setUserRoles(eqRole.getUserRoles());
        }
        roleService.save(role);
        return WebResult.success().putPOJO("role", role);
    }

    @DeleteMapping("/{id}")
    public JsonNode delete(@PathVariable String id) {
        roleService.delete(id);
        return WebResult.success();
    }


    @PostMapping("/{id}/enable")
    public JsonNode enable(@PathVariable String id) {
        roleService.updateByProperty(id, "enabled", true);
        return WebResult.success();
    }

    @PostMapping("/{id}/disable")
    public JsonNode disable(@PathVariable String id) {
        roleService.updateByProperty(id, "enabled", false);
        return WebResult.success();
    }

    @GetMapping(value = "/{roleId}/res", produces = "application/json;charset=UTF-8")
    public Map<String, Object> resList(@PathVariable String roleId) {
        Map<String, Object> map = Maps.newHashMap();
        roleService.setLazyInitializer(role -> Hibernate.initialize(role.getResources()));
        List<String> roleResIds = roleService.findById(roleId).getResources().stream().map(IdEntity::getId).collect(Collectors.toList());
        Map<String, List<String>> btnMap = rrbService.findAll(Condition.NEW().eq("roleId", roleId))
                .stream().collect(Collectors.groupingBy(RoleResourceButton::getResId, Collectors.mapping(rrb -> rrb.getButton().getId(), Collectors.toList())));
        Map<String, List<String>> colMap = rrcService.findAll(Condition.NEW().eq("roleId", roleId))
                .stream().collect(Collectors.groupingBy(RoleResourceColumn::getResId, Collectors.mapping(rrc -> rrc.getColumn().getId(), Collectors.toList())));

        map.put("roleResIds", roleResIds);
        map.put("roleResBtns", btnMap);
        map.put("roleResCols", colMap);
        return map;
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/{roleId}/res")
    public JsonNode resSave(@PathVariable String roleId, @RequestBody Map<String, Object> map) {
        roleService.updateRoleRes(roleId, (List<String>) map.get("roleResIds"),
                (Map<String, List<String>>) map.get("roleResBtns"), (Map<String, List<String>>) map.get("roleResCols"));
        return WebResult.success();
    }
}
