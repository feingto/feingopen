package com.feingto.cloud.account.service;

import com.feingto.cloud.domain.account.Resource;
import com.feingto.cloud.orm.jpa.IBase;

/**
 * @author longfei
 */
public interface IResource extends IBase<Resource, String> {
    void executeSort(String data);
}
