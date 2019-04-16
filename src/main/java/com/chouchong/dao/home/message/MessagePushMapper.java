package com.chouchong.dao.home.message;

import com.chouchong.entity.home.message.MessagePush;

public interface MessagePushMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessagePush record);

    int insertSelective(MessagePush record);

    MessagePush selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessagePush record);

    int updateByPrimaryKey(MessagePush record);
}