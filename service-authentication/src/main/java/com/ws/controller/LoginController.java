package com.ws.controller;

import com.ws.common.Result;
import com.ws.common.exception.CustomException;
import com.ws.pojo.User;
import com.ws.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录控制器
 *
 * @author wangsen
 * @date 2024/04/18
 */
@RestController
@RequestMapping(produces = "application/json")
public class LoginController {

    /**
     * 用户服务
     */
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user, HttpServletResponse response) {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            throw new CustomException(400, "用户名或密码为空");
        }
        userService.login(user, response);
        return Result.SUCCESS("登录成功");
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        if (!userService.register(user)) {
            return Result.FAIL("注册失败");
        }
        return Result.SUCCESS("注册成功");
    }

}
