package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberCard;
import com.chouchong.entity.v3.MemberEvent;

import java.util.List;

public interface MemberCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberCard record);

    int insertSelective(MemberCard record);

    MemberCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberCard record);

    int updateByPrimaryKey(MemberCard record);


    void deleteByCardId(Integer cardId);

    List<MemberEvent> selectByCardIds(List<Integer> list);

    List<MemberEvent> selectByCardId();

    MemberEvent selectEventByCardId(Integer cardId);
}
