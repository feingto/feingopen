package com.feing.cloud.account.service;

import com.feing.cloud.domain.account.Resource;
import com.feing.cloud.orm.jpa.IBase;

/**
 * @author longfei
 */
public interface IResource extends IBase<Resource, String> {
    void executeSort(String data);
}
