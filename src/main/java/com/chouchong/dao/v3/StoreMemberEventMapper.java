package com.chouchong.dao.v3;


import com.chouchong.entity.v3.StoreMemberCharge;
import com.chouchong.entity.v3.StoreMemberEvent;
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
}
