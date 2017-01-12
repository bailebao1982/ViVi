package com.spstudio.modules.sp.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Soul on 2017/1/12.
 */

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="T_ServiceProviderType")
public class ServiceProviderType {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    @Column(length=32)
    String serviceProviderTypeId;

    @Column(length=32, nullable=false)
    String serviceProviderTypeName;

    @Column(length=32)
    String description;

    @Column
    Date creationDate;

    @Column
    Date lastUpdateDate;

    @Column(length=32)
    String creator;

    @Column(length=32)
    String lastUpdateBy;

    public String getServiceProviderTypeId() {
        return serviceProviderTypeId;
    }

    public void setServiceProviderTypeId(String serviceProviderTypeId) {
        this.serviceProviderTypeId = serviceProviderTypeId;
    }

    public String getServiceProviderTypeName() {
        return serviceProviderTypeName;
    }

    public void setServiceProviderTypeName(String serviceProviderTypeName) {
        this.serviceProviderTypeName = serviceProviderTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
