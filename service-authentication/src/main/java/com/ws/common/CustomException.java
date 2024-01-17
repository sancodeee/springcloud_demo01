package com.ws.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author wangsen
 * @date 2024/01/14
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    /**
     * 代码
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String msg;

}
