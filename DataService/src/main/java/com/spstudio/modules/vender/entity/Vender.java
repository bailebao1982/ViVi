/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.vender.entity;

import com.spstudio.modules.sp.entity.ServiceProvider;
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
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_Vender")
public class Vender {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    String venderId;
    
    @Column
    String venderName;
    
    @Column
    String businessNO;
    
    @Column
    String address;
    
    @Column
    String serviceType;
    
    @Column
    String contactNo;
    
    //用 1|1|0|1 来表示各个部分的认证情况
    @Column
    String confirmed;
    
    @Column(columnDefinition = "int default 0")
    int deleteFlag;

   @ManyToMany(mappedBy = "venders")
    private Set<ServiceProvider> sps;
    
    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getBusinessNO() {
        return businessNO;
    }

    public void setBusinessNO(String businessNO) {
        this.businessNO = businessNO;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Set<ServiceProvider> getSps() {
        return sps;
    }

    public void setSps(Set<ServiceProvider> sps) {
        this.sps = sps;
    }
    
    
    
    
}