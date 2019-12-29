package com.chouchong.dao.v3;


import com.chouchong.entity.v3.StoreMemberCharge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreMemberChargeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreMemberCharge record);

    int insertSelective(StoreMemberCharge record);

    StoreMemberCharge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreMemberCharge record);

    int updateByPrimaryKey(StoreMemberCharge record);

    List<StoreMemberCharge> selectByUserIdCardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);

    StoreMemberCharge selectByUserIdCardIdOrderNo(@Param("cardId") Integer cardId,
                                                  @Param("userId") Integer userId,
                                                  @Param("orderNo") Long orderNo);
    StoreMemberCharge selectByUserIdCardIdOrderNo1(@Param("cardId") Integer cardId,
                                                  @Param("userId") Integer userId,
                                                  @Param("orderNo") Long orderNo);
}
