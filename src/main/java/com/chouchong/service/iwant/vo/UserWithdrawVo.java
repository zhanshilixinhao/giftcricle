package com.chouchong.service.iwant.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yy
 * @date 2018/7/24
 **/
public class UserWithdrawVo {
    private Integer id;
    // 申请者APP昵称
    private String nickname;
    // 申请者电话
    private String phone;
    // 申请状态
    private Integer status;
    // 持卡人姓名
    private String cardHolder;
    // 银行卡号
    private String cardNo;
    // 银行名称
    private String bankName;
    // 开户行
    private String depositBank;
    // 提现金额
    private BigDecimal amount;
    // 申请时间
    private Date created;
    // 修改时间
    private Date updated;
    // 提现说明
    private String describes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
