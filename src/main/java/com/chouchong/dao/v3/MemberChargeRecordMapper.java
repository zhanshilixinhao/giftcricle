package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberChargeRecord;
import com.chouchong.service.v3.vo.ChargeReVo;
import com.chouchong.service.v3.vo.ChargeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberChargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberChargeRecord record);

    int insertSelective(MemberChargeRecord record);

    MemberChargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberChargeRecord record);

    int updateByPrimaryKey(MemberChargeRecord record);


    List<ChargeVo> selectByUserIdCardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);

    List<ChargeReVo> selectBySearch(@Param("phone") String phone, @Param("storeName") String storeName, @Param("cardNo") Long cardNo,
                                    @Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("adminId") Integer adminId);

    List<ChargeReVo> selectBySearch1(@Param("phone") String phone, @Param("storeName") String storeName, @Param("cardNo") Long cardNo,
                                     @Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("list") List<Integer> list);

}
