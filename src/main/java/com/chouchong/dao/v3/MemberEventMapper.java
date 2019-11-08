package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberEvent;
import com.chouchong.service.v3.vo.EventVo;

import java.util.List;

public interface MemberEventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberEvent record);

    int insertSelective(MemberEvent record);

    MemberEvent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberEvent record);

    int updateByPrimaryKey(MemberEvent record);

    List<EventVo> selectByAll(Integer adminId);

    /**
     * 所有礼遇圈添加的活动
     * @param list
     * @return
     */
    List<EventVo> selectAllByAdminIds(List<Integer> list);

    List<EventVo> selectByCardId(Integer cardId);
}
