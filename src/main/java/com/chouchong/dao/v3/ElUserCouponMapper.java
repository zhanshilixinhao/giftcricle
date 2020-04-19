package com.chouchong.dao.v3;

import com.chouchong.entity.v3.ElCouponUseLog;
import com.chouchong.entity.v3.ElCouponVo;
import com.chouchong.entity.v3.ElUserCoupon;
import com.chouchong.service.v3.vo.ForUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElUserCouponMapper {
    int insert(ElUserCoupon record);

    int insertSelective(ElUserCoupon record);

    List<ForUserVo> selectBySearch1(@Param("title") String title, @Param("phone") String phone,
                                    @Param("store") String store, @Param("startTime") Long startTime,
                                    @Param("endTime") Long endTime, @Param("list") List<Integer> list);

    List<ForUserVo> selectBySearch(@Param("title") String title, @Param("phone") String phone,
                                   @Param("store") String store, @Param("startTime") Long startTime,
                                   @Param("endTime") Long endTime, @Param("adminId") Integer adminId);

    ElCouponVo selectDetailById(Long id);

    int deleteById(Long id);

    int updateQuantity(Long num);

    int insertUseLog(ElCouponUseLog log);

    ElUserCoupon selectByKey(Long id);
}
