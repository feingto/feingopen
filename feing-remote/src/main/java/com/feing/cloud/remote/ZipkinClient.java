package com.feing.cloud.remote;

import com.feing.cloud.config.feign.AuthClientConfiguration;
import com.feing.cloud.remote.fallback.ZipkinClientFallback;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Zipkin 客户端
 *
 * @author longfei
 */
@FeignClient(name = "zipkin", url = "${spring.zipkin.base-url:http://localhost:9411/}", configuration = AuthClientConfiguration.class, fallback = ZipkinClientFallback.class)
public interface ZipkinClient {
    String API = "/zipkin/api/v1";

    /**
     * 获取服务名
     *
     * @return 服务名集合
     */
    @RequestLine(value = "GET " + API + "/services")
    ResponseEntity<List<String>> getServiceNames();

    /**
     * 获取服务标签名
     *
     * @param serviceName 服务名
     * @return 标签名集合
     */
    @RequestLine(value = "GET " + API + "/spans?serviceName={serviceName}")
    ResponseEntity<List<String>> getSpanNames(@Param("serviceName") String serviceName);

    /**
     * 获取链路列表
     *
     * @param serviceName     服务名
     * @param spanName        标签名
     * @param annotationQuery 查询条件
     * @param minDuration     最小持续时间
     * @param maxDuration     最大持续时间
     * @param endTs           截止时间
     * @param lookback        时间范围：截止时间 - 起始时间
     * @param limit           条数
     * @return JSON
     */
    @RequestLine(value = "GET " + API + "/traces?serviceName={serviceName}&spanName={spanName}&annotationQuery={annotationQuery}" +
            "&minDuration={minDuration}&maxDuration={maxDuration}&endTs={endTs}&lookback={lookback}&limit={limit}")
    String traces(@Param("serviceName") String serviceName, @Param("spanName") String spanName,
                  @Param("annotationQuery") String annotationQuery, @Param("minDuration") Long minDuration,
                  @Param("maxDuration") Long maxDuration, @Param("endTs") Long endTs,
                  @Param("lookback") Long lookback, @Param("limit") Integer limit);

    /**
     * 获取链路详情
     *
     * @param traceIdHex 链路ID
     * @return JSON
     */
    @RequestLine(value = "GET " + API + "/trace/{traceIdHex}")
    String getTrace(@Param("traceIdHex") String traceIdHex);
}
