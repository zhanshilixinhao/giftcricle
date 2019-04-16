package com.chouchong.service.order.vo;

/**
 * @author linqin
 * @date 2018/12/18 11:22
 */

public class CommentDetail {

    private Integer id;

    private Integer star;

    private String content;

    private String pictures;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }
}
