package com.feingto.cloud.oauth2.security;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 重写JwtAccessTokenConverter的enhance方法, 增加自定义token属性(合并用户角色和客户端权限, 扩展API权限)
 *
 * @author longfei
 */
public class GwJwtAccessTokenConverter extends JwtAccessTokenConverter {
    @Autowired
    private GwClientDetailsService clientDetailsService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInformation = Maps.newHashMap();
        Set<String> authorities = Sets.newLinkedHashSet();
        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser) {
            SecurityUser user = (SecurityUser) principal;
            authorities.addAll(user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet()));
        }
        String clientId = authentication.getOAuth2Request().getClientId();
        ClientDetails details = clientDetailsService.loadClientByClientId(clientId);
        authorities.addAll(details.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));
        additionalInformation.put("authorities", authorities);
        if (details.getAdditionalInformation().containsKey("apiIds")) {
            //additionalInformation.put(OAuth2AccessToken.SCOPE, details.getAdditionalInformation().get("apiIds"));
            additionalInformation.put("apiIds", details.getAdditionalInformation().get("apiIds"));
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        return super.enhance(accessToken, authentication);
    }
}
