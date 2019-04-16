package com.chouchong.service.gift.article;

import java.math.BigDecimal;

/**
 * @author linqin
 * @date 2019/1/16 14:45
 */

public class ArticleVo {

    private Integer id; //文章商品id

    private Integer articleId; //文章id

    private Integer itemId ; //商品id

    private String title; //文章标题

    private String name; //商品名称

    private String cover;//商品封面

    private BigDecimal price;//商品价格

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
