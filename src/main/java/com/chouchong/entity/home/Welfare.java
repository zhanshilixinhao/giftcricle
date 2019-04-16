package com.chouchong.entity.home;


import java.math.BigDecimal;
import java.util.Date;

public class Welfare {
    private Integer id;

    private String title;

    private String cover;

    private Byte type;

    private Integer targetId;

    private Integer quantity;

    private Integer adminId;

    private Date targetDate;

    private Date created;

    private Date updated;

    private Date startTime;

    private Date endTime;

    private Integer count;

    private BigDecimal price; // 商品价格

    public Welfare(Integer id, String title, String cover, Byte type, Integer targetId, Integer quantity, Integer adminId, Date targetDate, Date created, Date updated, Date startTime, Date endTime, Integer count) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.type = type;
        this.targetId = targetId;
        this.quantity = quantity;
        this.adminId = adminId;
        this.targetDate = targetDate;
        this.created = created;
        this.updated = updated;
        this.startTime = startTime;
        this.endTime = endTime;
        this.count = count;
    }

    public Welfare() {
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
