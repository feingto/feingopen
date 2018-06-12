package com.feing.cloud.oauth2.repository;

import com.feing.cloud.oauth2.domain.RefreshToken;
import com.feing.cloud.orm.jpa.repository.MyRepository;

/**
 * @author longfei
 */
public interface RefreshTokenRepository extends MyRepository<RefreshToken, String> {
}
