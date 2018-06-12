package com.feing.cloud.account.service;

import com.feing.cloud.domain.account.Role;
import com.feing.cloud.orm.jpa.IBase;

import java.util.List;
import java.util.Map;

/**
 * @author longfei
 */
public interface IRole extends IBase<Role, String> {
    /**
     * 更新角色菜单权限
     *
     * @param id        角色ID
     * @param resIds    菜单ID List集合
     * @param buttonMap 菜单按钮Map集合(resId = [buttonId1, buttonId2])
     * @param columnMap 菜单字段Map集合(resId = [columnId1, columnId2])
     */
    void updateRoleRes(String id, List<String> resIds, Map<String, List<String>> buttonMap, Map<String, List<String>> columnMap);
}
