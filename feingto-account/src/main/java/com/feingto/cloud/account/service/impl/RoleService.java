package com.feingto.cloud.account.service.impl;

import com.feingto.cloud.account.repository.RoleRepository;
import com.feingto.cloud.account.repository.RoleResourceButtonRepository;
import com.feingto.cloud.account.repository.RoleResourceColumnRepository;
import com.feingto.cloud.account.service.IRole;
import com.feingto.cloud.domain.account.*;
import com.feingto.cloud.orm.jpa.BaseService;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author longfei
 */
@Service
@SuppressWarnings("unchecked")
public class RoleService extends BaseService<Role, String> implements IRole {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleResourceButtonRepository rrbRepository;

    @Autowired
    private RoleResourceColumnRepository rrcRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role save(Role role) {
        Assert.notNull(role.getName(), "角色名称不能为空.");
        if (role.isNew()) {
            Assert.state(super.count(Condition.NEW().eq("name", role.getName())) == 0, "角色名称\"" + role.getName() + "\"已存在.");
        } else {
            Assert.state(super.count(Condition.NEW().ne("id", role.getId()).eq("name", role.getName())) == 0, "角色名称\"" + role.getName() + "\"已存在.");
        }
        return roleRepository.save(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleRes(String id, List<String> resIds, Map<String, List<String>> buttonMap, Map<String, List<String>> columnMap) {
        Role role = roleRepository.getOne(id);
        role.getResources().clear();
        resIds.forEach(resId -> role.getResources().add(Resource.builder().id(resId).build()));
        this.save(role);

        super.executeBySql("delete from sy_role_res_btn where role_id='" + role.getId() + "'");
        List<RoleResourceButton> rrbList = new ArrayList();
        buttonMap.keySet().forEach(key -> buttonMap.get(key).forEach(buttonId -> rrbList.add(RoleResourceButton.builder().resId(key).roleId(role.getId()).button(ResourceButton.builder().id(buttonId).build()).build())));
        if (rrbList.size() > 0) {
            this.rrbRepository.saveAll(rrbList);
        }

        super.executeBySql("delete from sy_role_res_column where role_id='" + role.getId() + "'");
        List<RoleResourceColumn> rrcList = new ArrayList();
        columnMap.keySet().forEach(key -> columnMap.get(key).forEach(columnId -> rrcList.add(RoleResourceColumn.builder().resId(key).roleId(role.getId()).column(ResourceColumn.builder().id(columnId).build()).build())));
        if (rrcList.size() > 0) {
            this.rrcRepository.saveAll(rrcList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        super.executeBySql("delete from sy_role_res_btn where role_id='" + id + "'");
        super.executeBySql("delete from sy_role_res_column where role_id='" + id + "'");
        roleRepository.deleteById(id);
    }
}
