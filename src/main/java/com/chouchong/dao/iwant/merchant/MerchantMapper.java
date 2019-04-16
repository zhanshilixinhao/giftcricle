package com.chouchong.dao.iwant.merchant;

import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.service.iwant.vo.MerchantVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MerchantMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    Merchant selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);

    /**
     * 获得商家列表
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.entity.iwant.merchant.Merchant>
     * @author: yy
     * @Date: 2018/7/6
     */
    List<MerchantVo> selectBySearch(Map map);

    /**
     * 通过管理员id查找店铺
     *
     * @param: [adminId]
     * @return: com.chouchong.entity.iwant.merchant.Merchant
     * @author: yy
     * @Date: 2018/7/9
     */
    Merchant selectByAdminId(@Param("adminId") Integer adminId);
}