package com.feingto.cloud.remote.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.feingto.cloud.config.feign.AuthClientCodecConfiguration;
import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.domain.account.Resource;
import com.feingto.cloud.domain.account.User;
import com.feingto.cloud.dto.oauth.ClientDetailApiDTO;
import com.feingto.cloud.orm.jpa.page.ConditionPage;
import com.feingto.cloud.orm.jpa.page.Page;
import com.feingto.cloud.remote.account.fallback.UserClientFallback;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * 用户管理客户端
 *
 * @author longfei
 */
@FeignClient(name = "feingto-account", configuration = AuthClientCodecConfiguration.class, fallback = UserClientFallback.class)
public interface UserClient {
    String API = Constants.BASE_API + "/account/users";

    @RequestLine("GET " + API + "/{username}/get")
    User user(@Param("username") String username);

    @RequestLine(value = "GET " + API + "/{id}")
    User get(@Param("id") String id);

    /**
     * 获取用户资源
     *
     * @param id 用户ID
     */
    @RequestLine(value = "GET " + API + "/{id}/res")
    List<Resource> loadResources(@Param("id") String id);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "GET " + API)
    Page list(ConditionPage page);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API)
    JsonNode save(User user);

    @RequestLine(value = "DELETE " + API + "/{id}")
    JsonNode delete(@Param("id") String id);

    @RequestLine(value = "POST " + API + "/{id}/{property}?value={value}")
    JsonNode updateByProperty(@Param("id") String id, @Param("property") String property, @Param("value") Object value);

    /**
     * 分页获取用户，指定用户在最前面
     *
     * @param page     Page
     * @param username 前置用户名
     * @param keyword  关键字
     */
    @Headers("Content-Type: application/json")
    @RequestLine(value = "GET " + API + "/{username}/pages?keyword={keyword}")
    Page<User> findPageByUsername(Page<User> page, @Param("username") String username, @Param("keyword") String keyword);

    /**
     * 获取APP 密钥绑定用户的 API
     *
     * @param username 前置用户名
     */
    @RequestLine(value = "GET " + API + "/{username}/apis")
    List<ClientDetailApiDTO> findOAuthApisByUsername(@Param("username") String username);
}
