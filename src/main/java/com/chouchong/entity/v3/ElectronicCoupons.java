package com.chouchong.entity.v3;

import com.chouchong.service.v3.vo.StoreVo;

import java.util.Date;
import java.util.List;

public class ElectronicCoupons {
    private Integer id;

    private String title;

    private String summary;

    private String logo;

    private String storeIds;

    private Integer adminId;

    private Byte type;

    private Byte status;

    private Date date;
    private Date startTime;

    private Date updated;

    private Date created;

    /**
     * 适用门店
     */
    private List<StoreVo> storeVos;

    public ElectronicCoupons(Integer id, String title, String summary, String logo, String storeIds, Integer adminId, Byte type, Byte status, Date date, Date updated, Date created) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.logo = logo;
        this.storeIds = storeIds;
        this.adminId = adminId;
        this.type = type;
        this.status = status;
        this.date = date;
        this.updated = updated;
        this.created = created;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public ElectronicCoupons() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public List<StoreVo> getStoreVos() {
        return storeVos;
    }

    public void setStoreVos(List<StoreVo> storeVos) {
        this.storeVos = storeVos;
    }
}
