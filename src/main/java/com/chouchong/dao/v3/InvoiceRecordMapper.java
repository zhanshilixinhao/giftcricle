package com.chouchong.dao.v3;

import com.chouchong.entity.v3.InvoiceRecord;
import com.chouchong.service.v3.vo.InvoiceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvoiceRecordMapper {
    int insert(InvoiceRecord record);

    int insertSelective(InvoiceRecord record);

    List<InvoiceVo> selectByUserIdCardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);

    List<InvoiceVo> selectByKey(@Param("phone") String phone, @Param("startTime") Long startTime,
                                @Param("endTime") Long endTime, @Param("list") List<Integer> list);

    List<InvoiceVo> selectByKey1(@Param("phone") String phone, @Param("startTime") Long startTime,
                                 @Param("endTime") Long endTime, @Param("adminId") Integer adminId);
}
