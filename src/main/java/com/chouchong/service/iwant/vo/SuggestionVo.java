package com.chouchong.service.iwant.vo;

import java.util.Date;

/**
 * @author yy
 * @date 2018/7/25
 **/
public class SuggestionVo {
    // 意见反馈id
    private Integer id;
    // 反馈类型
    private Integer type;
    // 联系方式
    private String contactWay;
    // 反馈内容
    private String feedback;
    // 反馈用户昵称
    private String nickname;
    // 反馈时间
    private Date created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
