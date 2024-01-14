package com.ws.common;

import lombok.Getter;

/**
 * 自定义异常
 *
 * @author wangsen
 * @date 2024/01/14
 */
@Getter
public class CustomException extends RuntimeException {

    private final Integer code;

    private final String msg;

    public CustomException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
