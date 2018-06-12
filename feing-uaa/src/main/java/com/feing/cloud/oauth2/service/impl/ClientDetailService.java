package com.feing.cloud.oauth2.service.impl;

import com.feing.cloud.oauth2.domain.ClientDetail;
import com.feing.cloud.oauth2.repository.ClientDetailRepository;
import com.feing.cloud.oauth2.service.IClientDetail;
import com.feing.cloud.orm.jpa.BaseService;
import com.feing.cloud.orm.jpa.specification.bean.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author longfei
 */
@Service
public class ClientDetailService extends BaseService<ClientDetail, String> implements IClientDetail {
    @Autowired
    private ClientDetailRepository repository;

    //@RedisCacheable(key = "'feing-uaa:clientDetails:' + #clientId", expire = 600, clz = ClientDetail.class)
    @Override
    public ClientDetail findByClientId(String clientId) {
        return super.findOne(Condition.NEW().eq("clientId", clientId));
    }

    @Override
    public ClientDetail findByUsername(String username) {
        return this.findOne(Condition.NEW().eq("username", username));
    }

    @Override
    public List<ClientDetail> findByCreatedBy(String createdBy) {
        return this.findAll(Condition.NEW().eq("createdBy", createdBy));
    }

    //@RedisCachePut(key = "'feing-uaa:clientDetails:' + #clientDetail.clientId", expire = 600)
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindUser(String id, String username) {
        ClientDetail detail = this.findOne(Condition.NEW().eq("username", username));
        if (detail != null) {
            detail.setUsername(null);
            repository.save(detail);
        }
        repository.findById(id).ifPresent(clientDetail -> {
            clientDetail.setUsername(username);
            repository.save(clientDetail);
        });
    }

    //@RedisCacheEvict(key = "'feing-uaa:clientDetails:' + #clientId")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByClientId(String clientId) {
        super.delete(Condition.NEW().eq("clientId", clientId));
    }
}
