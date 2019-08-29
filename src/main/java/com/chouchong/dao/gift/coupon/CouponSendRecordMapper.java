package com.chouchong.dao.gift.coupon;

import com.chouchong.entity.gift.coupon.CouponSendRecord;

public interface CouponSendRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponSendRecord record);

    int insertSelective(CouponSendRecord record);

    CouponSendRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponSendRecord record);

    int updateByPrimaryKey(CouponSendRecord record);
}
