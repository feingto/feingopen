package com.feingto.cloud.remote.fallback;

import com.feingto.cloud.domain.type.Stage;
import com.feingto.cloud.dto.oauth.ClientDetailApiDTO;
import com.feingto.cloud.dto.oauth.ClientDetailDTO;
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
        return new ClientDetailDTO();
    }

    @Override
    public List<ClientDetailApiDTO> findApiById(String apiId, String username) {
        return Lists.newArrayList();
    }

    @Override
    public List<ClientDetailApiDTO> findApiByUser(String username) {
        return Lists.newArrayList();
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
    public ClientDetailDTO save(ClientDetailDTO clientDetailDto) {
        return null;
    }

    @Override
    public ClientDetailDTO saveAll(ClientDetailDTO clientDetailDto, boolean autoApproveAll) {
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
        return "应用授权API失败";
    }

    @Override
    public String unBindApi(String id, List<ClientDetailApiDTO> clientDetailApiS) {
        return "应用解绑API失败";
    }

    @Override
    public String bindUser(String id, List<String> users) {
        return "应用授权用户失败";
    }

    @Override
    public String unBindUser(String id, List<String> users) {
        return "应用解绑用户失败";
    }
}
