package com.ws.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1308162567215268390L;
    /**
     * 代码
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String msg;

}
