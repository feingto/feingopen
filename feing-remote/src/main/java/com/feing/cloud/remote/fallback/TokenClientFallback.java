package com.feing.cloud.remote.fallback;

import com.feing.cloud.orm.jpa.page.Page;
import com.feing.cloud.remote.TokenClient;
import org.springframework.stereotype.Component;

/**
 * @author longfei
 */
@Component
public class TokenClientFallback implements TokenClient {
    @Override
    public Page data(Page page) {
        return new Page();
    }

    @Override
    public Boolean revoke(String user, String token) {
        return false;
    }
}
