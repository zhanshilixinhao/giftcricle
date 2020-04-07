package com.chouchong.dao.v3;

import com.chouchong.entity.v3.ElCouponUseLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElCouponUseLogMapper {
    List<ElCouponUseLog> selectList(@Param("log") ElCouponUseLog log, @Param("adminId") Integer adminId);

    List<ElCouponUseLog> selectList1(@Param("log") ElCouponUseLog log, @Param("list") List<Integer> list);
}
