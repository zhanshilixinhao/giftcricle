package com.chouchong.dao.order;

import com.chouchong.entity.webUser.ConsignmentOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ConsignmentOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConsignmentOrder record);

    int insertSelective(ConsignmentOrder record);

    ConsignmentOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConsignmentOrder record);

    int updateByPrimaryKey(ConsignmentOrder record);

    List<Map> selectAll(@Param("nickname") String nickname,
                        @Param("phone")String phone,
                        @Param("orderNo")Long orderNo,
                        @Param("status")Byte status,
                        @Param("payWay")Byte payWay,
                        @Param("type")Byte type);

    int updateById(Integer id);

    /**
     * 寄售台订单统计
     *
     * @param: [params 查询条件]
     * @return: java.util.List<java.lang.Integer>
     * @author: yy
     * @Date: 2018/8/1
     */
    List<Integer> getOrderStatistics(Map params);
}