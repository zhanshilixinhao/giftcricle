package com.chouchong.entity.home.message;

import java.util.Date;

public class MessagePush {
    private Integer id;

    private Integer appMessageId;

    private String title;

    private String content;

    private Byte type;

    private String typeValue;

    private Byte ring;

    private Byte vibrate;

    private Byte clearable;

    private Byte status;

    private Date created;

    private Integer createAdminId;

    private Date updated;

    private Integer updateAdminId;

    public MessagePush(Integer id, Integer appMessageId, String title, String content, Byte type, String typeValue, Byte ring, Byte vibrate, Byte clearable, Byte status, Date created, Integer createAdminId, Date updated, Integer updateAdminId) {
        this.id = id;
        this.appMessageId = appMessageId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.typeValue = typeValue;
        this.ring = ring;
        this.vibrate = vibrate;
        this.clearable = clearable;
        this.status = status;
        this.created = created;
        this.createAdminId = createAdminId;
        this.updated = updated;
        this.updateAdminId = updateAdminId;
    }

    public MessagePush() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppMessageId() {
        return appMessageId;
    }

    public void setAppMessageId(Integer appMessageId) {
        this.appMessageId = appMessageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue == null ? null : typeValue.trim();
    }

    public Byte getRing() {
        return ring;
    }

    public void setRing(Byte ring) {
        this.ring = ring;
    }

    public Byte getVibrate() {
        return vibrate;
    }

    public void setVibrate(Byte vibrate) {
        this.vibrate = vibrate;
    }

    public Byte getClearable() {
        return clearable;
    }

    public void setClearable(Byte clearable) {
        this.clearable = clearable;
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

    public Integer getCreateAdminId() {
        return createAdminId;
    }

    public void setCreateAdminId(Integer createAdminId) {
        this.createAdminId = createAdminId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getUpdateAdminId() {
        return updateAdminId;
    }

    public void setUpdateAdminId(Integer updateAdminId) {
        this.updateAdminId = updateAdminId;
    }
}