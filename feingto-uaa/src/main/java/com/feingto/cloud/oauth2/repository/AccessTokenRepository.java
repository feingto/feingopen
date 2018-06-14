package com.feingto.cloud.oauth2.repository;

import com.feingto.cloud.oauth2.domain.AccessToken;
import com.feingto.cloud.orm.jpa.repository.MyRepository;

/**
 * @author longfei
 */
public interface AccessTokenRepository extends MyRepository<AccessToken, String> {
}
