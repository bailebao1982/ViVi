/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sp.entity;

import com.spstudio.modules.vender.entity.Vender;

import java.sql.Date;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 服务提供人，简单的理解为employee。
 * 实质是自由职业人
 * @author wewezhu
 */

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="T_ServiceProvider")
public class ServiceProvider {
    @Id
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    String serviceProviderId;

    // 工作编号
    @Column
    private String spWorkNo;

    @Column
    private String spName;

    @Column
    private String spSex;

    @Column
    private Date spBirthDay;

    @Column
    private String spTelphone;

    @Column
    private String spAddress;

    @Column
    private String spNote;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="serviceProviderTypeId",referencedColumnName="serviceProviderTypeId")
    private ServiceProviderType spType;

    // 从属单位
    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "sp_vender",
	   joinColumns = {@JoinColumn(name = "serviceProviderId")}, 
	   inverseJoinColumns = {@JoinColumn(name = "venderId")})
    private Set<Vender> venders;
    
    @Column
    private String level;
    
    @Column
    private boolean verified;
    
    @Column(columnDefinition = "int default 0")
    int deleteFlag;

    @Column
    Date creationDate;

    @Column
    Date lastUpdateDate;

    @Column(length=32)
    String creator;

    @Column(length=32)
    String lastUpdateBy;

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

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getSpWorkNo() {
        return spWorkNo;
    }

    public void setSpWorkNo(String spWorkNo) {
        this.spWorkNo = spWorkNo;
    }

    public Set<Vender> getVenders() {
        return venders;
    }

    public void setVenders(Set<Vender> venders) {
        this.venders = venders;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpSex() {
        return spSex;
    }

    public void setSpSex(String spSex) {
        this.spSex = spSex;
    }

    public Date getSpBirthDay() {
        return spBirthDay;
    }

    public void setSpBirthDay(Date spBirthDay) {
        this.spBirthDay = spBirthDay;
    }

    public String getSpTelphone() {
        return spTelphone;
    }

    public void setSpTelphone(String spTelphone) {
        this.spTelphone = spTelphone;
    }

    public String getSpAddress() {
        return spAddress;
    }

    public void setSpAddress(String spAddress) {
        this.spAddress = spAddress;
    }

    public String getSpNote() {
        return spNote;
    }

    public void setSpNote(String spNote) {
        this.spNote = spNote;
    }

    public ServiceProviderType getSpType() {
        return spType;
    }

    public void setSpType(ServiceProviderType spType) {
        this.spType = spType;
    }
}
