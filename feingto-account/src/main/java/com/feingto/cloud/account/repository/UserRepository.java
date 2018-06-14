package com.feingto.cloud.account.repository;

import com.feingto.cloud.domain.account.User;
import com.feingto.cloud.orm.jpa.repository.MyRepository;

/**
 * @author longfei
 */
public interface UserRepository extends MyRepository<User, String> {
}
