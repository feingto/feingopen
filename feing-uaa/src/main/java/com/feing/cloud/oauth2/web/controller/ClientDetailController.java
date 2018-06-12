package com.feing.cloud.oauth2.web.controller;

import com.feing.cloud.core.web.Constants;
import com.feing.cloud.domain.type.IntervalUnit;
import com.feing.cloud.domain.type.Stage;
import com.feing.cloud.dto.oauth.ClientDetailApiDTO;
import com.feing.cloud.dto.oauth.ClientDetailDTO;
import com.feing.cloud.dto.oauth.ClientDetailLimitDTO;
import com.feing.cloud.kit.StringKit;
import com.feing.cloud.oauth2.domain.*;
import com.feing.cloud.oauth2.security.ClientDetailHelper;
import com.feing.cloud.oauth2.security.GwClientDetailsService;
import com.feing.cloud.oauth2.service.IClientDetail;
import com.feing.cloud.oauth2.service.impl.AuthorityService;
import com.feing.cloud.oauth2.service.impl.ClientDetailApiService;
import com.feing.cloud.oauth2.service.impl.GrantTypeService;
import com.feing.cloud.oauth2.service.impl.ScopeService;
import com.feing.cloud.orm.jpa.page.Page;
import com.feing.cloud.orm.jpa.specification.bean.Condition;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 客户端管理控制器
 *
 * @author longfei
 */
@RestController
@RequestMapping(Constants.BASE_API + "/client")
@SuppressWarnings("unchecked")
public class ClientDetailController {
    @Resource(name = "clientDetailCacheService")
    private IClientDetail clientDetailService;

    @Resource
    private GrantTypeService grantTypeService;

    @Resource
    private ScopeService scopeService;

    @Resource
    private AuthorityService authorityService;

    @Resource
    private ClientDetailApiService clientDetailApiService;

    @Autowired
    private GwClientDetailsService clientDetailsService;

    @GetMapping("/findByClientId")
    public ClientDetailDTO findByClientId(@RequestParam String clientId) {
        return Optional.ofNullable(clientDetailService.findByClientId(clientId))
                .map(ClientDetailHelper.transform2dto)
                .orElseThrow(() -> new NoSuchClientException("Client ID not found."));
    }

    @GetMapping("/findByUsername")
    public ClientDetailDTO findByUsername(@RequestParam String username) {
        return Optional.ofNullable(clientDetailService.findByUsername(username))
                .map(ClientDetailHelper.transform2dto)
                .orElse(null);
    }

    @GetMapping("/findByCreatedBy")
    public List<ClientDetailDTO> findByCreatedBy(@RequestParam String createdBy) {
        return clientDetailService.findByCreatedBy(createdBy).stream()
                .map(ClientDetailHelper.transform2dto)
                .collect(Collectors.toList());
    }

    @GetMapping("/findByApi")
    public List<ClientDetailApiDTO> findByApi(@RequestParam String apiId, String createdBy) {
        Condition condition = Condition.NEW().eq("apiId", apiId);
        if (StringUtils.hasLength(createdBy)) {
            condition = condition.eq("clientDetail.createdBy", createdBy);
        }
        return clientDetailApiService.findAll(condition).stream()
                .map(clientDetailApi -> ClientDetailApiDTO.builder()
                        .clientDetail(ClientDetailHelper.transform2dto.apply(clientDetailApi.getClientDetail()))
                        .apiId(clientDetailApi.getApiId())
                        .stage(clientDetailApi.getStage())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/limit")
    public ClientDetailLimitDTO findClientDetailLimit(@RequestParam String clientId) {
        clientDetailService.setLazyInitializer(clientDetail -> Hibernate.initialize(clientDetail.getClientLimit()));
        return Optional.ofNullable(clientDetailService.findByClientId(clientId))
                .filter(clientDetail -> clientDetail.getClientLimit() != null)
                .map(ClientDetail::getClientLimit)
                .map(clientDetailLimit -> ClientDetailLimitDTO.builder()
                        .limits(clientDetailLimit.getLimits())
                        .frequency(clientDetailLimit.getFrequency())
                        .intervalUnit(clientDetailLimit.getIntervalUnit())
                        .build())
                .orElseThrow(() -> new NoSuchClientException("Client ID not found."));
    }

    @GetMapping("/grantTypes")
    public List<String> grantTypes() {
        return grantTypeService.findAll().stream()
                .map(GrantType::getValue)
                .collect(Collectors.toList());
    }

    @GetMapping("/scopes")
    public List<String> scopes() {
        return scopeService.findAll().stream()
                .map(Scope::getValue)
                .collect(Collectors.toList());
    }

    @GetMapping("/authorities")
    public List<String> authorities() {
        return authorityService.findAll().stream()
                .map(Authority::getValue)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDetailDTO findOne(@PathVariable String id) {
        return Optional.ofNullable(clientDetailService.findById(id))
                .map(ClientDetailHelper.transform2dto)
                .orElse(null);
    }

    @RequestMapping("/data")
    public Page data(@RequestBody Page page, String username) {
        Condition condition = Condition.NEW();
        if (StringUtils.hasLength(username)) {
            condition.eq("username", username);
        }
        page = clientDetailService.findByPage(condition, page);
        List<ClientDetail> clientDetailList = page.getContent();
        page.setContent(clientDetailList.stream()
                .map(ClientDetailHelper.transform2dto)
                .collect(Collectors.toList()));
        return page;
    }

    @PostMapping("/save")
    public ClientDetailDTO saveOrUpdate(@RequestBody ClientDetailDTO clientDetailDto,
                                        @RequestParam(defaultValue = "false") boolean autoApproveAll) {
        BaseClientDetails baseClientDetails = new BaseClientDetails();
        BeanUtils.copyProperties(clientDetailDto, baseClientDetails);
        baseClientDetails.setAuthorizedGrantTypes(clientDetailDto.getGrantTypes());
        baseClientDetails.setScope(clientDetailDto.getScopes());
        baseClientDetails.setAutoApproveScopes(autoApproveAll ? Collections.singleton("true") : clientDetailDto.getAutoScopes());
        baseClientDetails.setResourceIds(clientDetailDto.getResourceIds());
        baseClientDetails.setRegisteredRedirectUri(StringKit.readerLine(clientDetailDto.getRedirectUris()));
        baseClientDetails.setAuthorities(clientDetailDto.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));

        String secret = DigestUtils.md5Hex(clientDetailDto.getClientSecret());
        if (clientDetailDto.isNew()) {
            baseClientDetails.setClientSecret(secret);
            clientDetailsService.addClientDetails(baseClientDetails);
        } else {
            clientDetailsService.updateClientDetails(baseClientDetails);
            clientDetailsService.updateClientSecret(clientDetailDto.getClientId(), secret);
        }

        // 默认流量限制1分钟3次
        ClientDetail detail = clientDetailService.findOne(Condition.NEW().eq("clientId", baseClientDetails.getClientId()));
        ClientDetailLimitDTO limitDto = clientDetailDto.getClientLimit();
        detail.setClientLimit(ClientDetailLimit.builder()
                .clientDetail(detail)
                .limits(limitDto != null && limitDto.getLimits() != null ? limitDto.getLimits() : 3L)
                .frequency(limitDto != null && limitDto.getFrequency() != null ? limitDto.getFrequency() : 1L)
                .intervalUnit(limitDto != null && limitDto.getIntervalUnit() != null ? limitDto.getIntervalUnit() : IntervalUnit.MINUTES)
                .build());

        // 设置名称
        detail.setName(clientDetailDto.getName());
        clientDetailService.save(detail);
        return ClientDetailHelper.transform2dto.apply(detail);
    }

    @PostMapping("/updateSecret")
    public void updateClientSecret(String clientId, String clientSecret) {
        clientDetailsService.updateClientSecret(clientId, DigestUtils.md5Hex(clientSecret));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        ClientDetail clientDetail = clientDetailService.findById(id);
        // 根据客户端ID删除数据并移除缓存
        clientDetailsService.removeClientDetails(clientDetail.getClientId());
    }

    @PostMapping("/api/{id}")
    public void bindApi(@PathVariable String id, Stage stage, @RequestBody List<String> apiIds) {
        ClientDetail clientDetail = clientDetailService.findById(id);
        clientDetail.getClientDetailApis().removeIf(clientDetailApi -> stage.equals(clientDetailApi.getStage()));
        apiIds.forEach(apiId ->
                clientDetail.getClientDetailApis().add(ClientDetailApi.builder()
                        .clientDetail(clientDetail)
                        .apiId(apiId)
                        .stage(stage)
                        .build()));
        clientDetailService.save(clientDetail);
    }

    @PostMapping("/user/{id}")
    public void bindUser(@PathVariable String id, String username) {
        clientDetailService.bindUser(id, username);
    }
}
