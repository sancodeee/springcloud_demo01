package com.ws.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ws.dao.UserMapper;
import com.ws.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtils {

    @Resource
    UserMapper userMapper;

    private static UserMapper staticUserMapper;

    public void setUserMapper() {
        staticUserMapper = userMapper;
    }

    /**
     * 创建token令牌
     *
     * @param userId   用户id
     * @param password 密码
     * @return {@link String}
     */
    public static String createToken(String userId, String password) {
        // 将用户userId保存到token中作为载荷
        return JWT.create().withAudience(userId)
                // 设置2小时后过期
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                // 根据密码经过HMAC256算法加密后作为密钥 进行签名
                .sign(Algorithm.HMAC256(password));
    }

    /**
     * 获取当前用户
     *
     * @return {@link User}
     */
    public static User getCurrentUser() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            String token = request.getHeader("token");
            if (StringUtils.isNotBlank(token)) {
                // 解析token 获取userId
                String userId = JWT.decode(token).getAudience().get(0);
                // 根据userId查库
                return staticUserMapper.selectById(Long.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
