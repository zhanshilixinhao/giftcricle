package com.chouchong.controller.gift.discount;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.discount.WxDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/3/19 10:44
 */
@RestController
@RequestMapping("manage/wx/discount")
public class WxDiscountController {

    @Autowired
    private WxDiscountService wxDiscountService;

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
    @PostMapping("list")
    public Response getWxDiscountList(PageQuery page, String cardHolder, Byte type, Byte status) {
        return wxDiscountService.getWxDiscountList(page, cardHolder, type, status);
    }


    /**
     * 小程序折现处理
     *
     * @param id     折现记录id
     * @param status 2成功 3 失败
     * @return
     * @author linqin
     * @date 2019/3/19
     */
    @PostMapping("handle")
    public Response handleWXDiscount(Integer id, Byte status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (status != 2 && status != 3 ){
            return ResponseFactory.errMissingParameter();
        }
        return wxDiscountService.handleWXDiscount(id, status);
    }


    /**
     * 删除小程序背包物品提现记录
     *
     * @param id
     * @return
     * @author linqin
     * @date 2019/3/19
     */
    @PostMapping("del")
    public Response deleteWxDiscount(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return wxDiscountService.deleteWxDiscount(id);
    }

}
