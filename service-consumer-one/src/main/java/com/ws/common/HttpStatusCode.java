package com.ws.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * HTTP状态码枚举
 *
 * @author wangsen_a
 * @date 2023/12/12
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public enum HttpStatusCode {

    SUCCESS_200(200, "OK", "请求已经成功处理"),
    FAIL_501(501, "Parameter verification error", "参数校验错误"),
    FAIL_502(502, "Bad gateway", "错误网关"),
    FAIL_404(404, "Resource not found", "找不到资源"),
    FAIL_500(500, "Server internal error", "服务器内部错误");


    /**
     * 代码
     */
    private final Integer code;
    /**
     * 英文消息
     */
    private final String enMessage;
    /**
     * 中文消息
     */
    private final String cnMessage;


}
