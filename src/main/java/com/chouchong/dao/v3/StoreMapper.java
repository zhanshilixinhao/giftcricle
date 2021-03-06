package com.chouchong.dao.v3;


import com.chouchong.entity.v3.Store;
import com.chouchong.service.v3.vo.StoreVo;
import com.chouchong.service.v4.vo.ElVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);

    /**
     * 获取门店列表
     * @param adminId
     * @param merchantId
     * @param name
     * @param address
     * @return
     */
    List<Store> selectBySearch(@Param("adminId") Integer adminId, @Param("merchantId") Integer merchantId,
                               @Param("name") String name, @Param("address") String address, @Param("merchant") String merchant);

    StoreVo selectById(@Param("id") Integer id);

    List<StoreVo> selectByAll(@Param("merchantId") Integer merchantId);

    Store selectByAdminId(@Param("adminId") Integer adminId);

    @Select("SELECT id,title FROM electronic_coupons WHERE admin_id = #{adminId}")
    List<ElVo> findByAdminId(@Param("adminId") Integer adminId);
}
