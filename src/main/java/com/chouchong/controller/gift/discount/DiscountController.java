package com.chouchong.controller.gift.discount;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.discount.DiscountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2018/7/24
 */
@RestController
@RequestMapping("manage/item/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    /**
     * 背包物品折现记录列表
     *
     * @param nickname 用户昵称
     * @param title    商品标题
     * @param type     1-物品，2-虚拟物品
     * @param status   折现状态，1-折现完成，2-折现失败,-1删除记录
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    @PostMapping("list")
    public Response discountList(PageQuery pageQuery, String nickname, String title, Byte type, Byte status) {
        if (StringUtils.isBlank(nickname)) {
            nickname = null;
        }
        if (StringUtils.isBlank(title)) {
            title = null;
        }
        return discountService.discountList(pageQuery, nickname, title, type, status);
    }

    /**
     * 删除折现记录
     *
     * @param id 折现记录Id
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    @PostMapping("delete")
    public Response delDiscount(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return discountService.delDiscount(id);
    }

    /**
     * 确认折现
     *
     * @param id 折现记录Id
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    @PostMapping("confirm")
    public Response confirmDiscount(Integer id){
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return discountService.confirmDiscount(id);
    }


    /**
     * 拒绝折现
     *
     * @param id 折现记录Id
     * @param explain 折现说明
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    @PostMapping("refuse")
    public Response refuseDiscount(Integer id,String explain){
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isBlank(explain)){
            return ResponseFactory.errMissingParameter();
        }
        return discountService.refuseDiscount(id,explain);
    }

}
