package com.feingto.cloud.oauth2.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author longfei
 */
@RestController
@RequestMapping("/resources")
public class ResourceController {
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("user")
    public String helloUser() {
        return "hello user";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("admin")
    public String helloAdmin() {
        return "hello admin";
    }

    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @GetMapping("client")
    public String helloClient() {
        return "hello user authenticated by normal client";
    }

    @PreAuthorize("hasAuthority('AUTH_API')")
    @GetMapping("api")
    public String helloApi() {
        return "hello api authenticated by api client";
    }

    @GetMapping("principal")
    public Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("roles")
    public Object getRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
