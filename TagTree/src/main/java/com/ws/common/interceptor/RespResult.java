package com.ws.common.interceptor;

import java.lang.annotation.*;

/**
 * 自定义注解：用于标记是否需要统一响应体，标记该注解则封装成统一响应体
 *
 * @author wangsen_a
 * @date 2023/12/13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RespResult {

}
