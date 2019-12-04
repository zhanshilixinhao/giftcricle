package com.chouchong.dao.v3;


import com.chouchong.entity.v3.CardRebate;
import com.chouchong.service.v3.vo.RefundVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CardRebateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CardRebate record);

    int insertSelective(CardRebate record);

    CardRebate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CardRebate record);

    int updateByPrimaryKey(CardRebate record);

    List<RefundVo> selectBySearch(@Param("phone") String phone, @Param("storeName") String storeName,
                                  @Param("cardNo") Long cardNo, @Param("startTime") Long startTime,
                                  @Param("endTime") Long endTime, @Param("adminId") Integer adminId);

    List<RefundVo> selectBySearch1(@Param("phone") String phone, @Param("storeName") String storeName,
                                   @Param("cardNo") Long cardNo, @Param("startTime") Long startTime,
                                   @Param("endTime") Long endTime, @Param("list") List<Integer> list);

}
