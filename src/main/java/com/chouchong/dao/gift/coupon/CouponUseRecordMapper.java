package com.chouchong.dao.gift.coupon;

import com.chouchong.entity.gift.coupon.CouponUseRecord;

public interface CouponUseRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponUseRecord record);

    int insertSelective(CouponUseRecord record);

    CouponUseRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponUseRecord record);

    int updateByPrimaryKey(CouponUseRecord record);
}
