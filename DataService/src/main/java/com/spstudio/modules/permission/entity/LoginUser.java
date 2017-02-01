/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.entity;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_LoginUser")
public class LoginUser {

    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    private String loginUserId;
    
    @Column(length=32, nullable=false)
    String loginUser;
    
    @Column(length=32, nullable=false)
    String loginPassword;
    
    @Column
    String memberId;
    
    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="loginUserGroupId",referencedColumnName="loginUserGroupId")
    LoginUserGroup group;
    
    @Column(name = "creationTime", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date creationTime;
    
    @Column(name = "lastLoginTime", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date lastLoginTime;
    
    @Column
    int loginCount;

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public LoginUserGroup getGroup() {
        return group;
    }

    public void setGroup(LoginUserGroup group) {
        this.group = group;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }
    
    
    
}
