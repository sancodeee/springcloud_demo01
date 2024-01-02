package com.ws.common;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final Integer code;

    private final String msg;

    public CustomException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
