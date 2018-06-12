package com.feing.cloud.remote.account.fallback;

import com.fasterxml.jackson.databind.JsonNode;
import com.feing.cloud.domain.account.Resource;
import com.feing.cloud.domain.account.ResourceButton;
import com.feing.cloud.domain.account.ResourceColumn;
import com.feing.cloud.orm.jpa.specification.bean.Condition;
import com.feing.cloud.remote.account.ResourceClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author longfei
 */
@Component
public class ResourceClientFallback implements ResourceClient {
    @Override
    public Resource get(String id) {
        return null;
    }

    @Override
    public List<Resource> list(Condition condition) {
        return null;
    }

    @Override
    public List<Resource> tree() {
        return null;
    }

    @Override
    public JsonNode save(Resource res) {
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
    public void buttonSave(String id, List<ResourceButton> buttons) {
    }

    @Override
    public void columnSave(String id, List<ResourceColumn> columns) {
    }

    @Override
    public JsonNode sort(String data) {
        return null;
    }
}
