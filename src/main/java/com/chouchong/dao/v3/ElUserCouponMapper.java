package com.chouchong.dao.v3;

import com.chouchong.entity.v3.ElUserCoupon;

public interface ElUserCouponMapper {
    int insert(ElUserCoupon record);

    int insertSelective(ElUserCoupon record);
}