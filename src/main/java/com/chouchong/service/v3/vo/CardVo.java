package com.chouchong.service.v3.vo;

import java.util.Date;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/6
 */

public class CardVo {

    private Integer id;

    private Long cardNo;

    private String title;

    private String colour;

    private String logo;

    private String storeIds;

    private Integer adminId;
    
    private Date created;

    private String summary;

    private List<StoreVo> storeVos;

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
        this.title = title;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String storeIds) {
        this.storeIds = storeIds;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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
        this.summary = summary;
    }

    public List<StoreVo> getStoreVos() {
        return storeVos;
    }

    public void setStoreVos(List<StoreVo> storeVos) {
        this.storeVos = storeVos;
    }
}
