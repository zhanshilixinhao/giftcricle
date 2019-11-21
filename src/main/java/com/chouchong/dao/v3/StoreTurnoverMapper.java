package com.chouchong.dao.v3;


import com.chouchong.entity.v3.StoreTurnover;
import com.chouchong.service.v3.vo.TurnoverVo;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface StoreTurnoverMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreTurnover record);

    int insertSelective(StoreTurnover record);

    StoreTurnover selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreTurnover record);

    int updateByPrimaryKey(StoreTurnover record);

    List<TurnoverVo> selectBySearch(@Param("eventName") String eventName, @Param("title") String title,
                                    @Param("startTime") Long startTime, @Param("endTime") Long endTime,
                                    @Param("storeId") Integer storeId, @Param("merchantId") Integer merchantId);


}