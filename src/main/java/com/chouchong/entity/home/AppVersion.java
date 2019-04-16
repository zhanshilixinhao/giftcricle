package com.chouchong.entity.home;

import java.util.Date;

public class AppVersion {
    private Integer id;

    private String versionCode;

    private String versionName;

    private String title;

    private Byte type;

    private String apkUrl;

    private String upgradePoint;

    private Byte status;

    private Date updated;

    private Date created;

    public AppVersion(Integer id, String versionCode, String versionName, String title, Byte type, String apkUrl, String upgradePoint, Byte status, Date updated, Date created) {
        this.id = id;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.title = title;
        this.type = type;
        this.apkUrl = apkUrl;
        this.upgradePoint = upgradePoint;
        this.status = status;
        this.updated = updated;
        this.created = created;
    }

    public AppVersion() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl == null ? null : apkUrl.trim();
    }

    public String getUpgradePoint() {
        return upgradePoint;
    }

    public void setUpgradePoint(String upgradePoint) {
        this.upgradePoint = upgradePoint == null ? null : upgradePoint.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
