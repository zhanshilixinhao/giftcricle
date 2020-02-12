package com.chouchong.dao.v3;

import com.chouchong.entity.v3.ElCouponSend;
import com.chouchong.service.v3.vo.SendFriendVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElCouponSendMapper {
    int insert(ElCouponSend record);

    int insertSelective(ElCouponSend record);

    List<SendFriendVo> selectBySearch(@Param("nickname") String nickname, @Param("title") String title,
                                      @Param("status") Byte status, @Param("startTime") Long startTime,
                                      @Param("endTime") Long endTime, @Param("list") List<Integer> list);
}
