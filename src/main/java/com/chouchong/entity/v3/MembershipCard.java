package com.chouchong.entity.v3;

import java.util.Date;

public class MembershipCard {
    private Integer id;

    private Long cardNo;

    private String title;

    private String colour;

    private String logo;

    private String storeIds;

    private Integer adminId;

    private Byte type;

    private Byte status;

    private Date updated;

    private Date created;

    private String summary;

    public MembershipCard(Integer id, Long cardNo, String title, String colour, String logo, String storeIds, Integer adminId, Byte type, Byte status, Date updated, Date created) {
        this.id = id;
        this.cardNo = cardNo;
        this.title = title;
        this.colour = colour;
        this.logo = logo;
        this.storeIds = storeIds;
        this.adminId = adminId;
        this.type = type;
        this.status = status;
        this.updated = updated;
        this.created = created;
    }

    public MembershipCard(Integer id, Long cardNo, String title, String colour, String logo, String storeIds, Integer adminId, Byte type, Byte status, Date updated, Date created, String summary) {
        this.id = id;
        this.cardNo = cardNo;
        this.title = title;
        this.colour = colour;
        this.logo = logo;
        this.storeIds = storeIds;
        this.adminId = adminId;
        this.type = type;
        this.status = status;
        this.updated = updated;
        this.created = created;
        this.summary = summary;
    }

    public MembershipCard() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.title = title == null ? null : title.trim();
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour == null ? null : colour.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String storeIds) {
        this.storeIds = storeIds == null ? null : storeIds.trim();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}
