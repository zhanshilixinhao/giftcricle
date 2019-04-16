package com.chouchong.entity.home;

import java.util.Date;

public class Festival {
    private Integer id;

    private String festival;

    private Date targetDate;

    private Date created;

    public Festival(Integer id, String festival, Date targetDate, Date created) {
        this.id = id;
        this.festival = festival;
        this.targetDate = targetDate;
        this.created = created;
    }

    public Festival() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFestival() {
        return festival;
    }

    public void setFestival(String festival) {
        this.festival = festival == null ? null : festival.trim();
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
