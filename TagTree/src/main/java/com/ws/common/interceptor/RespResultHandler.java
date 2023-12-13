package com.ws.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.common.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 响应体结果拦截器
 *
 * @author wangsen_a
 * @date 2023/12/13
 */
@RestControllerAdvice
public class RespResultHandler implements ResponseBodyAdvice<Object> {

    // 属性名称，用于记录是否标注了RespResult注解
    public static final String RESPONSE_RESULTVO_ATTR = "RESPONSE_RESULTVO_ATTR";

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 判断是否需要执行beforeBodyWrite方法，true为执行；false为不执行
     *
     * @param returnType  返回类型
     * @param convertType 转换类型
     * @return boolean
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> convertType) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 判断请求中是否有标记字段RESPONSE_RESULTVO_ATTR, 如果有则返回true，否则返回false
        RespResult respResult = (RespResult) request.getAttribute(RESPONSE_RESULTVO_ATTR);
        return respResult != null;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof RespResult) {
            return (RespResult) body;
        } else if (body instanceof Result) {
            return body;
            // 如果返回的是Sting类型
        } else if (body instanceof String) {
            return objectMapper.writeValueAsString(Result.SUCCESS(body));
        }
        return Result.SUCCESS(body);
    }
}
