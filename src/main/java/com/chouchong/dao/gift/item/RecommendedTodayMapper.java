package com.chouchong.dao.gift.item;

import com.chouchong.entity.gift.item.RecommendedToday;

public interface RecommendedTodayMapper {
    int insert(RecommendedToday record);

    int insertSelective(RecommendedToday record);
}