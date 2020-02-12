package com.chouchong.dao.v3;

import com.chouchong.entity.v3.ElectronicCoupons;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElectronicCouponsMapper {
    int insert(ElectronicCoupons record);

    int insertSelective(ElectronicCoupons record);

    List<ElectronicCoupons> selectBySearch(@Param("adminId") Integer adminId, @Param("title") String title);

    ElectronicCoupons selectByKey(Integer id);

    int updateByPrimaryKeySelective(ElectronicCoupons elCoupon);

    List<ElectronicCoupons> selectByAdminId(@Param("adminId") Integer adminId);
}
