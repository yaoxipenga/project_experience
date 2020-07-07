package com.medcaptain.cloud.usermanage.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

import static com.medcaptain.helper.HttpServletHelper.INNER_INVOKE_FLAG;
import static com.medcaptain.helper.HttpServletHelper.REQUEST_HEADER_INNER_INVOKE;

/**
 * 服务间调用时添加标记
 *
 * @author bingwen.ai
 */
@Component
public class RequestHeaderInterceptor implements RequestInterceptor {
    /**
     * feign调用其他服务添加标记
     *
     * @param requestTemplate 访问请求模板
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (requestTemplate != null) {
            Map<String, Collection<String>> headers = requestTemplate.headers();
            if (headers != null) {
                if (!headers.keySet().contains(REQUEST_HEADER_INNER_INVOKE)) {
                    requestTemplate.header(REQUEST_HEADER_INNER_INVOKE, INNER_INVOKE_FLAG);
                }
            }
        }
    }
}
