package com.chouchong.service.gift.coupon.vo;

import com.chouchong.entity.webUser.SysAdminWallet;

/**
 * @author linqin
 * @date 2019/4/8 21:41
 */

public class SysWalletVo extends SysAdminWallet {

    private String username;

    private String avatar;

    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
