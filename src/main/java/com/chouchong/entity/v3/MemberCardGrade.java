package com.chouchong.entity.v3;

import java.util.Date;

public class MemberCardGrade {
    private Integer id;

    private Integer membershipCardId;

    private Integer gradeId;

    private Date updated;

    private Date created;

    public MemberCardGrade(Integer id, Integer membershipCardId, Integer gradeId, Date updated, Date created) {
        this.id = id;
        this.membershipCardId = membershipCardId;
        this.gradeId = gradeId;
        this.updated = updated;
        this.created = created;
    }

    public MemberCardGrade() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMembershipCardId() {
        return membershipCardId;
    }

    public void setMembershipCardId(Integer membershipCardId) {
        this.membershipCardId = membershipCardId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
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
