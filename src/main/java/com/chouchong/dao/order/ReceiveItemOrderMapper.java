package com.chouchong.dao.order;

import com.chouchong.entity.webUser.ReceiveItemOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReceiveItemOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReceiveItemOrder record);

    int insertSelective(ReceiveItemOrder record);

    ReceiveItemOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReceiveItemOrder record);

    int updateByPrimaryKey(ReceiveItemOrder record);

    List<Map> selectAll(@Param("nickname") String nickname,
                        @Param("phone")String phone,
                        @Param("orderNo")Long orderNo,
                        @Param("status")Byte status,
                        @Param("adminId")Integer adminId);

    int updateStatusById(Integer id);



}
