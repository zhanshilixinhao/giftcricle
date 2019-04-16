package com.chouchong.dao.gift.virItem;

import com.chouchong.entity.gift.virItem.VirtualItem;
import com.chouchong.service.home.welfare.vo.ItemListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VirtualItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VirtualItem record);

    int insertSelective(VirtualItem record);

    VirtualItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VirtualItem record);

    int updateByPrimaryKey(VirtualItem record);

    /**
     * 通过查询条件获得虚拟商品列表
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.entity.gift.virItem.VirtualItem>
     * @author: yy
     * @Date: 2018/7/2
     */
    List<VirtualItem> selectBySearch(Map map);

    /**
     * 删除虚拟商品
     *
     * @param: [id]
     * @return: int
     * @author: yy
     * @Date: 2018/7/2
     */
    int deleteByVirItemId(@Param("id") Integer id);

    List<ItemListVo> selectByTitle(@Param("title") String title);
}
