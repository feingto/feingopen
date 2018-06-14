package com.feingto.cloud.remote.account.fallback;

import com.fasterxml.jackson.databind.JsonNode;
import com.feingto.cloud.domain.account.Role;
import com.feingto.cloud.orm.jpa.page.ConditionPage;
import com.feingto.cloud.orm.jpa.page.Page;
import com.feingto.cloud.remote.account.RoleClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author longfei
 */
@Component
public class RoleClientFallback implements RoleClient {
    @Override
    public Role get(String id) {
        return null;
    }

    @Override
    public Page list(ConditionPage page) {
        return null;
    }

    @Override
    public JsonNode save(Role role) {
        return null;
    }

    @Override
    public JsonNode delete(String id) {
        return null;
    }

    @Override
    public JsonNode enable(String id) {
        return null;
    }

    @Override
    public JsonNode disable(String id) {
        return null;
    }

    @Override
    public Map<String, Object> resList(String roleId) {
        return null;
    }

    @Override
    public void resSave(String roleId, Map<String, Object> map) {
    }
}
