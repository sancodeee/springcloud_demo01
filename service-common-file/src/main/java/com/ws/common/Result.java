package com.ws.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 统一结果响应体
 *
 * @author wangsen_a
 * @date 2023/12/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 2614977424476745219L;
    /**
     * 是否成功
     */
    private Boolean isSucceed = true;
    /**
     * 代码
     */
    private Integer code = 0;
    /**
     * 消息
     */
    private String message = "成功";
    /**
     * 数据
     */
    private T data;
    /**
     * 时间戳
     */
    private String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


    public Result<T> success(String message) {
        this.message = message;
        this.code = HttpStatusCode.SUCCESS_200.getCode();
        this.isSucceed = true;
        return this;
    }

    public static <T> Result<T> SUCCESS() {
        Result<T> r = new Result<>();
        r.setCode(HttpStatusCode.SUCCESS_200.getCode());
        return r;
    }

    public static <T> Result<T> SUCCESS(T data) {
        Result<T> r = new Result<>();
        r.setCode(HttpStatusCode.SUCCESS_200.getCode());
        r.setData(data);
        return r;
    }

    public static <T> Result<T> SUCCESS(String msg) {
        return SUCCESS(msg, null);
    }

    public static <T> Result<T> SUCCESS(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setCode(HttpStatusCode.SUCCESS_200.getCode());
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> FAIL() {
        Result<T> r = new Result<>();
        r.setIsSucceed(false);
        r.setCode(HttpStatusCode.FAIL_500.getCode());
        r.setMessage("服务器内部错误");
        return r;
    }

    public static <T> Result<T> FAIL(String msg) {
        return FAIL(HttpStatusCode.FAIL_500.getCode(), msg);
    }

    public static <T> Result<T> FAIL(int code, String msg) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setIsSucceed(false);
        return r;
    }

}
