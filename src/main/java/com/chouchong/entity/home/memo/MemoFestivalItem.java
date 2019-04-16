package com.chouchong.entity.home.memo;

import java.util.Date;

public class MemoFestivalItem {
    private Integer id;

    private Integer memoFestivalId;

    private Integer itemId;

    private Date created;

    private Date updated;

    public MemoFestivalItem(Integer id, Integer memoFestivalId, Integer itemId, Date created, Date updated) {
        this.id = id;
        this.memoFestivalId = memoFestivalId;
        this.itemId = itemId;
        this.created = created;
        this.updated = updated;
    }

    public MemoFestivalItem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemoFestivalId() {
        return memoFestivalId;
    }

    public void setMemoFestivalId(Integer memoFestivalId) {
        this.memoFestivalId = memoFestivalId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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
