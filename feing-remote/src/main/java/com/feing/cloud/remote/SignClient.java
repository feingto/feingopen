package com.feing.cloud.remote;

import com.feing.cloud.config.feign.AuthClientConfiguration;
import com.feing.cloud.core.web.Constants;
import com.feing.cloud.domain.type.Stage;
import com.feing.cloud.dto.oauth.ClientDetailApiDTO;
import com.feing.cloud.dto.oauth.ClientDetailDTO;
import com.feing.cloud.dto.oauth.ClientDetailLimitDTO;
import com.feing.cloud.orm.jpa.page.Page;
import com.feing.cloud.remote.fallback.SignClientFallback;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * 签名客户端
 *
 * @author longfei
 */
@FeignClient(name = "feing-uaa", configuration = AuthClientConfiguration.class, fallback = SignClientFallback.class)
public interface SignClient {
    String API = "/uaa" + Constants.BASE_API + "/client";

    @RequestLine(value = "GET " + API + "/findByClientId?clientId={clientId}")
    ClientDetailDTO findByClientId(@Param("clientId") String clientId);

    @RequestLine(value = "GET " + API + "/findByUsername?username={username}")
    ClientDetailDTO findByUsername(@Param("username") String username);

    @RequestLine(value = "GET " + API + "/findByCreatedBy?createdBy={createdBy}")
    List<ClientDetailDTO> findByCreatedBy(@Param("createdBy") String createdBy);

    @RequestLine(value = "GET " + API + "/findByApi?apiId={apiId}&createdBy={createdBy}")
    List<ClientDetailApiDTO> findByApi(@Param("apiId") String apiId, @Param("createdBy") String createdBy);

    @RequestLine(value = "GET " + API + "/limit?clientId={clientId}")
    ClientDetailLimitDTO findClientDetailLimit(@Param("clientId") String clientId);

    @RequestLine(value = "GET " + API + "/grantTypes")
    List<String> grantTypes();

    @RequestLine(value = "GET " + API + "/scopes")
    List<String> scopes();

    @RequestLine(value = "GET " + API + "/authorities")
    List<String> authorities();

    @RequestLine(value = "GET " + API + "/{id}")
    ClientDetailDTO findOne(@Param("id") String id);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/data?username={username}")
    Page data(Page page, @Param("username") String username);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/save?autoApproveAll={autoApproveAll}")
    ClientDetailDTO saveOrUpdate(ClientDetailDTO clientDetailDto, @Param("autoApproveAll") boolean autoApproveAll);

    @RequestLine(value = "POST " + API + "/updateSecret?clientId={clientId}&clientSecret={clientSecret}")
    String updateClientSecret(@Param("clientId") String clientId, @Param("clientSecret") String clientSecret);

    @RequestLine(value = "DELETE " + API + "/delete/{id}")
    String delete(@Param("id") String id);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/api/{id}?stage={stage}")
    String bindApi(@Param("id") String id, @Param("stage") Stage stage, List<String> apiIds);

    @RequestLine(value = "POST " + API + "/user/{id}?username={username}")
    String bindUser(@Param("id") String id, @Param("username") String username);
}
