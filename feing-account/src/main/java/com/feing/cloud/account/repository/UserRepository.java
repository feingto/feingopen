package com.feing.cloud.account.repository;

import com.feing.cloud.domain.account.User;
import com.feing.cloud.orm.jpa.repository.MyRepository;

/**
 * @author longfei
 */
public interface UserRepository extends MyRepository<User, String> {
}
