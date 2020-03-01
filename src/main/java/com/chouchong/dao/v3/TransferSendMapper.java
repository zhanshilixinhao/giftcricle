package com.chouchong.dao.v3;

import com.chouchong.entity.v3.TransferSend;
import com.chouchong.service.v3.vo.TransferVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransferSendMapper {
    int insert(TransferSend record);

    int insertSelective(TransferSend record);

    List<TransferVo> selectBySearch(@Param("nickname") String nickname, @Param("title") String title,
                                    @Param("status") Byte status, @Param("startTime") Long startTime,
                                    @Param("endTime") Long endTime, @Param("list") List<Integer> list);

    List<TransferSend> selectByUserIdCardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);
}
