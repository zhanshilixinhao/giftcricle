package com.chouchong.dao.home.message;

import com.chouchong.entity.home.message.AppMessage;
import com.chouchong.service.home.message.vo.PushMsgVo;

import java.util.List;
import java.util.Map;

public interface AppMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppMessage record);

    int insertSelective(AppMessage record);

    AppMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppMessage record);

    int updateByPrimaryKey(AppMessage record);

    /**
     * 通过查询获得系统消息列表
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.service.home.message.vo.PushMsgVo>
     * @author: yy
     * @Date: 2018/7/24
     */
    List<PushMsgVo> selectBySearch(Map map);
}