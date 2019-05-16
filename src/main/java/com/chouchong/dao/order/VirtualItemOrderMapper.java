package com.chouchong.dao.order;

import com.chouchong.entity.webUser.VirtualItemOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VirtualItemOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VirtualItemOrder record);

    int insertSelective(VirtualItemOrder record);

    VirtualItemOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VirtualItemOrder record);

    int updateByPrimaryKey(VirtualItemOrder record);

    List<Map> selectAll(@Param("nickname") String nickname,
                        @Param("phone")String phone,
                        @Param("orderNo")Long orderNo,
                        @Param("status")Byte status,
                        @Param("payWay")Integer payWay);

    int updateStatusById(Integer id);

    /**
     * 统计虚拟商品订单
     *
     * @param: [params]
     * @return: java.util.List<java.lang.Integer>
     * @author: yy
     * @Date: 2018/7/31
     */
    List<Integer> getOrderStatistics(Map params);

    int selectUnredCount(@Param("start") Long start);
}
