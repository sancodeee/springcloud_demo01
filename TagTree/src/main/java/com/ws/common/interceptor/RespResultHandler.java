package com.ws.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.common.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestControllerAdvice
public class RespResultHandler implements ResponseBodyAdvice<Object> {

    /**
     * 属性名称，用于记录是否标注了RespResult注解，从而对返回值进行统一封装
     */
    public static final String RESPONSE_RESULT_ATTR = "RESPONSE_RESULT_ATTR";

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 判断是否需要执行 beforeBodyWrite 方法，true为执行；false为不执行
     *
     * @param returnType    返回类型
     * @param converterType 转换器类型
     * @return boolean
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 断言一下是否为空
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            // 判断请求中是否有标记字段RESPONSE_RESULT_ATTR, 如果有则返回true，否则返回false
            RespResult respResult = (RespResult) request.getAttribute(RESPONSE_RESULT_ATTR);
            return respResult != null;
        }
        return false;
    }

    /**
     * controller方法执行返回后，拦截返回值对象执行该方法
     *
     * @param methodParameter 方法参数
     * @param contentType     响应内容类型
     * @param converterType   消息转换器类型
     * @param request         请求
     * @param object          controller返回对象
     * @param response        响应
     * @return {@link Object}
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType contentType,
                                  Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // object为controller的返回值对象
        if (object instanceof RespResult) {
            return (RespResult) object;
        } else if (object instanceof Result) {
            return object;
            // 如果返回的是Sting类型，则做封装
        } else if (object instanceof String) {
            return objectMapper.writeValueAsString(Result.SUCCESS(object));
        }
        return Result.SUCCESS(object);
    }
}
