package com.feing.cloud.oauth2.security;

import com.feing.cloud.oauth2.domain.*;
import com.feing.cloud.oauth2.service.IClientDetail;
import com.feing.cloud.oauth2.service.impl.AuthorityService;
import com.feing.cloud.oauth2.service.impl.GrantTypeService;
import com.feing.cloud.oauth2.service.impl.ResourceIdService;
import com.feing.cloud.oauth2.service.impl.ScopeService;
import com.feing.cloud.orm.jpa.specification.bean.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * 客户端服务扩展
 *
 * @author longfei
 */
@Slf4j
@Service
public class GwClientDetailsService implements ClientDetailsService, ClientRegistrationService {
    @Resource(name = "clientDetailCacheService")
    private IClientDetail clientDetailService;

    @Resource
    private GrantTypeService grantTypeService;

    @Resource
    private ScopeService scopeService;

    @Resource
    private AuthorityService authorityService;

    @Resource
    private ResourceIdService resourceIdService;

    private BiFunction<? super ClientDetails, ClientDetail, ClientDetail> reverse = (clientDetails, clientDetail) -> {
        BeanUtils.copyProperties(clientDetails, clientDetail);

        clientDetail.getClientDetailGrantTypes().clear();
        clientDetail.getClientDetailGrantTypes().addAll(clientDetails.getAuthorizedGrantTypes().stream()
                .map(value -> Optional.of(grantTypeService.findOne(Condition.NEW().eq("value", value)))
                        .map(grantType -> ClientDetailGrantType.builder().clientDetail(clientDetail).grantType(grantType).build())
                        .orElseThrow(() -> new ClientRegistrationException("Unsupported grant type: " + value)))
                .collect(Collectors.toSet()));

        clientDetail.getClientDetailScopes().clear();
        clientDetail.getClientDetailScopes().addAll(clientDetails.getScope().stream()
                .map(value -> Optional.of(scopeService.findOne(Condition.NEW().eq("value", value)))
                        .map(scope -> ClientDetailScope.builder().clientDetail(clientDetail).scope(scope).autoApprove(clientDetails.isAutoApprove(value)).build())
                        .orElseThrow(() -> new ClientRegistrationException("Unknown scope: " + value)))
                .collect(Collectors.toSet()));

        clientDetail.getClientDetailResourceIds().clear();
        clientDetail.getClientDetailResourceIds().addAll(clientDetails.getResourceIds().stream()
                .map(value -> Optional.ofNullable(resourceIdService.findOne(Condition.NEW().eq("value", value)))
                        .map(resourceId -> ClientDetailResourceId.builder().clientDetail(clientDetail).resourceId(resourceId).build())
                        .orElseThrow(() -> new ClientRegistrationException("Unknown resource id: " + value)))
                .collect(Collectors.toSet()));

        clientDetail.getClientDetailAuthorities().clear();
        clientDetail.getClientDetailAuthorities().addAll(clientDetails.getAuthorities().stream()
                .map(grantedAuthority -> Optional.ofNullable(authorityService.findOne(Condition.NEW().eq("value", grantedAuthority.getAuthority())))
                        .map(authority -> ClientDetailAuthority.builder().clientDetail(clientDetail).authority(authority).build())
                        .orElseThrow(() -> new ClientRegistrationException("Unknown authority name: " + grantedAuthority.getAuthority())))
                .collect(Collectors.toSet()));

        clientDetail.getRedirectUris().clear();
        clientDetail.getRedirectUris().addAll(clientDetails.getRegisteredRedirectUri().stream()
                .map(value -> RedirectUri.builder().clientDetail(clientDetail).value(value).build())
                .collect(Collectors.toSet()));
        return clientDetail;
    };

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.debug(">>> Loading ClientDetails by clientId: {}", clientId);
        return Optional.ofNullable(clientDetailService.findByClientId(clientId))
                .map(ClientDetailHelper.transform)
                .orElseThrow(() -> new NoSuchClientException("Client ID not found."));
    }

    @Override
    @Transactional
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        Optional.ofNullable(clientDetails)
                .map(client -> this.reverse.apply(client, ClientDetail.builder().build()))
                .ifPresent(clientDetailService::save);
    }

    @Override
    @Transactional
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        ClientDetail clientDetail = Optional.ofNullable(clientDetailService.findOne(Condition.NEW()
                .eq("clientId", clientDetails.getClientId())))
                .orElseThrow(() -> new NoSuchClientException("Client details not found."));
        Optional.of(clientDetails)
                .map(client -> this.reverse.apply(client, clientDetail))
                .ifPresent(clientDetailService::save);
    }

    @Override
    @Transactional
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        ClientDetail clientDetail = Optional.ofNullable(clientDetailService.findOne(Condition.NEW()
                .eq("clientId", clientId)))
                .orElseThrow(() -> new NoSuchClientException("Client id not found."));
        clientDetail.setClientSecret(secret);
        clientDetailService.save(clientDetail);
    }

    @Override
    @Transactional
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        if (!Optional.ofNullable(clientDetailService.findOne(Condition.NEW()
                .eq("clientId", clientId)))
                .isPresent()) {
            throw new NoSuchClientException("Client id not found.");
        }
        clientDetailService.deleteByClientId(clientId);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return clientDetailService.findAll().stream()
                .map(ClientDetailHelper.transform)
                .collect(Collectors.toList());
    }
}
