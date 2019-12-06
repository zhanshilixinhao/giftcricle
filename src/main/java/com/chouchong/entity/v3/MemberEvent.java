package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class MemberEvent {
    private Integer id;

    private String title;

    private String summary;

    private BigDecimal rechargeMoney;

    private BigDecimal sendMoney;

    private Integer targetId;

    private Integer adminId;

    private Byte type;

    private Byte status;

    private Date updated;

    private Date created;

    private Float scale;

    public MemberEvent(Integer id, String title, String summary, BigDecimal rechargeMoney, BigDecimal sendMoney, Integer targetId, Integer adminId, Byte type, Byte status, Date updated, Date created, Float scale) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.rechargeMoney = rechargeMoney;
        this.sendMoney = sendMoney;
        this.targetId = targetId;
        this.adminId = adminId;
        this.type = type;
        this.status = status;
        this.updated = updated;
        this.created = created;
        this.scale = scale;
    }

    public MemberEvent() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
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

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
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

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }
}
