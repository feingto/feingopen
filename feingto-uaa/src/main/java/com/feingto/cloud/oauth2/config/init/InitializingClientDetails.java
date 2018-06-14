package com.feingto.cloud.oauth2.config.init;

import com.feingto.cloud.domain.type.IntervalUnit;
import com.feingto.cloud.oauth2.domain.*;
import com.feingto.cloud.oauth2.security.GwClientDetailsService;
import com.feingto.cloud.oauth2.service.IClientDetail;
import com.feingto.cloud.oauth2.service.impl.AuthorityService;
import com.feingto.cloud.oauth2.service.impl.GrantTypeService;
import com.feingto.cloud.oauth2.service.impl.ScopeService;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 初始化客户端数据
 *
 * @author longfei
 */
@Configuration
@Order(-30)
@Profile("init-db-data-client")
public class InitializingClientDetails implements InitializingBean {
    private static final String[] DEFAULT_GRANT_TYPES = {"authorization_code", "refresh_token", "password", "client_credentials"};
    private static final String API_GRANT_TYPES = StringUtils.join(DEFAULT_GRANT_TYPES, ",");
    private static final String[] DEFAULT_SCOPES = {"read", "write", "trust"};
    private static final String API_SCOPES = StringUtils.join(DEFAULT_SCOPES, ",");
    private static final String[] DEFAULT_AUTHORITIES = {"CLIENT", "API"};
    private static final String API_AUTHORITIES = StringUtils.join(DEFAULT_AUTHORITIES, ",");

    @Resource(name = "clientDetailService")
    private IClientDetail gwClientDetailsService;

    @Resource
    private GrantTypeService grantTypeService;

    @Resource
    private ScopeService scopeService;

    @Resource
    private AuthorityService authorityService;

    @Autowired
    private GwClientDetailsService oAuth2DatabaseClientDetailsService;

    @Override
    public void afterPropertiesSet() {
        if (grantTypeService.count() == 0) {
            List<GrantType> grantTypeList = Arrays.stream(DEFAULT_GRANT_TYPES)
                    .map(grantType -> GrantType.builder().value(grantType).build())
                    .collect(Collectors.toList());
            grantTypeList.forEach(grantType -> grantTypeService.save(grantType));
        }

        Arrays.stream(DEFAULT_SCOPES).forEach(scope ->
                Optional.ofNullable(scopeService.findOne(Condition.NEW().eq("value", scope)))
                        .orElseGet(() -> scopeService.save(Scope.builder().value(scope).build())));

        Arrays.stream(DEFAULT_AUTHORITIES).forEach(authorityName ->
                Optional.ofNullable(authorityService.findOne(Condition.NEW().eq("value", authorityName)))
                        .orElseGet(() -> authorityService.save(Authority.builder().value(authorityName).enabled(true).build())));

        if (gwClientDetailsService.count(Condition.NEW().eq("clientId", "gateway")) == 0) {
            BaseClientDetails clientDetails = new BaseClientDetails("gateway", null, API_SCOPES, API_GRANT_TYPES, API_AUTHORITIES);
            clientDetails.setClientSecret(DigestUtils.md5Hex("gateway123456"));
            clientDetails.setRegisteredRedirectUri(Collections.emptySet());
            oAuth2DatabaseClientDetailsService.addClientDetails(clientDetails);

            // 默认流量限制1分钟3次
            ClientDetail gwClientDetails = gwClientDetailsService.findOne(Condition.NEW()
                    .eq("clientId", clientDetails.getClientId()));
            gwClientDetails.setClientLimit(ClientDetailLimit.builder()
                    .clientDetail(gwClientDetails)
                    .limits(3L)
                    .frequency(1L)
                    .intervalUnit(IntervalUnit.MINUTES)
                    .build());
            gwClientDetailsService.save(gwClientDetails);
        }
    }
}
