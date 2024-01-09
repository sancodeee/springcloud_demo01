package com.ws;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.ws.common.Result;
import com.ws.pojo.User;
import com.ws.service.UserService;
import com.ws.vo.HoneyUserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginUserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param user 用户
     * @return {@link Result}<{@link HoneyUserVO}>
     */
    @PostMapping("")
    public Result<HoneyUserVO> login(User user) {
        if (ObjectUtils.isNull(user)) {
            return Result.FAIL("输入为空");
        }
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return Result.FAIL("用户名或密码错误");
        }
        HoneyUserVO honeyUserVO = userService.login(user);
        return Result.SUCCESS(honeyUserVO);
    }

}
