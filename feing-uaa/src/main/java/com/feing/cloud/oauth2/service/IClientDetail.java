package com.feing.cloud.oauth2.service;

import com.feing.cloud.oauth2.domain.ClientDetail;
import com.feing.cloud.orm.jpa.IBase;

import java.util.List;

/**
 * @author longfei
 */
public interface IClientDetail extends IBase<ClientDetail, String> {
    /**
     * 根据客户端ID查询数据并缓存
     *
     * @param clientId 客户端ID
     * @return ClientDetail
     */
    ClientDetail findByClientId(String clientId);

    /**
     * 根据客户端绑定用户查询数据并缓存
     *
     * @param username 客户端绑定用户
     * @return ClientDetail
     */
    ClientDetail findByUsername(String username);

    /**
     * 根据客户端所有者查询数据并缓存
     *
     * @param createdBy 客户端所有者
     * @return ClientDetail
     */
    List<ClientDetail> findByCreatedBy(String createdBy);

    /**
     * 根据客户端ID删除数据并移除缓存
     *
     * @param id       ID
     * @param username 用户名
     */
    void bindUser(String id, String username);

    /**
     * 根据客户端ID删除数据并移除缓存
     *
     * @param clientId 客户端ID
     */
    void deleteByClientId(String clientId);
}
