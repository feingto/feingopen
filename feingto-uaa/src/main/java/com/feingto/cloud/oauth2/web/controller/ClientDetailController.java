package com.feingto.cloud.oauth2.web.controller;

import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.domain.type.Stage;
import com.feingto.cloud.dto.oauth.ClientDetailApiDTO;
import com.feingto.cloud.dto.oauth.ClientDetailDTO;
import com.feingto.cloud.kit.StringKit;
import com.feingto.cloud.oauth2.domain.*;
import com.feingto.cloud.oauth2.security.ClientDetailHelper;
import com.feingto.cloud.oauth2.security.GwClientDetailsService;
import com.feingto.cloud.oauth2.service.IClientDetail;
import com.feingto.cloud.oauth2.service.impl.AuthorityService;
import com.feingto.cloud.oauth2.service.impl.ClientDetailApiService;
import com.feingto.cloud.oauth2.service.impl.GrantTypeService;
import com.feingto.cloud.oauth2.service.impl.ScopeService;
import com.feingto.cloud.orm.jpa.page.Page;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
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

    @GetMapping("/findByClientId/{clientId}")
    public ClientDetailDTO findByClientId(@PathVariable String clientId) {
        return Optional.ofNullable(clientDetailService.findByClientId(clientId))
                .map(ClientDetailHelper.transform2dto)
                .orElseThrow(() -> new NoSuchClientException("Client ID not found."));
    }

    @GetMapping("/findApiById/{apiId}")
    public List<ClientDetailApiDTO> findApiById(@PathVariable String apiId, String username) {
        Condition condition = Condition.NEW().eq("apiId", apiId);
        if (StringUtils.hasLength(username)) {
            condition = condition.eq("clientDetail.clientDetailUsers.username", username);
        }
        return clientDetailApiService.findAll(condition).stream()
                .map(clientDetailApi -> ClientDetailApiDTO.builder()
                        .clientDetail(ClientDetailHelper.transform2dto.apply(clientDetailApi.getClientDetail()))
                        .apiId(clientDetailApi.getApiId())
                        .stage(clientDetailApi.getStage())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 获取用户全部客户端绑定的api
     */
    @GetMapping("/findApiByUser/{username}")
    public List<ClientDetailApiDTO> findApiByUser(@PathVariable String username) {
        List<ClientDetail> clientDetails = clientDetailService.findAll(Condition.NEW()
                .eq("clientDetailUsers.username", username));
        return CollectionUtils.isEmpty(clientDetails) ? Lists.newArrayList() : clientDetails.stream()
                .map(ClientDetail::getClientDetailApis)
                .flatMap(Collection::stream)
                .map(clientDetailApi -> ClientDetailApiDTO.builder()
                        .clientDetail(ClientDetailHelper.transform2dto.apply(clientDetailApi.getClientDetail()))
                        .apiId(clientDetailApi.getApiId())
                        .stage(clientDetailApi.getStage())
                        .build())
                .collect(Collectors.toList());
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
            condition.eq("clientDetailUsers.username", username);
        }
        page = clientDetailService.findByPage(condition, page);
        List<ClientDetail> clientDetailList = page.getContent();
        page.setContent(clientDetailList.stream()
                .map(ClientDetailHelper.transform2dto)
                .collect(Collectors.toList()));
        return page;
    }

    @PostMapping("/save")
    public ClientDetailDTO save(@RequestBody ClientDetailDTO clientDetailDto) {
        clientDetailDto.setAuthorities(Sets.newHashSet(authorities()));
        clientDetailDto.setGrantTypes(Sets.newHashSet(grantTypes()));
        clientDetailDto.setScopes(Sets.newHashSet(scopes()));
        clientDetailDto.setAutoScopes(clientDetailDto.getScopes());
        clientDetailDto.setAutoApproveAll(true);
        return this.saveAll(clientDetailDto, true);
    }

    @PostMapping("/saveAll")
    public ClientDetailDTO saveAll(@RequestBody ClientDetailDTO clientDetailDto,
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

        ClientDetail detail = clientDetailService.findOne(Condition.NEW().eq("clientId", baseClientDetails.getClientId()));
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

    @DeleteMapping("/api/{id}")
    public void unBindApi(@PathVariable String id, @RequestBody List<ClientDetailApiDTO> clientDetailApiS) {
        ClientDetail clientDetail = clientDetailService.findById(id);
        clientDetailApiS.forEach(api ->
                clientDetail.getClientDetailApis().removeIf(clientDetailApi ->
                        api.getStage().equals(clientDetailApi.getStage())
                                && api.getApiId().equals(clientDetailApi.getApiId())));
        clientDetailService.save(clientDetail);
    }

    @PostMapping("/user/{id}")
    public void bindUser(@PathVariable String id, @RequestBody List<String> users) {
        ClientDetail clientDetail = clientDetailService.findById(id);
        clientDetail.getClientDetailUsers().clear();
        users.forEach(user ->
                clientDetail.getClientDetailUsers().add(ClientDetailUser.builder()
                        .clientDetail(clientDetail)
                        .username(user)
                        .build()));
        clientDetailService.save(clientDetail);
    }

    @DeleteMapping("/user/{id}")
    public void unBindUser(@PathVariable String id, @RequestBody List<String> users) {
        ClientDetail clientDetail = clientDetailService.findById(id);
        users.forEach(user ->
                clientDetail.getClientDetailUsers().removeIf(clientDetailUser ->
                        user.contains(clientDetailUser.getUsername())));
        clientDetailService.save(clientDetail);
    }
}
