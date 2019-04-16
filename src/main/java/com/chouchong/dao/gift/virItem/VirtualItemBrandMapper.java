package com.chouchong.dao.gift.virItem;

import com.chouchong.entity.gift.virItem.VirtualItemBrand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VirtualItemBrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VirtualItemBrand record);

    int insertSelective(VirtualItemBrand record);

    VirtualItemBrand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VirtualItemBrand record);

    int updateByPrimaryKey(VirtualItemBrand record);

    /**
     * 获得虚拟商品品牌列表
     *
     * @param: [name 虚拟商品品牌名称]
     * @return: java.util.List<com.chouchong.entity.gift.virItem.VirtualItemBrand>
     * @author: yy
     * @Date: 2018/7/2
     */
    List<VirtualItemBrand> selectByName(@Param("name") String name);

    /**
     * 通过id删除虚拟商品品牌
     *
     * @param: [id]
     * @return: int
     * @author: yy
     * @Date: 2018/7/2
     */
    int deleteByVirItemBrandId(@Param("id") Integer id);

    /**
     * 获取全部的虚拟商品品牌
     *
     * @param: []
     * @return: java.util.List<com.chouchong.entity.gift.virItem.VirtualItemBrand>
     * @author: yy
     * @Date: 2018/7/2
     */
    List<VirtualItemBrand> selectAllVirItemBrand();
}