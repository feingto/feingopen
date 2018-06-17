package com.feingto.cloud.oauth2.service;

import com.feingto.cloud.oauth2.domain.ClientDetail;
import com.feingto.cloud.orm.jpa.IBase;

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
     * 根据客户端ID删除数据并移除缓存
     *
     * @param clientId 客户端ID
     */
    void deleteByClientId(String clientId);
}
