package com.chouchong.dao.gift.item;

import com.chouchong.entity.gift.item.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);

    /**
     * 获得品牌名称列表
     *
     * @param: [name 品牌名称]
     * @return: java.util.List<com.chouchong.entity.brand.Brand>
     * @author: yy
     * @Date: 2018/6/25
     */
    List<Brand> selectByName(@Param("name") String name);

    /**
     * 删除品牌
     *
     * @param: [id 品牌id]
     * @return: int
     * @author: yy
     * @Date: 2018/6/26
     */
    int deleteByUpdateStatus(@Param("id") Integer id);

    /**
     * 获得所有未删除品牌
     *
     * @param: []
     * @return: java.util.List<com.chouchong.entity.brand.Brand>
     * @author: yy
     * @Date: 2018/6/26
     */
    List<Brand> selectAllBrand();
}