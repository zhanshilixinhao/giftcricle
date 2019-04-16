package com.chouchong.dao.gift.item;

import com.chouchong.entity.gift.item.ItemFeature;
import com.chouchong.service.gift.item.vo.FeatureVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemFeatureMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemFeature record);

    int insertSelective(ItemFeature record);

    ItemFeature selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemFeature record);

    int updateByPrimaryKey(ItemFeature record);

    /**
     * 根据条件查询商品属性列表
     *
     * @param: [name 商品属性名称]
     * @return: java.util.List<com.chouchong.entity.gift.item.ItemFeature>
     * @author: yy
     * @Date: 2018/6/28
     */
    List<FeatureVo> selectByName(@Param("name") String name, @Param("adminId") Integer adminId);

    /**
     * 删除商品属性
     *
     * @param: [id 商品属性id]
     * @return: int
     * @author: yy
     * @Date: 2018/6/28
     */
    int deleteByItemFeatureId(@Param("id") Integer id);

    /**
     * 获得全部的商品属性
     *
     * @param: []
     * @return: java.util.List<com.chouchong.entity.gift.item.ItemFeature>
     * @author: yy
     * @Date: 2018/6/28
     */
    List<ItemFeature> getAllItemFeature();
}
