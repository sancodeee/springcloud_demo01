package com.ws.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.common.exception.CustomException;
import com.ws.dao.UserMapper;
import com.ws.pojo.User;
import com.ws.service.UserService;
import com.ws.utils.TokenUtils;
import com.ws.vo.HoneyUserVO;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 登录
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public void login(User user, HttpServletResponse response) {
        String username = user.getUsername();
        // 根据用户名去数据库里查询
        User dbUser = this.getBaseMapper().selectOne(new QueryWrapper<User>().eq("username", username));
        // 查库校验该用户是否存在
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(422, "校验失败，该用户不存在");
        }
        // 校验密码
        if (!user.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException(500, "用户名或密码错误");
        }
        // 根据用户信息生成token
        String token = TokenUtils.createToken(dbUser.getId().toString(), user.getPassword());
        // 构建带token信息的对象返回到前端
        response.setHeader(HttpHeaders.AUTHORIZATION, token);
    }

    /**
     * 注册
     *
     * @param user 用户
     * @return {@link Boolean}
     */
    @Override
    public Boolean register(User user) {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            throw new CustomException(400, "用户名或密码为空");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User dbUser = this.getBaseMapper().selectOne(queryWrapper);
        if (ObjectUtils.isNotEmpty(dbUser)) {
            throw new CustomException(409, "用户名已存在，请修改");
        }
        if (getBaseMapper().insert(user) == 0) {
            return false;
        }
        return true;
    }


}
