package com.chouchong.service.gift.label;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.gift.label.LabelItem;

/**
 * @author linqin
 * @date 2019/1/15 11:26
 */

public interface LabelItemService {

    /**
     * 获取标签商品列表
     * @param pageQuery
     * @param search
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response getLabelItemList(PageQuery pageQuery, String search);

    /**
     * 修改主题商品状态
     * @param id
     * @param status
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response changeStatus(Integer id, Integer status);

    /**
     * 删除商品
     *
     * @param id 标签商品id
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response delLabelItem(Integer id);

    /**
     * 添加标签商品
     * @param labelId 标签id
     * @param ids 商品id集合
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response addLabelItem(Integer labelId, String ids);

    /**
     * 标签商品排序
     *
     * @param
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response setThemeItemSort(LabelItem labelItem);
}
