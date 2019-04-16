package com.chouchong.dao.home;

import com.chouchong.entity.home.WxDiscount;
import com.chouchong.service.gift.discount.vo.WxDiscountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxDiscountMapper {
    int insert(WxDiscount record);

    int insertSelective(WxDiscount record);

    int updateByPrimaryKeySelective(WxDiscount record);

    /**
     * 后台获取列表
     * @param cardHolder
     * @param type
     * @param status
     * @return
     */
    List<WxDiscountVo> selectAllBySearch(@Param("cardHolder") String cardHolder,@Param("type") Byte type,
                                         @Param("status")Byte status);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    WxDiscount selectById(Integer id);
}
