package com.chouchong.dao.gift.coupon;

import com.chouchong.entity.gift.coupon.CouponUseRecord;
import com.chouchong.service.gift.coupon.vo.CouponVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponUseRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponUseRecord record);

    int insertSelective(CouponUseRecord record);

    CouponUseRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponUseRecord record);

    int updateByPrimaryKey(CouponUseRecord record);

    List<CouponVo> selectByTitle( @Param("title") String title,@Param("id") Integer id);
}
