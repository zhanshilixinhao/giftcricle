package com.chouchong.service.v3.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author linqin
 * @date 2019/12/23
 */

public class UserCardVo2 extends UserCardVo1 {

    private Integer userId;

    private Integer cardId;

    private String phone;

    private String nickname;

    private String avatar;

    private String title;

    private Long cardNo;

    private BigDecimal balance;

    private Byte type;

    private BigDecimal capital;

    private BigDecimal send;

    private Date created;

    private List<EventCardVo> eventCards;

    public List<EventCardVo> getEventCards() {
        return eventCards;
    }

    public void setEventCards(List<EventCardVo> eventCards) {
        this.eventCards = eventCards;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getSend() {
        return send;
    }

    public void setSend(BigDecimal send) {
        this.send = send;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }



}
