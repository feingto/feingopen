package com.feingto.cloud.remote;

import com.feingto.cloud.config.feign.AuthClientConfiguration;
import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.domain.type.Stage;
import com.feingto.cloud.dto.oauth.ClientDetailApiDTO;
import com.feingto.cloud.dto.oauth.ClientDetailDTO;
import com.feingto.cloud.orm.jpa.page.Page;
import com.feingto.cloud.remote.fallback.SignClientFallback;
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
@FeignClient(name = "feingto-uaa", configuration = AuthClientConfiguration.class, fallback = SignClientFallback.class)
public interface SignClient {
    String API = "/uaa" + Constants.BASE_API + "/client";

    @RequestLine(value = "GET " + API + "/findByClientId/{clientId}")
    ClientDetailDTO findByClientId(@Param("clientId") String clientId);

    @RequestLine(value = "GET " + API + "/findApiById/{apiId}?username={username}")
    List<ClientDetailApiDTO> findApiById(@Param("apiId") String apiId, @Param("username") String username);

    @RequestLine(value = "GET " + API + "/findApiByUser/{username}")
    List<ClientDetailApiDTO> findApiByUser(@Param("username") String username);

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
    @RequestLine(value = "POST " + API + "/save")
    ClientDetailDTO save(ClientDetailDTO clientDetailDto);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/saveAll?autoApproveAll={autoApproveAll}")
    ClientDetailDTO saveAll(ClientDetailDTO clientDetailDto, @Param("autoApproveAll") boolean autoApproveAll);

    @RequestLine(value = "POST " + API + "/updateSecret?clientId={clientId}&clientSecret={clientSecret}")
    String updateClientSecret(@Param("clientId") String clientId, @Param("clientSecret") String clientSecret);

    @RequestLine(value = "DELETE " + API + "/delete/{id}")
    String delete(@Param("id") String id);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/api/{id}?stage={stage}")
    String bindApi(@Param("id") String id, @Param("stage") Stage stage, List<String> apiIds);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "DELETE " + API + "/api/{id}")
    String unBindApi(@Param("id") String id, List<ClientDetailApiDTO> clientDetailApiS);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/user/{id}")
    String bindUser(@Param("id") String id, List<String> users);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "DELETE " + API + "/user/{id}")
    String unBindUser(@Param("id") String id, List<String> users);
}
