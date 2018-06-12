package com.feing.cloud.account.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.feing.cloud.core.json.JSON;
import com.feing.cloud.core.web.Constants;
import com.feing.cloud.core.web.WebResult;
import com.feing.cloud.domain.account.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 登录
 *
 * @author longfei
 */
@RestController
@RequestMapping(Constants.BASE_API)
public class LoginController {
    @Autowired
    private UserController userController;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @PostMapping("/login")
    public JsonNode login(@RequestParam String username, @RequestParam String password) {
        return Optional.ofNullable(userController.user(username))
                .map(user -> {
                    String errorString = this.parseException(user, password);
                    if (StringUtils.hasText(errorString)) {
                        return WebResult.error(errorString);
                    }
                    return JSON.build().JSONObject().put("token", restTemplate.getAccessToken().getValue());
                })
                .orElse(WebResult.error("账户不存在."));
    }

    @GetMapping("/user")
    public User user(@RequestParam String username) {
        User user = userController.user(username);
        // 加载用户资源
        user.setResources(userController.loadResources(user.getId()));
        return user;
    }

    private String parseException(User user, String password) {
        String errorString = null;
        if (user != null) {
            if (!user.isEnabled()) {
                errorString = "账户已禁用.";
            } else if (!StringUtils.isEmpty(password) && !DigestUtils.md5Hex(password).equals(user.getPassword())) {
                errorString = "密码错误.";
            }
        } else {
            errorString = "账户不存在.";
        }
        return errorString;
    }
}
