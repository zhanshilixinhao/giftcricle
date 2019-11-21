package com.chouchong.service.v3.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author linqin
 * @date 2019/11/20
 */

public class ChargeReVo {

    private Integer id;

    private Integer membershipCardId;

    private String title;

    private Long cardNo;

    private Integer userId;

    private String nickname;

    private String phone;

    private Integer memberEventId;

    private String eventName;

    private BigDecimal rechargeMoney;

    private BigDecimal sendMoney;

    private Byte type;

    private Integer storeId;

    private String storeName;

    private String explain;

    private Date created;

    private BigDecimal appTotalCh;
    private BigDecimal appTotalSen;
    private BigDecimal storeTotalCh;
    private BigDecimal storeTotalSen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMembershipCardId() {
        return membershipCardId;
    }

    public void setMembershipCardId(Integer membershipCardId) {
        this.membershipCardId = membershipCardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getMemberEventId() {
        return memberEventId;
    }

    public void setMemberEventId(Integer memberEventId) {
        this.memberEventId = memberEventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public BigDecimal getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(BigDecimal sendMoney) {
        this.sendMoney = sendMoney;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }

    public BigDecimal getAppTotalCh() {
        return appTotalCh;
    }

    public void setAppTotalCh(BigDecimal appTotalCh) {
        this.appTotalCh = appTotalCh;
    }

    public BigDecimal getAppTotalSen() {
        return appTotalSen;
    }

    public void setAppTotalSen(BigDecimal appTotalSen) {
        this.appTotalSen = appTotalSen;
    }

    public BigDecimal getStoreTotalCh() {
        return storeTotalCh;
    }

    public void setStoreTotalCh(BigDecimal storeTotalCh) {
        this.storeTotalCh = storeTotalCh;
    }

    public BigDecimal getStoreTotalSen() {
        return storeTotalSen;
    }

    public void setStoreTotalSen(BigDecimal storeTotalSen) {
        this.storeTotalSen = storeTotalSen;
    }
}
