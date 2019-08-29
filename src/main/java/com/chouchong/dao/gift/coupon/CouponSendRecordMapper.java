package com.chouchong.dao.gift.coupon;

import com.chouchong.entity.gift.coupon.CouponSendRecord;
import com.chouchong.service.gift.coupon.vo.CouponVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponSendRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponSendRecord record);

    int insertSelective(CouponSendRecord record);

    CouponSendRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponSendRecord record);

    int updateByPrimaryKey(CouponSendRecord record);

    List<CouponVo> selectByTitle(@Param("title") String title, @Param("id") Integer id);
}
