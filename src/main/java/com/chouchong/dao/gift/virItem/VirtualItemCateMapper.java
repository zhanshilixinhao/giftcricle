package com.chouchong.dao.gift.virItem;

import com.chouchong.entity.gift.virItem.VirtualItemCate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VirtualItemCateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VirtualItemCate record);

    int insertSelective(VirtualItemCate record);

    VirtualItemCate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VirtualItemCate record);

    int updateByPrimaryKey(VirtualItemCate record);

    /**
     * 通过条件查询获得虚拟商品分类列表
     *
     * @param: [name 虚拟商品分类名称]
     * @return: java.util.List<com.chouchong.entity.gift.virItem.VirtualItemCate>
     * @author: yy
     * @Date: 2018/7/2
     */
    List<VirtualItemCate> selectByName(@Param("name") String name);

    /**
     * 删除商品分类
     *
     * @param: [id 虚拟商品分类id]
     * @return: int
     * @author: yy
     * @Date: 2018/7/2
     */
    int deleteByItemCateId(@Param("id") Integer id);

    /**
     * 修改虚拟商品分类状态
     *
     * @param: [id 分类id, status 分类状态]
     * @return: int
     * @author: yy
     * @Date: 2018/7/2
     */
    int updateItemCateStatus(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 获得全部的虚拟商品分类
     *
     * @param: []
     * @return: java.util.List<com.chouchong.entity.gift.virItem.VirtualItemCate>
     * @author: yy
     * @Date: 2018/7/2
     */
    List<VirtualItemCate> selectAllItemCate();
}