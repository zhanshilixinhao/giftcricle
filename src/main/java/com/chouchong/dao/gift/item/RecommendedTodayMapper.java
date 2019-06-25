package com.chouchong.dao.gift.item;

import com.chouchong.entity.gift.item.RecommendedToday;
import com.chouchong.service.gift.item.vo.RecommendVo;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.List;

public interface RecommendedTodayMapper {
    int insert(RecommendedToday record);

    int insertSelective(RecommendedToday record);


    RecommendedToday selectByDayAndItemId(@Param("itemId") Integer itemId,@Param("start") Long start,@Param("endTime") Long endTime);


    RecommendedToday selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendedToday record);

    List<RecommendVo> selectBySearch(@Param("name") String name,@Param("start") Long start,@Param("endTime") Long endTime);
}
