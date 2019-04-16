package com.chouchong.service.gift.item.vo;

import java.util.Date;

/**
 * @author linqin
 * @date 2019/1/21 15:52
 */

public class FeatureVo {

    private Integer id;

    private String name;

    private Integer sort;

    private String values;

    private Byte isSelect;

    private Byte status;

    private Date created;

    private Date updated;

    private Integer adminId;

    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public Byte getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Byte isSelect) {
        this.isSelect = isSelect;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
}
