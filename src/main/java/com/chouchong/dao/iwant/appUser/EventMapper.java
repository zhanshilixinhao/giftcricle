package com.chouchong.dao.iwant.appUser;


import com.chouchong.entity.iwant.appUser.Event;
import com.chouchong.service.iwant.vo.EventVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Event record);

    int insertSelective(Event record);

    Event selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Event record);

    int updateByPrimaryKey(Event record);


    /**
     * 获得送礼事件列表
     *
     * @param: [name 事件名称]
     * @return: java.util.List<com.chouchong.entity.iwant.appUser.Event>
     * @author: yy
     * @Date: 2018/7/11
     */
    List<EventVo> selectByName(@Param("name") String name);

}
