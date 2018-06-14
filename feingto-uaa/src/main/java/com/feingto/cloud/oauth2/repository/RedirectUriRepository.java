package com.feingto.cloud.oauth2.repository;

import com.feingto.cloud.oauth2.domain.RedirectUri;
import com.feingto.cloud.orm.jpa.repository.MyRepository;

/**
 * @author longfei
 */
public interface RedirectUriRepository extends MyRepository<RedirectUri, String> {
}
