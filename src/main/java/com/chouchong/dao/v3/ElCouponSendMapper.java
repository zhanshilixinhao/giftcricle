package com.chouchong.dao.v3;

import com.chouchong.entity.v3.ElCouponSend;

public interface ElCouponSendMapper {
    int insert(ElCouponSend record);

    int insertSelective(ElCouponSend record);
}