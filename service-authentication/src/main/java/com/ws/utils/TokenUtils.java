package com.ws.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ws.dao.UserMapper;
import com.ws.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenUtils {

    private static UserMapper staticUserMapper;

    private final UserMapper userMapper;

    @Autowired
    public TokenUtils(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void setUserService() {
        staticUserMapper = userMapper;
    }

    // 设定最大大小
    private static final int MAX_MAP_SIZE = 20;
    private static final int MAX_LIST_SIZE = 10;
    /**
     * 用户token
     */
    private static final Map<String, String> userTokenMap = new ConcurrentHashMap<>();
    /**
     * 无效token
     */
    private static final Map<String, List<String>> invalidTokenMap = new ConcurrentHashMap<>();


    /**
     * 创建token令牌
     *
     * @param userId   用户id
     * @param password 密码
     * @return {@link String}
     */
    public static String createToken(String userId, String password) {
        // 将用户userId保存到token中作为载荷
        String token = JWT.create().withAudience(userId).withSubject(userId)
                // 设置2小时后过期
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                // 根据密码经过HMAC256算法加密后作为密钥 进行签名
                .sign(Algorithm.HMAC256(password));
        // 若存在旧token，则返回
        String oldToken = userTokenMap.put(userId, token);
        if (StringUtils.isNotBlank(oldToken)) {
            List<String> oldTokenList;
            List<String> mapList = invalidTokenMap.get(userId);
            if (mapList == null) {
                mapList = new ArrayList<>();
            }
            oldTokenList = mapList;
            // 清理list，防止oom
            listCleanup(oldTokenList);
            oldTokenList.add(oldToken);
            // 清理map，防止oom
            mapCleanup(invalidTokenMap);
            // 将老token存入map
            invalidTokenMap.put(userId, oldTokenList);
        }
        return token;
    }

    /**
     * 是否失效
     *
     * @param token 令牌
     * @return boolean
     */
    public static boolean isInvalided(String userId, String token) {
        List<String> oldTokenList = invalidTokenMap.get(userId);
        if (oldTokenList.isEmpty()) {
            return false;
        }
        return oldTokenList.stream().anyMatch(oldToken -> oldToken.equals(token));
    }

    /**
     * map清理
     *
     * @param map CurrentHashMap
     */
    private static <K, V> void mapCleanup(Map<K, List<V>> map) {
        if (map.size() > MAX_MAP_SIZE) {
            int toRemove = map.size() - MAX_MAP_SIZE + 1;
            map.entrySet().stream().limit(toRemove).forEach(entry -> {
                // 移除最开始添加的entry
                map.remove(entry.getKey());
            });
        }
    }

    /**
     * 列表中清除
     * list清理
     *
     * @param list 列表
     */
    private static <T> void listCleanup(List<T> list) {
        if (list.size() > MAX_LIST_SIZE) {
            // 清理最开始添加的值
            list.subList(0, list.size() - MAX_LIST_SIZE).clear();
        }
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
