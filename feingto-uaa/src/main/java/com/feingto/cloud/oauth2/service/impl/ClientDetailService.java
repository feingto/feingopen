package com.feingto.cloud.oauth2.service.impl;

import com.feingto.cloud.oauth2.domain.ClientDetail;
import com.feingto.cloud.oauth2.repository.ClientDetailRepository;
import com.feingto.cloud.oauth2.service.IClientDetail;
import com.feingto.cloud.orm.jpa.BaseService;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author longfei
 */
@Service
public class ClientDetailService extends BaseService<ClientDetail, String> implements IClientDetail {
    @Autowired
    private ClientDetailRepository repository;

    //@RedisCacheable(key = "'feingto-uaa:clientDetails:' + #clientId", expire = 600, clz = ClientDetail.class)
    @Override
    public ClientDetail findByClientId(String clientId) {
        return super.findOne(Condition.NEW().eq("clientId", clientId));
    }

    //@RedisCachePut(key = "'feingto-uaa:clientDetails:' + #clientDetail.clientId", expire = 600)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientDetail save(ClientDetail clientDetail) {
        if (clientDetail.isNew()) {
            Assert.state(super.count(Condition.NEW().eq("clientId", clientDetail.getClientId())) == 0, "客户端\"" + clientDetail.getClientId() + "\"已存在.");
        } else {
            Assert.state(super.count(Condition.NEW().ne("id", clientDetail.getId()).eq("clientId", clientDetail.getClientId())) == 0, "客户端\"" + clientDetail.getClientId() + "\"已存在.");
        }
        return repository.save(clientDetail);
    }

    //@RedisCacheEvict(key = "'feingto-uaa:clientDetails:' + #clientId")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByClientId(String clientId) {
        super.delete(Condition.NEW().eq("clientId", clientId));
    }
}
