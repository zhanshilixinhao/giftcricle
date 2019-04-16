package com.chouchong.dao.gift.item;

import com.chouchong.entity.gift.item.ItemCategory;
import com.chouchong.service.gift.item.vo.ItemCategoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemCategory record);

    int insertSelective(ItemCategory record);

    ItemCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemCategory record);

    int updateByPrimaryKey(ItemCategory record);

    /**
     * 通过分类名称查找分类列表
     *
     * @param: [name 分类名称]
     * @return: java.util.List<com.chouchong.entity.gift.item.ItemCategory>
     * @author: yy
     * @Date: 2018/6/25
     */
    List<ItemCategory> selectByName(@Param("name") String name);

    /**
     * 删除商品分类
     *
     * @param: [id 商品分类id]
     * @return: int
     * @author: yy
     * @Date: 2018/6/25
     */
    int deleteByItemCateId(@Param("id") Integer id);

    /**
     * 设置商品分类状态
     *
     * @param: [id 商品分类id, status 状态]
     * @return: int
     * @author: yy
     * @Date: 2018/6/25
     */
    int updateItemCateStatus(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 获取商品的所有分类
     *
     * @param: []
     * @return: java.util.List<com.chouchong.entity.gift.item.ItemCategory>
     * @author: yy
     * @Date: 2018/6/26
     */
    List<ItemCategoryVo> selectAllItemCate();

    /**
     * 根据父级id查询列表
     * @param pid
     * @return
     */
    List<ItemCategory> selectByPid(@Param("pid") Integer pid);


}
