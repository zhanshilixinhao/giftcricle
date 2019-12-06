package com.chouchong.dao.v3;


import com.chouchong.entity.v3.StoreMemberEvent;

public interface StoreMemberEventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreMemberEvent record);

    int insertSelective(StoreMemberEvent record);

    StoreMemberEvent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreMemberEvent record);

    int updateByPrimaryKey(StoreMemberEvent record);
}
