package com.spstudio.modules.member.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Soul on 2017/1/17.
 */
@Entity
@Table(name="T_MemberBonusPoint")
public class MemberBonusPoint {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    @Column(length=32)
    private String bonusPointId;

    @OneToOne(cascade = { CascadeType.DETACH })
    @JoinColumn(name = "memberId")
    private Member member;

    // 积分点
    @Column(columnDefinition = "int default 0")
    private int bonusPoint;

    @Column(updatable = false)
    private Date creationDate;

    @Column
    private Date lastUpdateDate;

    public String getBonusPointId() {
        return bonusPointId;
    }

    public void setBonusPointId(String bonusPointId) {
        this.bonusPointId = bonusPointId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getBonusPoint() {
        return bonusPoint;
    }

    public void setBonusPoint(int bonusPoint) {
        this.bonusPoint = bonusPoint;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
