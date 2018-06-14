package com.feingto.cloud.account.service;

import com.feingto.cloud.domain.account.Resource;
import com.feingto.cloud.domain.account.User;
import com.feingto.cloud.dto.oauth.ClientDetailApiDTO;
import com.feingto.cloud.orm.jpa.IBase;
import com.feingto.cloud.orm.jpa.page.Page;

import java.util.List;

/**
 * @author longfei
 */
public interface IUser extends IBase<User, String> {
    /**
     * 根据用户ID和角色ID查找用户的资源
     * 角色ID为空时查询该用户的所有角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID, 可为空
     */
    List<Resource> parseUserResource(String userId, String roleId);

    /**
     * 分页获取用户，指定用户在最前面
     *
     * @param page     Page
     * @param username 前置用户名
     * @param keyword  关键字
     */
    Page<User> findPageByUsername(Page<User> page, String username, String keyword);

    /**
     * 获取APP 密钥绑定用户的 API
     *
     * @param username 前置用户名
     */
    List<ClientDetailApiDTO> findOAuthApisByUsername(String username);
}
