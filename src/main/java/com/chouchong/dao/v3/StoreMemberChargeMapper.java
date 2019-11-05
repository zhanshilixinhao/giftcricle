package com.chouchong.dao.v3;


import com.chouchong.entity.v3.StoreMemberCharge;

public interface StoreMemberChargeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreMemberCharge record);

    int insertSelective(StoreMemberCharge record);

    StoreMemberCharge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreMemberCharge record);

    int updateByPrimaryKey(StoreMemberCharge record);
}
