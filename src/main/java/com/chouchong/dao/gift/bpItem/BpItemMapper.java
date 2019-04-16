package com.chouchong.dao.gift.bpItem;

import com.chouchong.entity.gift.bpItem.BpItem;
import com.chouchong.service.gift.discount.vo.DiscountVo;

public interface BpItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BpItem record);

    int insertSelective(BpItem record);

    BpItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BpItem record);

    int updateByPrimaryKey(BpItem record);

    int updateQuantity(Long bpId);
}