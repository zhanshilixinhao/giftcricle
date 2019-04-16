package com.chouchong.dao.gift.themeItem;

import com.chouchong.entity.gift.themeItem.ThemeItem;
import com.chouchong.service.gift.themeItem.vo.ThemeItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ThemeItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThemeItem record);

    int insertSelective(ThemeItem record);

    ThemeItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThemeItem record);

    int updateByPrimaryKey(ThemeItem record);

    /**
     * 通过查询条件获得主题商品列表
     *
     * @param: [map]
     * @return: java.util.List<com.chouchong.service.gift.themeItem.vo.ThemeItemVo>
     * @author: yy
     * @Date: 2018/7/3
     */
    List<ThemeItemVo> selectBySearch(Map map);

    /**
     * 通过商品id和主题id获得主题商品
     *
     * @param: [themeId  主题id, id 商品id]
     * @return: com.chouchong.entity.gift.themeItem.ThemeItem
     * @author: yy
     * @Date: 2018/7/3
     */
    ThemeItem selectByThemeIdAndItemId(@Param("themeId") Integer themeId, @Param("id") int id);

    /**
     * 通过主题删除该主题下的主题商品
     *
     * @param: [id]
     * @return: int
     * @author: yy
     * @Date: 2018/7/4
     */
    int deleteByThemeId(@Param("id") Integer id);
}