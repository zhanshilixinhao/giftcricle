package com.chouchong.dao.gift.coupon;

import com.chouchong.entity.gift.coupon.Coupon;
import com.chouchong.service.home.welfare.vo.ItemListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    /**
     * 通过优惠券标题获得券列表
     *
     * @param: [title 优惠券标题]
     * @return: java.util.List<com.chouchong.entity.gift.coupon.Coupon>
     * @author: yy
     * @Date: 2018/7/9
     */
    List<Coupon> selectByTitle(@Param("title") String title, @Param("id") Integer id, @Param("brandName") String brandName);

    List<ItemListVo> selectByTitleAdmin(@Param("title") String title,@Param("adminId") Integer adminId);
}
