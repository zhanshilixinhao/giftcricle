package com.chouchong.entity.gift.item;

import java.math.BigDecimal;
import java.util.Date;

public class Item {
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

    private String cover;

    private String pictures;

    private Byte choiceness;

    private Byte hot;

    private Byte status;

    private Byte isAudit;

    private Integer brandId;

    private Date created;

    private Date updated;

    private Integer adminId;

    private String wxCover;

    private Integer sort;


    public Item(Integer id, String title, BigDecimal price, Integer sales, Integer stock, String description, Byte reGender, Byte reMaxAge, Byte reAgeMin, Integer storeId, Integer categoryId, String cover, String pictures, Byte choiceness, Byte hot, Byte status, Byte isAudit, Integer brandId, Date created, Date updated) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
        this.description = description;
        this.reGender = reGender;
        this.reMaxAge = reMaxAge;
        this.reAgeMin = reAgeMin;
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.cover = cover;
        this.pictures = pictures;
        this.choiceness = choiceness;
        this.hot = hot;
        this.status = status;
        this.isAudit = isAudit;
        this.brandId = brandId;
        this.created = created;
        this.updated = updated;
    }

    public Item() {
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
        this.description = description == null ? null : description.trim();
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
        this.cover = cover == null ? null : cover.trim();
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures == null ? null : pictures.trim();
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

    public void setStatus(byte status) {
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

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getWxCover() {
        return wxCover;
    }

    public void setWxCover(String wxCover) {
        this.wxCover = wxCover;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
