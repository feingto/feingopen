package com.feing.cloud.remote.account.fallback;

import com.fasterxml.jackson.databind.JsonNode;
import com.feing.cloud.domain.account.Resource;
import com.feing.cloud.domain.account.User;
import com.feing.cloud.dto.oauth.ClientDetailApiDTO;
import com.feing.cloud.orm.jpa.page.ConditionPage;
import com.feing.cloud.orm.jpa.page.Page;
import com.feing.cloud.remote.account.UserClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author longfei
 */
@Component
public class UserClientFallback implements UserClient {
    @Override
    public User user(String username) {
        return null;
    }

    @Override
    public User get(String id) {
        return null;
    }

    @Override
    public List<Resource> loadResources(String id) {
        return null;
    }

    @Override
    public Page data(ConditionPage page) {
        return null;
    }

    @Override
    public Page list(ConditionPage page) {
        return null;
    }

    @Override
    public JsonNode save(User user) {
        return null;
    }

    @Override
    public JsonNode delete(String id) {
        return null;
    }

    @Override
    public JsonNode updateByProperty(String id, String property, Object value) {
        return null;
    }

    @Override
    public Page<User> findPageByUsername(Page<User> page, String username, String keyword) {
        return null;
    }

    @Override
    public List<ClientDetailApiDTO> findOAuthApisByUsername(String username) {
        return null;
    }
}
