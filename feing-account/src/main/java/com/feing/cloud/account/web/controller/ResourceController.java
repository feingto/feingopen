package com.feing.cloud.account.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.feing.cloud.account.service.IResource;
import com.feing.cloud.core.web.Constants;
import com.feing.cloud.core.web.WebResult;
import com.feing.cloud.domain.account.Resource;
import com.feing.cloud.domain.account.ResourceButton;
import com.feing.cloud.domain.account.ResourceColumn;
import com.feing.cloud.orm.jpa.specification.bean.Condition;
import com.feing.cloud.orm.jpa.specification.bean.OrderSort;
import com.google.common.collect.Lists;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源菜单管理
 *
 * @author longfei
 */
@RestController
@RequestMapping(Constants.BASE_API + "/account/res")
public class ResourceController {
    @Autowired
    private IResource resourceService;

    @GetMapping("/{id}")
    public Resource get(@PathVariable String id) {
        resourceService.setLazyInitializer(resource -> {
            Hibernate.initialize(resource.getButtons());
            Hibernate.initialize(resource.getColumns());
        });
        return resourceService.findById(id);
    }

    @GetMapping
    public List<Resource> list(@RequestBody Condition condition) {
        resourceService.setLazyInitializer(res -> {
            Hibernate.initialize(res.getButtons());
            Hibernate.initialize(res.getColumns());
        });
        return resourceService.findAll(condition, new OrderSort("location"));
    }

    @GetMapping("/tree")
    public List<Resource> tree() {
        List<Resource> resources = Lists.newArrayList();
        resourceService.setLazyInitializer(res -> {
            Hibernate.initialize(res.getButtons());
            Hibernate.initialize(res.getColumns());
        });
        List<Resource> resourceList = resourceService.findAll(new OrderSort("location"));
        resourceList.forEach(res -> {
            if ("-1".equals(res.getParentId()))
                resources.add(res);
            resourceList.stream()
                    .filter(child -> child.getParentId().equals(res.getId()))
                    .forEach(child -> res.getChildren().add(child));
        });
        return resources;
    }

    @PostMapping
    public JsonNode saveOrUpdate(@RequestBody Resource res) {
        if (StringUtils.isEmpty(res.getParentId())) {
            res.setParentId("-1");
        }
        if (!res.isNew()) {
            resourceService.setLazyInitializer(resource -> {
                Hibernate.initialize(resource.getButtons());
                Hibernate.initialize(resource.getColumns());
                Hibernate.initialize(resource.getRoles());
            });
            Resource eqRes = resourceService.findById(res.getId());
            res.setButtons(eqRes.getButtons());
            res.setColumns(eqRes.getColumns());
            res.setRoles(eqRes.getRoles());
        }
        resourceService.save(res);
        return WebResult.success().putPOJO("res", res);
    }

    @DeleteMapping("/{id}")
    public JsonNode delete(@PathVariable String id) {
        resourceService.delete(id);
        return WebResult.success();
    }

    @PostMapping("/{id}/{property}")
    public JsonNode updateByProperty(@PathVariable String id, @PathVariable("property") String property,
                                     @RequestParam("value") Object value) {
        if (value.equals("true") || value.equals("false"))
            value = Boolean.parseBoolean(String.valueOf(value));
        resourceService.updateByProperty(id, property, value);
        return WebResult.success();
    }

    @PostMapping("/{id}/button")
    public JsonNode buttonSave(@PathVariable String id, @RequestBody List<ResourceButton> buttons) {
        resourceService.setLazyInitializer(resource -> Hibernate.initialize(resource.getButtons()));
        Resource res = resourceService.findById(id);
        res.getButtons().clear();
        res.getButtons().addAll(buttons);
        res.getButtons().forEach(button -> button.setResource(res));
        resourceService.save(res);
        return WebResult.success();
    }

    @PostMapping("/{id}/column")
    public JsonNode columnSave(@PathVariable String id, @RequestBody List<ResourceColumn> columns) {
        resourceService.setLazyInitializer(resource -> Hibernate.initialize(resource.getColumns()));
        Resource res = resourceService.findById(id);
        res.getColumns().clear();
        res.getColumns().addAll(columns);
        res.getColumns().forEach(column -> column.setResource(res));
        resourceService.save(res);
        return WebResult.success();
    }

    /**
     * 排序
     *
     * @param data ResourceId TreeNode [id: 1, children: [{id: 12}, {id: 12}], id: 2]
     */
    @PostMapping("/sort")
    public JsonNode sort(String data) {
        resourceService.executeSort(data);
        return WebResult.success();
    }
}
