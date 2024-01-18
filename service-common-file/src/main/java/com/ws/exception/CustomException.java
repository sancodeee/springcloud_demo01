package com.ws.exception;

import com.ws.common.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author wangsen_a
 * @date 2024/01/18
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

    /**
     * 重载构造方法
     *
     * @param resultCodeEnum 结果码enum
     */
    public CustomException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.msg;
    }

}
