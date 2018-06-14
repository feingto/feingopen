package com.feingto.cloud.oauth2.repository;

import com.feingto.cloud.oauth2.domain.Scope;
import com.feingto.cloud.orm.jpa.repository.MyRepository;

/**
 * @author longfei
 */
public interface ScopeRepository extends MyRepository<Scope, String> {
}
