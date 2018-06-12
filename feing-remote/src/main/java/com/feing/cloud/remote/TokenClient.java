package com.feing.cloud.remote;

import com.feing.cloud.config.feign.AuthClientConfiguration;
import com.feing.cloud.core.web.Constants;
import com.feing.cloud.orm.jpa.page.Page;
import com.feing.cloud.remote.fallback.TokenClientFallback;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author longfei
 */
@FeignClient(name = "feing-uaa", configuration = AuthClientConfiguration.class, fallback = TokenClientFallback.class)
public interface TokenClient {
    String API = "/uaa" + Constants.BASE_API + "/tokens";

    @Headers("Content-Type: application/json")
    @RequestLine(value = "POST " + API + "/data")
    Page data(Page page);

    @RequestLine(value = "POST " + API + "/revoke?user={user}&token={token}")
    Boolean revoke(@Param("user") String user, @Param("token") String token);
}
