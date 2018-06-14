package com.feingto.cloud.account.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.feingto.cloud.account.repository.ResourceRepository;
import com.feingto.cloud.account.service.IResource;
import com.feingto.cloud.core.json.JSON;
import com.feingto.cloud.domain.account.Resource;
import com.feingto.cloud.orm.jpa.BaseService;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author longfei
 */
@Service
public class ResourceService extends BaseService<Resource, String> implements IResource {
    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resource save(Resource resource) {
        Assert.notNull(resource.getName(), "菜单名称不能为空.");
        if (resource.isNew()) {
            Assert.state(super.count(Condition.NEW().eq("sn", resource.getSn())) == 0, "菜单标识\"" + resource.getSn() + "\"已存在.");
            resource.setCode(super.createCode(resource.getParentId()));
            long location = super.count(Condition.NEW().eq("parentId", resource.getParentId())) + 1;
            resource.setLocation((int) location);
            resourceRepository.save(resource);
        } else {
            Assert.state(super.count(Condition.NEW().ne("id", resource.getId()).eq("sn", resource.getSn())) == 0, "菜单标识\"" + resource.getSn() + "\"已存在.");
            Resource eqRes = this.findById(resource.getId());
            // 修改前的父节点
            String oldPid = eqRes.getParentId();
            if (!oldPid.equals(resource.getParentId())) {
                // 父节点变更时修改code
                resource.setCode(super.createCode(resource.getParentId()));
            }
            resourceRepository.save(resource);
            if (!oldPid.equals(resource.getParentId())) {
                // 父节点变更时修改原父节点的孩子状态和原子节点的code
                boolean hasChildren = super.count(Condition.NEW().eq("parentId", oldPid)) > 0;
                // 更新修改前的父节点是否有子节点的状态为 true
                super.updateByProperty(oldPid, "hasChildren", hasChildren);
                // 更新子节点的code
                List<Resource> resourceList = super.findAll(Condition.NEW().eq("parentId", resource.getId()), null);
                for (int i = 0; i < resourceList.size(); i++) {
                    super.updateByProperty(resourceList.get(i).getId(), "code", resource.getCode() + String.format("%03d", i + 1));
                }
            }
            boolean hasChildren = super.count(Condition.NEW().eq("parentId", resource.getId())) > 0;
            // 更新该节点是否有子节点的状态为 true
            super.updateByProperty(resource.getId(), "hasChildren", hasChildren);
        }
        if (!"-1".equals(resource.getParentId())) {
            // 更新父节点是否有子节点的状态为 true
            super.updateByProperty(resource.getParentId(), "hasChildren", true);
        }
        return resource;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeSort(String data) {
        this._executeSort(JSON.build().read(data), "-1", "");
    }

    private void _executeSort(JsonNode resList, String parentId, String codePrefix) {
        for (int i = 0; i < resList.size(); ++i) {
            JsonNode res = resList.get(i);
            String id = res.get("id").asText();
            String code = codePrefix + String.format("%03d", i + 1);
            Resource resource = resourceRepository.getOne(id);
            resource.setCode(code);
            resource.setLocation(i + 1);
            resource.setParentId(parentId);
            resource.setId(id);
            if (res.has("children")) {
                resource.setHasChildren(true);
                resourceRepository.save(resource);
                this._executeSort(res.path("children"), id, code);
            } else {
                resource.setHasChildren(false);
                resourceRepository.save(resource);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Resource resource = resourceRepository.getOne(id);
        List<Resource> resources = super.findAll(Condition.NEW().slike("code", resource.getCode()));
        for (Resource resource1 : resources) {
            resourceRepository.deleteById(resource1.getId());
        }
        if (super.count(Condition.NEW().eq("parentId", resource.getParentId())) == 0) {
            // 更新父节点的是否有子节点状态为 false
            super.updateByProperty(resource.getParentId(), "hasChildren", false);
        }
    }
}
