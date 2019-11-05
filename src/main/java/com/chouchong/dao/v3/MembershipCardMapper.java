package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MembershipCard;

public interface MembershipCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MembershipCard record);

    int insertSelective(MembershipCard record);

    MembershipCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MembershipCard record);

    int updateByPrimaryKeyWithBLOBs(MembershipCard record);

    int updateByPrimaryKey(MembershipCard record);
}
