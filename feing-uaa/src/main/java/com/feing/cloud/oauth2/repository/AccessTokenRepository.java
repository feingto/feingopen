package com.feing.cloud.oauth2.repository;

import com.feing.cloud.oauth2.domain.AccessToken;
import com.feing.cloud.orm.jpa.repository.MyRepository;

/**
 * @author longfei
 */
public interface AccessTokenRepository extends MyRepository<AccessToken, String> {
}
