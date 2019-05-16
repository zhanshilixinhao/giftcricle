package com.chouchong.dao.order;

import com.chouchong.entity.webUser.ItemOrder;
import com.chouchong.service.order.vo.ItemOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemOrder record);

    int insertSelective(ItemOrder record);

    ItemOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemOrder record);

    int updateByPrimaryKey(ItemOrder record);

    List<ItemOrderVo> selectList(@Param("nickname") String nickname,
                                 @Param("phone") String phone,
                                 @Param("orderNo") Long orderNo,
                                 @Param("status") Byte status,
                                 @Param("payWay") Integer payWay,
                                 @Param("adminId") Integer adminId);

    int updateStatusById(Integer id);

    /**
     * 统计商品订单的数据
     *
     * @param: [params]
     * @return: java.util.List
     * @author: yy
     * @Date: 2018/7/31
     */
    List<Integer> getOrderStatistics(Map params);

    int selectUnredCount(@Param("adminId") Integer adminId,@Param("start") Long start);
}
