package com.chouchong.dao.v3;


import com.chouchong.entity.v3.StoreMemberCharge;
import com.chouchong.entity.v3.StoreMemberEvent;
import com.chouchong.service.v3.vo.EventCardVo;
import com.chouchong.service.v3.vo.UserCardVo2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMemberEventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreMemberEvent record);

    int insertSelective(StoreMemberEvent record);

    StoreMemberEvent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreMemberEvent record);

    int updateByPrimaryKey(StoreMemberEvent record);

    List<StoreMemberEvent> selectByUserIdCardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);

    List<StoreMemberEvent> selectByUserIdCardId1(@Param("userId") Integer userId, @Param("cardId") Integer cardId);

    List<StoreMemberEvent> selectByUserIdCardIds(@Param("userId") Integer userId, @Param("cardId") Integer cardId);

    StoreMemberEvent selectByUserIdCardIdOrderNo(@Param("cardId") Integer cardId, @Param("userId") Integer userId, @Param("orderNo") Long orderNo);
    StoreMemberEvent selectByUserIdCardIdOrderNo1(@Param("cardId") Integer cardId, @Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    List<EventCardVo> selectAll(@Param("userId") Integer userId, @Param("cardId") Integer cardId);
}
