package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberEvent;

public interface MemberEventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberEvent record);

    int insertSelective(MemberEvent record);

    MemberEvent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberEvent record);

    int updateByPrimaryKey(MemberEvent record);
}
