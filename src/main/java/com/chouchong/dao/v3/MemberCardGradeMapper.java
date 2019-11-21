package com.chouchong.dao.v3;


import com.chouchong.entity.v3.CardGrade;
import com.chouchong.entity.v3.MemberCardGrade;

import java.util.List;

public interface MemberCardGradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberCardGrade record);

    int insertSelective(MemberCardGrade record);

    MemberCardGrade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberCardGrade record);

    int updateByPrimaryKey(MemberCardGrade record);

    List<CardGrade> selectByCardId(Integer cardId);

    int deleteByGradeId(Integer gradeId);
}
