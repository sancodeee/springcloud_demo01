package com.ws.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;

/**
 * 全局异常处理类
 *
 * @author wangsen_a
 * @date 2023/12/13
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 异常处理程序
     *
     * @param e e
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> exceptionHandler(Exception e) {
        log.error("出现异常：{}", e.toString());
        e.printStackTrace();
        return Result.FAIL("服务器出现未知错误");
    }

    /**
     * 意外类型异常处理程序
     *
     * @param e e
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(value = UnexpectedTypeException.class)
    public Result<?> unexpectedTypeExceptionHandler(Exception e) {
        log.error("出现异常：{}", e.toString());
        e.printStackTrace();
        return Result.FAIL("服务器错误：数据校验异常");
    }

    /**
     * 空指针异常处理程序
     *
     * @param e e
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Result<?> NullPointerExceptionHandler(Exception e) {
        log.error("出现异常：{}", e.toString());
        e.printStackTrace();
        return Result.FAIL("服务器错误：空指针异常");
    }

}
