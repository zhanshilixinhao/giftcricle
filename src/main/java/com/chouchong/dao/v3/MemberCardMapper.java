package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberCard;

public interface MemberCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberCard record);

    int insertSelective(MemberCard record);

    MemberCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberCard record);

    int updateByPrimaryKey(MemberCard record);


}
