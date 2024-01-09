package com.ws.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户映射器
 *
 * @author wangsen
 * @date 2024/01/09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
