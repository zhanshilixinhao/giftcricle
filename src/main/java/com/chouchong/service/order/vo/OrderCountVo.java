package com.chouchong.service.order.vo;

/**
 * @author linqin
 * @date 2019/5/16
 */

public class OrderCountVo {

    private Integer chargeCount; //充值订单数量

    private Integer itemCount;//购买商品订单数量

    private Integer viItemCount;//购买虚拟商品订单数量

    private Integer reCount;//提货订单数量

    private Integer commentCount;//订单评价数量

    public Integer getChargeCount() {
        return chargeCount;
    }

    public void setChargeCount(Integer chargeCount) {
        this.chargeCount = chargeCount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getViItemCount() {
        return viItemCount;
    }

    public void setViItemCount(Integer viItemCount) {
        this.viItemCount = viItemCount;
    }

    public Integer getReCount() {
        return reCount;
    }

    public void setReCount(Integer reCount) {
        this.reCount = reCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
