package com.chouchong.service.gift.themeItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.gift.themeItem.Theme;
import com.chouchong.entity.gift.themeItem.ThemeItem;

public interface ThemeItemService {
    /**
     * 获得主题商品列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response getThemeItemList(PageQuery page, String search);

    /**
     * 修改主题商品状态
     *
     * @param: [id 主题商品id , status 商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response changeStatus(Integer id, Integer status);

    /**
     * 主题商品排序
     *
     * @param: [themeItem 主题商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response setThemeItemSort(ThemeItem theme);

    /**
     * 添加主题商品
     *
     * @param: [themeId 主题id, ids 商品id集合]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response addThemeItem(Integer themeId, String ids);

    /**
     * 删除主题商品
     *
     * @param: [id 主题商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response delThemeItem(Integer id);
}
