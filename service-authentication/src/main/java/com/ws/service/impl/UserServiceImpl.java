package com.ws.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ws.common.CustomException;
import com.ws.dao.UserMapper;
import com.ws.pojo.User;
import com.ws.service.UserService;
import com.ws.utils.TokenUtils;
import com.ws.vo.HoneyUserVO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 登录
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public HoneyUserVO login(User user) {
        String username = user.getUsername();
        User dbUser = this.getBaseMapper().selectOne(new QueryWrapper<User>().eq("username", username));
        // 查库校验该用户是否存在
        if (ObjectUtil.isNotNull(dbUser)) {
            throw new CustomException(422, "校验失败，该用户不存在");
        }
        // 根据用户信息生成token
        String token = TokenUtils.createToken(dbUser.getId().toString(), user.getPassword());
        // 构建带token信息的对象返回到前端
        HoneyUserVO userVO = new HoneyUserVO();
        userVO.setToken(token);
        userVO.setUsername(username);
        return userVO;
    }
}
