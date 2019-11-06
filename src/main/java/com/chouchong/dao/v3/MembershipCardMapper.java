package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MembershipCard;
import com.chouchong.service.v3.vo.CardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MembershipCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MembershipCard record);

    int insertSelective(MembershipCard record);

    MembershipCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MembershipCard record);

    int updateByPrimaryKeyWithBLOBs(MembershipCard record);

    int updateByPrimaryKey(MembershipCard record);

    List<CardVo> selectBySearch(@Param("adminId") Integer adminId, @Param("cardId") Integer cardId,
                                @Param("cardNo") Long cardNo, @Param("title") String title);

    CardVo selectById(@Param("cardId") Integer cardId);
}
