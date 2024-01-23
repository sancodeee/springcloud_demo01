package com.ws.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ws.common.exception.CustomException;
import com.ws.dao.UserMapper;
import com.ws.pojo.User;
import com.ws.utils.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 *
 * @author wangsen
 * @date 2024/01/02
 */
public class    JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    /**
     * 预处理
     * 拦截请求
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求中的token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        // token校验失败
        if (StringUtils.isBlank(token)) {
            throw new CustomException(401, "请登录");
        }
        // 解析token获取userId
        String userId;
        // JWT.decode(token) 解码
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new CustomException(401, "请登录");
        }
        // 验证token是否是最新的token，老token被标记失效
        if (TokenUtils.isInvalided(userId, token)) {
            throw new CustomException(401, "token失效，请重新登录");
        }
        // 根据userId去数据库中查询该用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new CustomException(401, "用户不存在，请重新登录");
        }

        String password = user.getPassword();
        // String password = "123456";
        // 通过用户密码加密之后生成一个验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();
        try {
            // 通过验证器去验证用户
            // 如果这里不报错，就说明验证通过
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new CustomException(401, "用户验证失败，请重新登录");
        }
        return true;
    }
}
