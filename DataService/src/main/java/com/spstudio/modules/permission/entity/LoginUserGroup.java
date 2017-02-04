/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.entity;


import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_LoginUserGroup")
public class LoginUserGroup {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    private String loginUserGroupId;
    
    @Column(length=32, nullable=false)
    String groupName;
    
    @Column(length=32)
    String groupDescription;
    
    @Column(name = "ceationDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date ceationDate;
    
    @OneToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name="loginUserGroupId" ,referencedColumnName="loginUserGroupId")
    Set<LoginUser> users;
    
    
    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "usergroup_permissionrole", 
	   joinColumns = {@JoinColumn(name = "loginUserGroupId")}, 
	   inverseJoinColumns = {@JoinColumn(name = "permissionRoleId")})
    Set<PermissionRole> roles;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Date getCeationDate() {
        return ceationDate;
    }

    public void setCeationDate(Date ceationDate) {
        this.ceationDate = ceationDate;
    }

    public Set<PermissionRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<PermissionRole> roles) {
        this.roles = roles;
    }

    public String getLoginUserGroupId() {
        return loginUserGroupId;
    }

    public void setLoginUserGroupId(String loginUserGroupId) {
        this.loginUserGroupId = loginUserGroupId;
    }

    public Set<LoginUser> getUsers() {
        return users;
    }

    public void setUsers(Set<LoginUser> users) {
        this.users = users;
    }
    
    
    
}
