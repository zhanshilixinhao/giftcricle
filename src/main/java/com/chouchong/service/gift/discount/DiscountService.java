package com.chouchong.service.gift.discount;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2018/7/24
 */
public interface DiscountService {
    /**
     * 背包物品折现记录列表
     *
     * @param nickname 用户昵称
     * @param title    商品标题
     * @param type     1-物品，2-虚拟物品
     * @param status   折现状态，1-等待折现，2-折现完成，3-折现失败,4-删除记录
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    Response discountList(PageQuery pageQuery, String nickname, String title, Byte type, Byte status);

    /**
     * 删除折现记录
     *
     * @param id 折现记录Id
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    Response delDiscount(Integer id);

    /**
     * 确认折现
     *
     * @param id 折现记录Id
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    Response confirmDiscount(Integer id);

    /**
     * 拒绝折现
     *
     * @param id 折现记录Id
     * @param explain 折现说明
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    Response refuseDiscount(Integer id, String explain);
}
