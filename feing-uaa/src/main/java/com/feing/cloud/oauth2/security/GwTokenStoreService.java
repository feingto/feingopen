package com.feing.cloud.oauth2.security;

import com.feing.cloud.oauth2.domain.AccessToken;
import com.feing.cloud.oauth2.domain.RefreshToken;
import com.feing.cloud.oauth2.service.impl.AccessTokenService;
import com.feing.cloud.oauth2.service.impl.RefreshTokenService;
import com.feing.cloud.orm.jpa.specification.bean.Condition;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 令牌存储服务实现
 *
 * @author longfei
 */
@Service
@Transactional
public class GwTokenStoreService implements TokenStore {
    @Resource(name = "accessTokenService")
    private AccessTokenService accessTokenService;

    @Resource(name = "refreshTokenService")
    private RefreshTokenService refreshTokenService;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String tokenId) {
        return Optional.ofNullable(accessTokenService.findOne(Condition.NEW()
                .eq("tokenId", tokenId)))
                .map(AccessToken::getAuthentication)
                .orElse(null);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String tokenId = token.getValue();
        String authenticationKey = authenticationKeyGenerator.extractKey(authentication);
        final RefreshToken refreshToken;

        if (token.getRefreshToken() != null) {
            refreshToken = Optional.ofNullable(refreshTokenService.findOne(Condition.NEW()
                    .eq("tokenId", token.getRefreshToken().getValue())))
                    .orElseGet(() -> refreshTokenService.save(RefreshToken.builder().tokenId(token.getRefreshToken().getValue())
                            .token(token.getRefreshToken()).authentication(authentication).build()));
        } else {
            refreshToken = null;
        }

        Optional.ofNullable(accessTokenService.findOne(Condition.NEW()
                .eq("authenticationId", authenticationKey)))
                .filter(accessToken -> !tokenId.equals(accessToken.getTokenId()))
                .ifPresent(accessToken -> accessTokenService.delete(accessToken.getTokenId()));

        AccessToken entityToSave = Optional.ofNullable(accessTokenService.findOne(Condition.NEW()
                .eq("tokenId", tokenId)))
                .map(accessToken -> {
                    accessToken.setToken(token);
                    accessToken.setAuthenticationId(authenticationKey);
                    accessToken.setAuthentication(authentication);
                    accessToken.setUserName(authentication.isClientOnly() ? null : authentication.getName());
                    accessToken.setClientId(authentication.getOAuth2Request().getClientId());
                    accessToken.setRefreshToken(refreshToken);
                    return accessToken;
                }).orElseGet(() -> AccessToken.builder().tokenId(tokenId).token(token)
                        .authenticationId(authenticationKey).authentication(authentication)
                        .userName(authentication.isClientOnly() ? null : authentication.getName())
                        .clientId(authentication.getOAuth2Request().getClientId())
                        .refreshToken(refreshToken)
                        .build());
        accessTokenService.save(entityToSave);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return Optional.ofNullable(accessTokenService.findOne(Condition.NEW()
                .eq("tokenId", tokenValue)))
                .map(AccessToken::getToken)
                .orElse(null);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        accessTokenService.delete(Condition.NEW().eq("tokenId", token.getValue()));
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        RefreshToken token = Optional.ofNullable(refreshTokenService.findOne(Condition.NEW()
                .eq("tokenId", refreshToken.getValue())))
                .map(rfToken -> {
                    rfToken.setToken(refreshToken);
                    rfToken.setAuthentication(authentication);
                    return rfToken;
                }).orElseGet(() -> RefreshToken.builder()
                        .tokenId(refreshToken.getValue())
                        .token(refreshToken)
                        .authentication(authentication)
                        .build());
        refreshTokenService.save(token);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return Optional.ofNullable(refreshTokenService.findOne(Condition.NEW()
                .eq("tokenId", tokenValue)))
                .map(RefreshToken::getToken)
                .orElse(null);
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return Optional.ofNullable(refreshTokenService.findOne(Condition.NEW()
                .eq("tokenId", token.getValue())))
                .map(RefreshToken::getAuthentication)
                .orElse(null);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        refreshTokenService.delete(Condition.NEW().eq("tokenId", token.getValue()));
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        accessTokenService.delete(Condition.NEW().eq("refreshTokenId", refreshToken.getValue()));
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String authenticationKey = authenticationKeyGenerator.extractKey(authentication);
        return Optional.ofNullable(accessTokenService.findOne(Condition.NEW()
                .eq("authenticationId", authenticationKey)))
                .map(AccessToken::getToken)
                .orElse(null);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return accessTokenService.findAll(Condition.NEW()
                .eq("clientId", clientId)
                .eq("userName", userName)).stream()
                .map(AccessToken::getToken)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return accessTokenService.findAll(Condition.NEW()
                .eq("clientId", clientId)).stream()
                .map(AccessToken::getToken)
                .collect(Collectors.toList());
    }
}
