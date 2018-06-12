package com.feing.cloud.remote.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.feing.cloud.config.feign.AuthClientCodecConfiguration;
import com.feing.cloud.core.web.Constants;
import com.feing.cloud.domain.account.Role;
import com.feing.cloud.orm.jpa.page.ConditionPage;
import com.feing.cloud.orm.jpa.page.Page;
import com.feing.cloud.remote.account.fallback.RoleClientFallback;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * 角色管理客户端
 *
 * @author longfei
 */
@FeignClient(name = "feing-account", configuration = AuthClientCodecConfiguration.class, fallback = RoleClientFallback.class)
public interface RoleClient {
    String API = Constants.BASE_API + "/account/role";

    @RequestLine(value = "GET " + API + "/{id}")
    Role get(@Param("id") String id);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/data")
    Page data(ConditionPage page);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "GET " + API)
    Page list(ConditionPage page);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API)
    JsonNode save(Role role);

    @RequestLine(value = "DELETE " + API + "/{id}")
    JsonNode delete(@Param("id") String id);

    @RequestLine(value = "POST " + API + "/{id}/enable")
    JsonNode enable(@Param("id") String id);

    @RequestLine(value = "POST " + API + "/{id}/disable")
    JsonNode disable(@Param("id") String id);

    @RequestLine(value = "GET " + API + "/{roleId}/res")
    Map<String, Object> resList(@Param("roleId") String roleId);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/{roleId}/res")
    void resSave(@Param("roleId") String roleId, Map<String, Object> map);
}
