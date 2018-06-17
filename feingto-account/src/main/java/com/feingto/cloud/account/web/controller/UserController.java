package com.feingto.cloud.account.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.feingto.cloud.account.service.IUser;
import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.core.web.WebResult;
import com.feingto.cloud.domain.account.Resource;
import com.feingto.cloud.domain.account.User;
import com.feingto.cloud.orm.jpa.page.ConditionPage;
import com.feingto.cloud.orm.jpa.page.Page;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 用户管理
 *
 * @author longfei
 */
@RestController
@RequestMapping(Constants.BASE_API + "/account/users")
public class UserController {
    @Autowired
    private IUser userService;

    @GetMapping("/{username}/get")
    public User user(@PathVariable String username) {
        return userService.findOne(Condition.NEW().eq("username", username));
    }

    @GetMapping("/{id}")
    public User get(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping("/{id}/res")
    public List<Resource> loadResources(@PathVariable String id) {
        return userService.parseUserResource(id, null);
    }

    @GetMapping
    public Page list(@RequestBody(required = false) ConditionPage<User> page) {
        if (page == null) {
            page = new ConditionPage<>();
        }
        return userService.findByPage(page.getCondition(), page);
    }

    @PostMapping
    public JsonNode save(@RequestBody User user) {
        userService.save(user);
        return WebResult.success().putPOJO("user", user);
    }

    @DeleteMapping("/{id}")
    public JsonNode delete(@PathVariable String id) {
        userService.delete(id);
        return WebResult.success();
    }

    @PostMapping("/{id}/{property}")
    public JsonNode updateByProperty(@PathVariable String id, @PathVariable String property,
                                     @RequestParam Object value) {
        if (value.equals("true") || value.equals("false"))
            value = Boolean.parseBoolean(String.valueOf(value));
        userService.updateByProperty(id, property, value);
        return WebResult.success();
    }

    /**
     * 分页获取用户，指定用户在最前面
     *
     * @param page      Page
     * @param userNames 前置用户名逗号分隔字符串
     * @param keyword   关键字
     */
    @PostMapping("/preposition")
    public Page<User> findPageByUsername(@RequestBody Page<User> page, String userNames, String keyword) {
        Set<String> users = Sets.newHashSet(Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .splitToList(Optional.ofNullable(userNames)
                        .orElse("")));
        return userService.findPageByUsers(page, users, keyword);
    }
}
