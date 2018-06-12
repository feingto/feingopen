package com.feing.cloud.oauth2.security;

import com.feing.cloud.dto.oauth.ClientDetailApiDTO;
import com.feing.cloud.dto.oauth.ClientDetailDTO;
import com.feing.cloud.dto.oauth.ClientDetailLimitDTO;
import com.feing.cloud.oauth2.domain.ClientDetail;
import com.feing.cloud.oauth2.domain.ClientDetailLimit;
import com.feing.cloud.oauth2.domain.ClientDetailScope;
import com.feing.cloud.oauth2.domain.RedirectUri;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 客户端工具辅助
 *
 * @author longfei
 */
public class ClientDetailHelper {
    private static final String AUTHORITY_PREFIX = "AUTH_";

    public static Function<? super ClientDetail, ? extends BaseClientDetails> transform = clientDetail -> {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientDetail.getClientId());
        clientDetails.setClientSecret(clientDetail.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(clientDetail.getAccessTokenValiditySeconds());
        clientDetails.setRefreshTokenValiditySeconds(clientDetail.getRefreshTokenValiditySeconds());
        if (clientDetail.getClientDetailGrantTypes() != null) {
            clientDetails.setAuthorizedGrantTypes(clientDetail.getClientDetailGrantTypes().stream()
                    .map(clientDetailGrantType -> clientDetailGrantType.getGrantType().getValue())
                    .collect(Collectors.toList()));
        }
        if (clientDetail.getClientDetailScopes() != null) {
            clientDetails.setScope(clientDetail.getClientDetailScopes().stream()
                    .map(clientDetailScope -> clientDetailScope.getScope().getValue())
                    .collect(Collectors.toList()));
            clientDetails.setAutoApproveScopes(clientDetail.getClientDetailScopes().stream()
                    .filter(ClientDetailScope::getAutoApprove)
                    .map(clientDetailScope -> clientDetailScope.getScope().getValue())
                    .collect(Collectors.toList()));
        }
        if (clientDetail.getClientDetailResourceIds() != null) {
            clientDetails.setResourceIds(clientDetail.getClientDetailResourceIds().stream()
                    .map(clientDetailResourceId -> clientDetailResourceId.getResourceId().getValue())
                    .collect(Collectors.toList()));
        }
        if (clientDetail.getRedirectUris() != null) {
            clientDetails.setRegisteredRedirectUri(clientDetail.getRedirectUris().stream()
                    .map(RedirectUri::getValue)
                    .collect(Collectors.toSet()));
        }
        if (clientDetail.getClientDetailAuthorities() != null) {
            clientDetails.setAuthorities(clientDetail.getClientDetailAuthorities().stream()
                    .map(clientDetailAuthority -> new SimpleGrantedAuthority(prefixAuthorityName(
                            clientDetailAuthority.getAuthority().getValue())))
                    .collect(Collectors.toList()));
        }
        if (clientDetail.getClientDetailApis() != null) {
            Map<String, Set<ClientDetailApiDTO>> additionalInformations = Maps.newLinkedHashMap();
            additionalInformations.put("apiIds", clientDetail.getClientDetailApis().stream()
                    .map(clientDetailApi -> ClientDetailApiDTO.builder()
                            .apiId(clientDetailApi.getApiId())
                            .stage(clientDetailApi.getStage())
                            .build())
                    .collect(Collectors.toSet()));
            clientDetails.setAdditionalInformation(additionalInformations);
        } else {
            clientDetails.setAdditionalInformation(Collections.emptyMap());
        }
        return clientDetails;
    };

    public static Function<? super ClientDetail, ? extends ClientDetailDTO> transform2dto = clientDetail -> {
        ClientDetailDTO clientDetailDto = ClientDetailDTO.builder().build();
        BeanUtils.copyProperties(clientDetail, clientDetailDto);
        if (clientDetail.getClientDetailGrantTypes() != null) {
            clientDetailDto.setGrantTypes(clientDetail.getClientDetailGrantTypes().stream()
                    .map(clientDetailGrantType -> clientDetailGrantType.getGrantType().getValue())
                    .collect(Collectors.toSet()));
        }
        if (clientDetail.getClientDetailScopes() != null) {
            clientDetailDto.setScopes(clientDetail.getClientDetailScopes().stream()
                    .map(clientDetailScope -> clientDetailScope.getScope().getValue())
                    .collect(Collectors.toSet()));
        }
        if (clientDetail.getClientDetailScopes() != null) {
            clientDetailDto.setAutoScopes(clientDetail.getClientDetailScopes().stream()
                    .filter(ClientDetailScope::getAutoApprove)
                    .map(clientDetailScope -> clientDetailScope.getScope().getValue())
                    .collect(Collectors.toSet()));
        }
        if (clientDetail.getClientDetailResourceIds() != null) {
            clientDetailDto.setResourceIds(clientDetail.getClientDetailResourceIds().stream()
                    .map(clientDetailResourceId -> clientDetailResourceId.getResourceId().getValue())
                    .collect(Collectors.toSet()));
        }
        if (clientDetail.getRedirectUris() != null) {
            clientDetailDto.setRedirectUris(clientDetail.getRedirectUris().stream()
                    .map(RedirectUri::getValue)
                    .collect(Collectors.joining(System.lineSeparator())));
        }
        if (clientDetail.getClientDetailAuthorities() != null) {
            clientDetailDto.setAuthorities(clientDetail.getClientDetailAuthorities().stream()
                    .filter(clientDetailAuthority -> clientDetailAuthority.getAuthority().isEnabled())
                    .map(clientDetailAuthority -> eliminateAuthorityName(clientDetailAuthority.getAuthority().getValue()))
                    .collect(Collectors.toSet()));
        }
        if (clientDetail.getClientDetailApis() != null) {
            clientDetailDto.setApis(clientDetail.getClientDetailApis().stream()
                    .map(clientDetailApi -> ClientDetailApiDTO.builder()
                            .apiId(clientDetailApi.getApiId())
                            .stage(clientDetailApi.getStage())
                            .build())
                    .collect(Collectors.toSet()));
        }
        if (clientDetail.getClientLimit() != null) {
            ClientDetailLimit clientLimit = clientDetail.getClientLimit();
            clientDetailDto.setClientLimit(ClientDetailLimitDTO.builder()
                    .limits(clientLimit.getLimits())
                    .frequency(clientLimit.getFrequency())
                    .intervalUnit(clientLimit.getIntervalUnit())
                    .build());
        }
        return clientDetailDto;
    };

    private static String prefixAuthorityName(String authorityName) {
        if (StringUtils.isNoneBlank(authorityName) && !authorityName.startsWith(AUTHORITY_PREFIX)) {
            return AUTHORITY_PREFIX + authorityName;
        }
        return authorityName;
    }

    private static String eliminateAuthorityName(String authorityName) {
        if (StringUtils.isNoneBlank(authorityName) && authorityName.startsWith(AUTHORITY_PREFIX)) {
            return StringUtils.substringAfter(authorityName, AUTHORITY_PREFIX);
        }
        return authorityName;
    }
}
