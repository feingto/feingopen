package com.feing.cloud.oauth2.repository;

import com.feing.cloud.oauth2.domain.RedirectUri;
import com.feing.cloud.orm.jpa.repository.MyRepository;

/**
 * @author longfei
 */
public interface RedirectUriRepository extends MyRepository<RedirectUri, String> {
}
