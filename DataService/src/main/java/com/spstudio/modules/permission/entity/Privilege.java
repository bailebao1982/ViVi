/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_Privilege")
public class Privilege {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    private String privilegeId;
    
    @Column(length=32)
    String privilegeName;
    
    @Column(length=32)
    String privilegeDescription;
    
    @Column(length=32)
    String funcationName;

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeDescription() {
        return privilegeDescription;
    }

    public void setPrivilegeDescription(String privilegeDescription) {
        this.privilegeDescription = privilegeDescription;
    }

    public String getFuncationName() {
        return funcationName;
    }

    public void setFuncationName(String funcationName) {
        this.funcationName = funcationName;
    }
    
    
}
