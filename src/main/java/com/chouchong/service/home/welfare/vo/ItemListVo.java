package com.chouchong.service.home.welfare.vo;

import javax.xml.crypto.Data;
import java.math.BigDecimal;

/**
 * @author linqin
 * @date 2019/2/24 15:09
 */

public class ItemListVo {

    private Integer id; //物品id

    private String title; // 商品标题

    private String cover; //商品图片

    private BigDecimal price;// 价格

    private Data Created; //创建时间

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Data getCreated() {
        return Created;
    }

    public void setCreated(Data created) {
        Created = created;
    }
}
