package com.chouchong.service.gift.item.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yy
 * @date 2018/6/27
 **/
public class ItemVo {
    private Integer id;

    private String title;

    private BigDecimal price;

    private Integer sales;

    private Integer stock;

    private String description;

    private Byte reGender;

    private Byte reMaxAge;

    private Byte reAgeMin;

    private Integer storeId;

    private Integer categoryId;

    private Integer pid;

    private String categoryName;

    private String cover;

    private String pictures;

    private Byte choiceness;

    private Byte hot;

    private Byte status;

    private Byte isAudit;

    private Integer brandId;

    private String detail;

    private Date created;

    private Date updated;

    private Integer isGroup;

    private Integer adminId;

    private String username;
    private String wxCover;


    public Integer getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Integer isGroup) {
        this.isGroup = isGroup;
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
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getReGender() {
        return reGender;
    }

    public void setReGender(Byte reGender) {
        this.reGender = reGender;
    }

    public Byte getReMaxAge() {
        return reMaxAge;
    }

    public void setReMaxAge(Byte reMaxAge) {
        this.reMaxAge = reMaxAge;
    }

    public Byte getReAgeMin() {
        return reAgeMin;
    }

    public void setReAgeMin(Byte reAgeMin) {
        this.reAgeMin = reAgeMin;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public Byte getChoiceness() {
        return choiceness;
    }

    public void setChoiceness(Byte choiceness) {
        this.choiceness = choiceness;
    }

    public Byte getHot() {
        return hot;
    }

    public void setHot(Byte hot) {
        this.hot = hot;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Byte isAudit) {
        this.isAudit = isAudit;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getWxCover() {
        return wxCover;
    }

    public void setWxCover(String wxCover) {
        this.wxCover = wxCover;
    }
}
