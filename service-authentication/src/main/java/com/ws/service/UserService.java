package com.ws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ws.pojo.User;
import com.ws.vo.HoneyUserVO;

public interface UserService extends IService<User> {

    HoneyUserVO login(User user);

}
