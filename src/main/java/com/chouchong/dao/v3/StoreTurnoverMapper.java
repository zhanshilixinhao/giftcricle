package com.chouchong.dao.v3;


import com.chouchong.entity.v3.StoreTurnover;
import com.chouchong.service.v3.vo.TurnoverVo;
import com.chouchong.service.v3.vo.TurnoverVos;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreTurnoverMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreTurnover record);

    int insertSelective(StoreTurnover record);

    StoreTurnover selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreTurnover record);

    int updateByPrimaryKey(StoreTurnover record);

    List<TurnoverVo> selectBySearch(@Param("eventId") Integer eventId, @Param("title") String title,
                                    @Param("startTime") Long startTime, @Param("endTime") Long endTime,
                                    @Param("storeId") Integer storeId, @Param("merchantId") Integer merchantId,
                                    @Param("phone") String phone, @Param("storeName") String storeName);


    TurnoverVos selectBySearch1(@Param("eventId") Integer eventId, @Param("title") String title,
                                      @Param("startTime") Long startTime, @Param("endTime") Long endTime, @Param("storeId") Integer storeId,
                                      @Param("merchantId") Integer merchantId,@Param("phone") String phone, @Param("storeName") String storeName);

    List<StoreTurnover> selectByStoreMId(Integer id);
}
