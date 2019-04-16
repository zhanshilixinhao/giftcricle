package com.chouchong.dao.home.message;

import com.chouchong.entity.home.message.AppMessageUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppMessageUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppMessageUser record);

    int insertSelective(AppMessageUser record);

    AppMessageUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppMessageUser record);

    int updateByPrimaryKey(AppMessageUser record);

    /**
     * 批量插入用户系统通知
     *
     * @param: [appMessageUsers 用户系统通知集合]
     * @return: int
     * @author: yy
     * @Date: 2018/7/24
     */
    int insertList(@Param("appMessageUsers") List<AppMessageUser> appMessageUsers);

    /**
     * 删除用户系统通知
     *
     * @param: [id 系统通知id]
     * @return: int
     * @author: yy
     * @Date: 2018/7/24
     */
    int deleteByMessageId(@Param("id") Integer id);

    int insertBatch(List<AppMessageUser> users);

}
