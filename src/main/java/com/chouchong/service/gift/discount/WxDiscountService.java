package com.chouchong.service.gift.discount;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2019/3/19 10:45
 */

public interface WxDiscountService {

    /**
     * 小程序背包物品提现记录列表
     *
     * @param page
     * @param cardHolder 收款人姓名
     * @param type       1支付宝 2银行卡
     * @param status     1 申请折现 2 成功 3 失败，-1删除
     * @return
     * @author linqin
     * @date 2019/3/19 10:44
     */
    Response getWxDiscountList(PageQuery page, String cardHolder, Byte type, Byte status);

    /**
     * 删除小程序背包物品提现记录
     *
     * @param id
     * @return
     * @author linqin
     * @date 2019/3/19
     */
    Response deleteWxDiscount(Integer id);
    /**
     * 小程序折现处理成功
     *
     * @param id
     * @return
     * @author linqin
     * @date 2019/3/19
     */
    Response handleWXDiscount(Integer id, Byte status);
}
