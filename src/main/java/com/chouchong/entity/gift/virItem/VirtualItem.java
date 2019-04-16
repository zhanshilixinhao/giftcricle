package com.chouchong.entity.gift.virItem;

import java.math.BigDecimal;
import java.util.Date;

public class VirtualItem {
    private Integer id;

    private Integer brandId;

    private Integer cateId;

    private String name;

    private BigDecimal price;

    private String cover;

    private String description;

    private Integer sales;

    private Byte status;

    private Integer sort;

    private Date created;

    private Date updated;

    public VirtualItem(Integer id, Integer brandId, Integer cateId, String name, BigDecimal price, String cover, String description, Integer sales, Byte status, Integer sort, Date created, Date updated) {
        this.id = id;
        this.brandId = brandId;
        this.cateId = cateId;
        this.name = name;
        this.price = price;
        this.cover = cover;
        this.description = description;
        this.sales = sales;
        this.status = status;
        this.sort = sort;
        this.created = created;
        this.updated = updated;
    }

    public VirtualItem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
}