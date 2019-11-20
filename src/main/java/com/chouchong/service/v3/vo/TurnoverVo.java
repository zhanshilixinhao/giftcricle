package com.chouchong.service.v3.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author linqin
 * @date 2019/11/18
 */

public class TurnoverVo {

    private Integer id;

    private Integer userId;

    private String nickname;

    private String phone;

    private Integer storeId;

    private String storeName;

    private BigDecimal blagMoney;

    private BigDecimal turnoverMoney;

    private Integer membershipCardId;

    private Long cardNo;

    private String title;

    private Integer eventId;

    private String eventName;

    private Date created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getBlagMoney() {
        return blagMoney;
    }

    public void setBlagMoney(BigDecimal blagMoney) {
        this.blagMoney = blagMoney;
    }

    public BigDecimal getTurnoverMoney() {
        return turnoverMoney;
    }

    public void setTurnoverMoney(BigDecimal turnoverMoney) {
        this.turnoverMoney = turnoverMoney;
    }

    public Integer getMembershipCardId() {
        return membershipCardId;
    }

    public void setMembershipCardId(Integer membershipCardId) {
        this.membershipCardId = membershipCardId;
    }

    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
