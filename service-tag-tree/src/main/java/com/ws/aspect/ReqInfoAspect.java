package com.ws.aspect;

import com.ws.dto.RequestLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求信息日志打印
 *
 * @author wangsen
 * @date 2024/01/09
 */
@Component
@Slf4j
@Aspect
public class ReqInfoAspect {

    private long startTime;

    @Pointcut("execution(* com.ws.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void before(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        ServletRequestAttributes servletAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletAttributes != null) {
            HttpServletRequest request = servletAttributes.getRequest();
            // url
            String url = request.getRequestURL().toString();
            // ip
            String ip = request.getRemoteAddr();
            // 类名和方法名
            String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            // 参数列表
            Object[] args = joinPoint.getArgs();
            RequestLogDTO requestLog = new RequestLogDTO(url, ip, classMethod, args);
            // 打印
            log.info("==========================请求开始==========================");
            log.info("请求信息：{}", requestLog);
        }
    }

    /**
     * 该方法无论切点方法是否执行成功都会执行
     */
    @After("log()")
    public void after() {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("==========================请求完毕，执行时间：{}(ms)==========================", duration);
    }

    /**
     * 后返回
     * 该方法只在切点方法执行成功后，执行
     * <p>
     * res是切点方法的返回结果
     *
     * @param res 结果
     */
    @AfterReturning(returning = "res", pointcut = "log()")
    public void afterReturn(Object res) {
        log.info("Request：{}", res);
    }

}
