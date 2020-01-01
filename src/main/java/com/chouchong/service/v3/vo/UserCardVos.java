package com.chouchong.service.v3.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author linqin
 * @date 2020/1/1 20:10
 */

public class UserCardVos {

    private BigDecimal totalMoney;

    private List<UserCardVo> userCardVos;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<UserCardVo> getUserCardVos() {
        return userCardVos;
    }

    public void setUserCardVos(List<UserCardVo> userCardVos) {
        this.userCardVos = userCardVos;
    }
}
