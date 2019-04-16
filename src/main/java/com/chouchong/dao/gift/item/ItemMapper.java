package com.chouchong.dao.gift.item;

import com.chouchong.entity.gift.item.Item;
import com.chouchong.service.gift.item.vo.ItemVo;
import com.chouchong.service.home.welfare.vo.ItemListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    /**
     * 通过查询条件获得商品列表
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.entity.gift.item.Item>
     * @author: yy
     * @Date: 2018/6/26
     */
    List<ItemVo> selectBySearch(Map map);

    /**
     * 插入商品详情
     *
     * @param: [detail]
     * @return: int
     * @author: yy
     * @Date: 2018/6/27
     */
    int insertItemDetail(@Param("id") Integer id, @Param("detail") String detail);

    /**
     * 根据商品id 获得商品详情
     *
     * @param: [id]
     * @return: java.lang.String
     * @author: yy
     * @Date: 2018/6/27
     */
    String selectDetailByItemId(@Param("id") Integer id);

    /**
     * 修改商品详情富文本
     *
     * @param: [id 商品id, detail 商品详情]
     * @return: int
     * @author: yy
     * @Date: 2018/6/27
     */
    int updateItemDetail(@Param("id") Integer id, @Param("detail") String detail);

    /**
     * 删除商品
     *
     * @param: [id 商品id]
     * @return: int
     * @author: yy
     * @Date: 2018/6/27
     */
    int deleteByItemId(Integer id);


    List<ItemListVo> selectByTitle(@Param("title") String title);

    Integer selectPidByItemId(Integer id);
}
