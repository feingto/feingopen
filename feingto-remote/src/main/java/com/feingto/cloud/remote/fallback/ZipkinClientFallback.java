package com.feingto.cloud.remote.fallback;

import com.feingto.cloud.core.web.WebResult;
import com.feingto.cloud.remote.ZipkinClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author longfei
 */
@Component
public class ZipkinClientFallback implements ZipkinClient {
    @Override
    public ResponseEntity<List<String>> getServiceNames() {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> getSpanNames(String serviceName) {
        return null;
    }

    @Override
    public String traces(String serviceName, String spanName, String annotationQuery,
                         Long minDuration, Long maxDuration, Long endTs,
                         Long lookback, Integer limit) {
        return WebResult.error("获取服务\"" + serviceName + "\"跟踪记录失败").toString();
    }

    @Override
    public String getTrace(String traceIdHex) {
        return WebResult.error("获取跟踪记录详情失败, TraceId：" + traceIdHex).toString();
    }
}
