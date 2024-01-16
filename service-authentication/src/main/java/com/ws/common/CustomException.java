package com.ws.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
