package com.chouchong.dao.order;

import com.chouchong.entity.webUser.ItemOrderDetail;

import java.util.List;
import java.util.Map;

public interface ItemOrderDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemOrderDetail record);

    int insertSelective(ItemOrderDetail record);

    ItemOrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemOrderDetail record);

    int updateByPrimaryKey(ItemOrderDetail record);

    int updateStatusById(Long orderNo);

    List<Map> selectByOrderNo(Long orderNo);

}