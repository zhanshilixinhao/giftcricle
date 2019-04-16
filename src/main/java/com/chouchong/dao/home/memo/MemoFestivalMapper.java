package com.chouchong.dao.home.memo;


import com.chouchong.entity.home.Festival;
import com.chouchong.entity.home.memo.MemoFestival;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemoFestivalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemoFestival record);

    int insertSelective(MemoFestival record);

    MemoFestival selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemoFestival record);

    int updateByPrimaryKey(MemoFestival record);

    /**
     * 查询节日列表
     * @param name
     * @return
     */
    List<MemoFestival> selectAllByName(@Param("name") String name);

    /**
     * 查询所有节日事件
     * @return
     */
    List<MemoFestival> selectAll();
}
