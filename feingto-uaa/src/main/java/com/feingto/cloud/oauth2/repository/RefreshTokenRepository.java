package com.feingto.cloud.oauth2.repository;

import com.feingto.cloud.oauth2.domain.RefreshToken;
import com.feingto.cloud.orm.jpa.repository.MyRepository;

/**
 * @author longfei
 */
public interface RefreshTokenRepository extends MyRepository<RefreshToken, String> {
}
