package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberExpenseRecord;
import com.chouchong.service.v3.vo.ExpenseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberExpenseRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberExpenseRecord record);

    int insertSelective(MemberExpenseRecord record);

    MemberExpenseRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberExpenseRecord record);

    int updateByPrimaryKey(MemberExpenseRecord record);

    List<ExpenseVo> selectByUserIdCardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);
}