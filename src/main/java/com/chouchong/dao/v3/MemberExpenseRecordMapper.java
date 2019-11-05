package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberExpenseRecord;

public interface MemberExpenseRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberExpenseRecord record);

    int insertSelective(MemberExpenseRecord record);

    MemberExpenseRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberExpenseRecord record);

    int updateByPrimaryKey(MemberExpenseRecord record);

}
