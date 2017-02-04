/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_PermissionRole")
public class PermissionRole {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    private String permissionRoleId;
    
    @ManyToMany(mappedBy = "roles")
    Set<LoginUserGroup> userGroups;
    
    @Column(length=32)
    String roleName;
    
    @Column(length=32)
    String roleDescription;
    
    @OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="permissionRoleId",referencedColumnName="permissionRoleId")
    Set<Privilege> priviledges;

    public String getPermissionRoleId() {
        return permissionRoleId;
    }

    public void setPermissionRoleId(String permissionRoleId) {
        this.permissionRoleId = permissionRoleId;
    }

    public Set<LoginUserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<LoginUserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Set<Privilege> getPriviledges() {
        return priviledges;
    }

    public void setPriviledges(Set<Privilege> priviledges) {
        this.priviledges = priviledges;
    }
    
    
}
