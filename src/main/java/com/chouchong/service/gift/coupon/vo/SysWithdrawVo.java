package com.chouchong.service.gift.coupon.vo;

import com.chouchong.entity.webUser.SysAdminWithdraw;

/**
 * @author linqin
 * @date 2019/4/9
 */

public class SysWithdrawVo extends SysAdminWithdraw {

    private String username;

    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
