package com.feingto.cloud.oauth2.web.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 授权控制器
 *
 * @author longfei
 */
@Slf4j
@Controller
@SessionAttributes("authorizationRequest")
public class AccessController {
    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 确认授权
     */
    @RequestMapping("/oauth/confirm_access")
    public String getAccessConfirmation(Map<String, Object> map) {
        AuthorizationRequest clientAuth = (AuthorizationRequest) map.remove("authorizationRequest");
        BaseClientDetails client = (BaseClientDetails) clientDetailsService.loadClientByClientId(clientAuth.getClientId());
        Map<String, String> scopes = Maps.newLinkedHashMap();
        for (String scope : client.getScope()) {
//            scopes.put(scope, clientAuth.getScope().contains(scope) ? "true" : "false");
            scopes.put(scope, client.getAutoApproveScopes().contains(scope) ? "true" : "false");
        }
        map.put("scopes", scopes);
        return "authorize";
    }

    @RequestMapping(value = "/oauth/error", produces = "text/html")
    public String handleError(HttpServletRequest request, Map<String, Object> map) {
        map.putAll(this.getErrorAttributes(request));
        map.put("status", Response.SC_BAD_REQUEST);
        return "oauth_error";
    }

    @RequestMapping("/oauth/error")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = this.getErrorAttributes(request);
        HttpStatus status = HttpStatus.valueOf(Response.SC_BAD_REQUEST);
        return new ResponseEntity<>(body, status);
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        Object error = request.getAttribute("error");
        if (error instanceof OAuth2Exception) {
            OAuth2Exception oauthError = (OAuth2Exception) error;
            log.error(oauthError.getSummary());
            map.put("httpErrorCode", oauthError.getHttpErrorCode());
            map.put("oAuth2ErrorCode", oauthError.getOAuth2ErrorCode());
            map.put("message", oauthError.getMessage());
        } else {
            map.put("message", "Unknown error");
        }
        return map;
    }
}
