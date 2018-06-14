package com.feingto.cloud.remote.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.feingto.cloud.config.feign.AuthClientCodecConfiguration;
import com.feingto.cloud.core.web.Constants;
import com.feingto.cloud.domain.account.Resource;
import com.feingto.cloud.domain.account.ResourceButton;
import com.feingto.cloud.domain.account.ResourceColumn;
import com.feingto.cloud.orm.jpa.specification.bean.Condition;
import com.feingto.cloud.remote.account.fallback.ResourceClientFallback;
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
@FeignClient(name = "feingto-account", configuration = AuthClientCodecConfiguration.class, fallback = ResourceClientFallback.class)
public interface ResourceClient {
    String API = Constants.BASE_API + "/account/res";

    @RequestLine(value = "GET " + API + "/{id}")
    Resource get(@Param("id") String id);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "GET " + API)
    List<Resource> list(Condition condition);

    @RequestLine(value = "GET " + API + "/tree")
    List<Resource> tree();

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API)
    JsonNode save(Resource res);

    @RequestLine(value = "DELETE " + API + "/{id}")
    JsonNode delete(@Param("id") String id);

    @RequestLine(value = "POST " + API + "/{id}/{property}?value={value}")
    JsonNode updateByProperty(@Param("id") String id, @Param("property") String property, @Param("value") Object value);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/{id}/button")
    void buttonSave(@Param("id") String id, List<ResourceButton> buttons);

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/{id}/column")
    void columnSave(@Param("id") String id, List<ResourceColumn> columns);

    @RequestLine(value = "POST " + API + "/sort?data={data}")
    JsonNode sort(@Param("data") String data);
}
