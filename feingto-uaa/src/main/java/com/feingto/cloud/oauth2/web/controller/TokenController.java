package com.feingto.cloud.oauth2.web.controller;

import com.feingto.cloud.core.json.JSON;
import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.dto.oauth.AccessTokenDTO;
import com.feingto.cloud.dto.oauth.ClientDetailDTO;
import com.feingto.cloud.dto.oauth.OAuth2TokenDTO;
import com.feingto.cloud.oauth2.domain.ClientDetail;
import com.feingto.cloud.oauth2.security.ClientDetailHelper;
import com.feingto.cloud.oauth2.service.IClientDetail;
import com.feingto.cloud.orm.jpa.page.Page;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * Token管理控制器
 *
 * @author longfei
 */
@RestController
@RequestMapping(Constants.BASE_API + "/tokens")
@SuppressWarnings("unchecked")
public class TokenController {
    @Resource(name = "consumerTokenServices")
    private ConsumerTokenServices tokenServices;

    @Autowired
    private TokenStore tokenStore;

    @Resource(name = "clientDetailCacheService")
    private IClientDetail clientDetailService;

    @PostMapping("/data")
    public Page data(@RequestBody Page page) {
        page = clientDetailService.findByPage(page);
        List<ClientDetail> clientDetailList = page.getContent();
        List<ClientDetailDTO> clientDetailDtoList = Lists.newArrayList();
        clientDetailList.forEach(clientDetail -> clientDetailDtoList.add(ClientDetailHelper.transform2dto.apply(clientDetail)));
        clientDetailDtoList.forEach(clientDetailDto -> clientDetailDto.setAccessTokens(this.enhance(tokenStore.findTokensByClientId(clientDetailDto.getClientId()))));
        page.setContent(clientDetailDtoList);
        return page;
    }

    private Collection<AccessTokenDTO> enhance(Collection<OAuth2AccessToken> tokens) {
        Collection<AccessTokenDTO> result = Lists.newArrayList();
        for (OAuth2AccessToken accessToken : tokens) {
            AccessTokenDTO accessTokenDto = AccessTokenDTO.builder().build();
            BeanUtils.copyProperties(accessToken, accessTokenDto);
            DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
            OAuth2Authentication authentication = tokenStore.readAuthentication(defaultOAuth2AccessToken);
            if (authentication != null) {
                String userName = StringUtils.hasText(authentication.getName()) ? authentication.getName() : "Unknown";
                defaultOAuth2AccessToken.getAdditionalInformation().put("username", userName);
                accessTokenDto.setAdditionalInformations(defaultOAuth2AccessToken.getAdditionalInformation());
            }
            accessTokenDto.setAuth2Token(JSON.build().json2pojo(accessToken.getValue(), OAuth2TokenDTO.class));
        }
        return result;
    }

    @PostMapping("/revoke")
    public Boolean revokeToken(@RequestParam String user, @RequestParam String token, Principal principal) {
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication authentication = (OAuth2Authentication) principal;
            if (!authentication.isClientOnly() && !user.equals(principal.getName())) {
                throw new AccessDeniedException(String.format("User '%s' cannot obtain tokens for user '%s'", principal.getName(), user));
            }
        }
        return tokenServices.revokeToken(token);
    }
}
