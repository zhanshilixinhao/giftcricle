package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberExpenseRecord;
import com.chouchong.service.v3.vo.ExpenseReVo;
import com.chouchong.service.v3.vo.ExpenseReVos;
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

    List<ExpenseReVo> selectBySearch(@Param("phone") String phone, @Param("storeName") String storeName, @Param("cardNo") Long cardNo, @Param("orderNo") Long orderNo,
                                     @Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("adminId") Integer adminId);

    List<ExpenseReVo> selectBySearch1(@Param("phone") String phone, @Param("storeName") String storeName, @Param("cardNo") Long cardNo,@Param("orderNo") Long orderNo,
                                      @Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("list") List<Integer> list);

    ExpenseReVos selectBySearchs(@Param("phone") String phone, @Param("storeName") String storeName, @Param("cardNo") Long cardNo,@Param("orderNo") Long orderNo,
                                 @Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("adminId") Integer adminId);

    ExpenseReVos selectBySearch1s(@Param("phone") String phone, @Param("storeName") String storeName, @Param("cardNo") Long cardNo,@Param("orderNo") Long orderNo,
                                  @Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("list") List<Integer> list);

}
