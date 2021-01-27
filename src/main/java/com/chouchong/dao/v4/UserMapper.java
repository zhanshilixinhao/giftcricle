package com.chouchong.dao.v4;

import com.chouchong.entity.v4.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/9/24 15:57
 */
public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
}
