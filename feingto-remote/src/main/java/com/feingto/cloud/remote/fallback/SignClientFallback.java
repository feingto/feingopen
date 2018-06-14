package com.feingto.cloud.remote.fallback;

import com.feingto.cloud.domain.type.Stage;
import com.feingto.cloud.dto.oauth.ClientDetailApiDTO;
import com.feingto.cloud.dto.oauth.ClientDetailDTO;
import com.feingto.cloud.dto.oauth.ClientDetailLimitDTO;
import com.feingto.cloud.orm.jpa.page.Page;
import com.feingto.cloud.remote.SignClient;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author longfei
 */
@Component
public class SignClientFallback implements SignClient {
    private static final String[] DEFAULT_GRANT_TYPES = {"authorization_code", "refresh_token", "password", "client_credentials"};
    private static final String[] DEFAULT_SCOPES = {"read", "write", "trust"};
    private static final String[] DEFAULT_AUTHORITIES = {"CLIENT", "API"};

    @Override
    public ClientDetailDTO findByClientId(String clientId) {
        return null;
    }

    @Override
    public ClientDetailDTO findByUsername(String username) {
        return null;
    }

    @Override
    public List<ClientDetailDTO> findByCreatedBy(String createdBy) {
        return Lists.newArrayList();
    }

    @Override
    public List<ClientDetailApiDTO> findByApi(String apiId, String createdBy) {
        return Lists.newArrayList();
    }

    @Override
    public ClientDetailLimitDTO findClientDetailLimit(String clientId) {
        return null;
    }

    @Override
    public List<String> grantTypes() {
        return Lists.newArrayList(DEFAULT_GRANT_TYPES);
    }

    @Override
    public List<String> scopes() {
        return Lists.newArrayList(DEFAULT_SCOPES);
    }

    @Override
    public List<String> authorities() {
        return Lists.newArrayList(DEFAULT_AUTHORITIES);
    }

    @Override
    public ClientDetailDTO findOne(String id) {
        return new ClientDetailDTO();
    }

    @Override
    public Page data(Page page, String username) {
        return new Page();
    }

    @Override
    public ClientDetailDTO saveOrUpdate(ClientDetailDTO clientDetailDto, boolean autoApproveAll) {
        return null;
    }

    @Override
    public String updateClientSecret(String clientId, String secret) {
        return "修改密钥失败";
    }

    @Override
    public String delete(String id) {
        return "删除密钥失败";
    }

    @Override
    public String bindApi(String id, Stage stage, List<String> apiIds) {
        return "密钥绑定API失败";
    }

    @Override
    public String unBindApi(String id, List<ClientDetailApiDTO> clientDetailApiS) {
        return "密钥解绑API失败";
    }

    @Override
    public String bindUser(String id, String username) {
        return "密钥绑定用户失败";
    }
}
