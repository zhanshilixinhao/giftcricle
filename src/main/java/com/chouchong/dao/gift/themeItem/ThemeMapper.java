package com.chouchong.dao.gift.themeItem;

import com.chouchong.entity.gift.themeItem.Theme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThemeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Theme record);

    int insertSelective(Theme record);

    Theme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Theme record);

    int updateByPrimaryKey(Theme record);

    /**
     * 通过主题名称获得主题列表
     *
     * @param: [name 主题名称]
     * @return: java.util.List<com.chouchong.entity.gift.themeItem.Theme>
     * @author: yy
     * @Date: 2018/7/3
     */
    List<Theme> selectByName(@Param("name") String name);

    /**
     * 删除主题
     *
     * @param: [id]
     * @return: int
     * @author: yy
     * @Date: 2018/7/3
     */
    int deleteByThemeId(@Param("id") Integer id);

    /**
     * 获得全部的主题
     *
     * @param: []
     * @return: java.util.List<com.chouchong.entity.gift.themeItem.Theme>
     * @author: yy
     * @Date: 2018/7/3
     */
    List<Theme> selectAllTheme();
}