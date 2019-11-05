package com.chouchong.entity.v3;

import java.util.Date;

public class MemberCard {
    private Integer id;

    private Integer membershipCardId;

    private Integer membersEventId;

    private Date updated;

    private Date created;

    public MemberCard(Integer id, Integer membershipCardId, Integer membersEventId, Date updated, Date created) {
        this.id = id;
        this.membershipCardId = membershipCardId;
        this.membersEventId = membersEventId;
        this.updated = updated;
        this.created = created;
    }

    public MemberCard() {
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

    public Integer getMembersEventId() {
        return membersEventId;
    }

    public void setMembersEventId(Integer membersEventId) {
        this.membersEventId = membersEventId;
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
