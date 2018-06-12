package com.feing.cloud.oauth2.web.controller;

import com.feing.cloud.core.json.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * @author longfei
 */
@Slf4j
@Controller
public class IndexController {
    @GetMapping("/")
    @ResponseBody
    public Principal index(HttpServletRequest request) {
        return request.getUserPrincipal();
    }

    @RequestMapping(value = "/user")
    @ResponseBody
    public Principal user(Principal user, @RequestHeader(value = "Authorization") String authorization) {
        log.debug("Principal: {}, token: {}", JSON.build().obj2json(user), authorization);
        return user;
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
