package com.chouchong.dao.webUser;

import com.chouchong.common.Response;
import com.chouchong.entity.webUser.ChargeOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ChargeOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeOrder record);

    int insertSelective(ChargeOrder record);

    ChargeOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeOrder record);

    int updateByPrimaryKey(ChargeOrder record);

    List<Map> selectList(@Param("nickname") String nickname,
                         @Param("phone") String phone,
                         @Param("orderNo") Long orderNo,
                         @Param("status") Byte status,
                         @Param("payWay") Byte payWay);

    int deleteById(Integer id);

    /**
     * 充值订单统计
     *
     * @param: [params]
     * @return: java.util.List<java.lang.Integer>
     * @author: yy
     * @Date: 2018/8/1
     */
    List<Integer> getOrderStatistics(Map params);

    int selectUnredCount(Long start);
}
