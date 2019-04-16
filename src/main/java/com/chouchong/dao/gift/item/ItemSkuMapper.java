package com.chouchong.dao.gift.item;

import com.chouchong.entity.SkuListVo;
import com.chouchong.entity.gift.item.ItemSku;
import com.chouchong.service.gift.item.vo.SkuContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemSku record);

    int insertSelective(ItemSku record);

    ItemSku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemSku record);

    int updateByPrimaryKey(ItemSku record);

    /**
     * 获得商品的组合sku
     *
     * @param: [id]
     * @return: java.util.List<com.chouchong.entity.gift.item.ItemSku>
     * @author: yy
     * @Date: 2018/6/28
     */
    List<ItemSku> selectByItemId(@Param("id") Integer id);

    /**
     * 通过搜索条件获得sku列表
     *
     * @param: [map 搜索条件]
     * @return: java.util.List<com.chouchong.entity.gift.item.ItemSku>
     * @author: yy
     * @Date: 2018/6/29
     */
    List<ItemSku> selectBySearch(Map map);

    /**
     * 获得sku组合内容
     *
     * @param: [id sku的id]
     * @return: java.util.List<com.chouchong.service.gift.item.vo.SkuContent>
     * @author: yy
     * @Date: 2018/6/29
     */
    List<SkuContent> selectGroupContent(@Param("id") Integer id);

    /**
     * 根据itemId删除sku值
     *
     * @param itemId
     * @return
     */
    int deleteByItemId(Integer itemId);

    List<ItemSku> selectAll();

    SkuListVo selectDetailBySkuId(@Param("skuId") Integer id);

    int updateBatch(@Param("id") Integer id,
                    @Param("linqin") String linqin);
}
