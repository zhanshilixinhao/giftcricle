package com.chouchong.dao.gift.item;

import com.chouchong.entity.gift.item.RecommendedToday;
import com.chouchong.service.gift.item.vo.RecommendVo;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.List;

public interface RecommendedTodayMapper {
    int insert(RecommendedToday record);

    int insertSelective(RecommendedToday record);

    List<RecommendVo> selectBySearch(@Param("name") String name,@Param("day") Long day);

    RecommendedToday selectByDayAndItemId(@Param("day") Long day,@Param("itemId") Integer itemId);


    RecommendedToday selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendedToday record);
}
