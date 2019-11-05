package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberChargeRecord;

public interface MemberChargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberChargeRecord record);

    int insertSelective(MemberChargeRecord record);

    MemberChargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberChargeRecord record);

    int updateByPrimaryKey(MemberChargeRecord record);


}
