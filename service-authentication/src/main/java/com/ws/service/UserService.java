package com.ws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.pojo.User;
import com.ws.vo.HoneyUserVO;

import javax.servlet.http.HttpServletResponse;

public interface UserService extends IService<User> {

    void login(User user, HttpServletResponse response);

    Boolean register(User user);

}
