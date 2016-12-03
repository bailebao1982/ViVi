/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.member.entity;

import java.sql.Date;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="T_Member",
       uniqueConstraints={@UniqueConstraint(columnNames={"mobile"})}
)
public class Member {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    @Column(length=32)
    private String memberId;

    @Column(length=32, nullable = false)
    String memberName;

    @Column(length=32)
    String weixinId;

    @Column(length=32)
    String sex;
    
    @Column(length=32)
    String address;
    
    @Column(length=32)
    String hobby;
    
    @Column(length=32, nullable = false)
    String mobile;

    @Column
    String email;
    
    @Column(length=32)
    String job;
    
    @Column(length=32)
    String remark;
    
    @Column
    Date birthDay;
    
    @Column(updatable = false)
    Date creationDate;
    
    @Column
    Date lastUpdateDate;
    
    @Column(length=32)
    String creator;
          
    @Column(length=32)
    String lastUpdateBy;
    
    @Column(columnDefinition = "int default 0")
    int deleteFlag;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="memberTypeId",referencedColumnName="memberTypeId")
    MemberType type;
    
    public Member(){
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public MemberType getMemberType() {
        return type;
    }

    public void setMemberType(MemberType type) {
        this.type = type;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Address) {
        this.address = Address;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    
    
}
