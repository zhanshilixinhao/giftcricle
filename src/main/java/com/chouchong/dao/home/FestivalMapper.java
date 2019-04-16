package com.chouchong.dao.home;


import com.chouchong.entity.home.Festival;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FestivalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Festival record);

    int insertSelective(Festival record);

    Festival selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Festival record);

    int updateByPrimaryKey(Festival record);

    /**
     * 查询最近5天节日
     * @return
     */
    List<Festival> selectByAll();

    /**
     * 后台获取节日列表
     * @param name
     * @param time
     * @return
     */
    List<Festival> selectByName(@Param("name") String name,@Param("time") Long time);

    /**
     * 查询所i有节日
     * @return
     */
    List<Festival> selectAll();
}
