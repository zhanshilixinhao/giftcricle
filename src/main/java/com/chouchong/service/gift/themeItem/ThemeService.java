package com.chouchong.service.gift.themeItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.gift.themeItem.Theme;

public interface ThemeService {
    /**
     * 获得主题列表
     *
     * @param: [page 分页信息, name 主题名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/3
     */
    Response getThemeList(PageQuery page, String name);

    /**
     * 添加主题
     *
     * @param: [name 主题名称, cover 主题封面]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response addTheme(String name, String cover);

    /**
     * 修改主题
     *
     * @param: [theme 主题信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response updateTheme(Theme theme);

    /**
     * 修改主题状态
     *
     * @param: [id 主题id, status 主题状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response updateStatus(Integer id, Integer status);

    /**
     * 删除主题
     *
     * @param: [id 主题id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response delTheme(Integer id);

    /**
     * 获得全部主题
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response getAllTheme();
}
