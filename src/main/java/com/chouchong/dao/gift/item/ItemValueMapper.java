package com.chouchong.dao.gift.item;

import com.chouchong.entity.gift.item.ItemValue;
import com.chouchong.service.gift.item.vo.FeatureValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemValue record);

    int insertSelective(ItemValue record);

    ItemValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemValue record);

    int updateByPrimaryKey(ItemValue record);

    List<FeatureValue> selectByItemId(@Param("id") Integer id);

    int deleteByItemId(Integer itemId);
}
