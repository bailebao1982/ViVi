/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.entity;

import java.util.Date;
import java.util.Set;
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
@Table(name="T_ProductPackage",
       uniqueConstraints={@UniqueConstraint(columnNames={"serialNo"})}
)
public class ProductPackage {
    @Id
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    @Column(length=32)
    String productPackageId;

    @OneToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    @JoinColumn(name="productPackageId" ,referencedColumnName="productPackageId")
    Set<PackageProductMapping> productMappingSet;

    @Column
    String serialNo;

    @Column
    int unitPrice;

    @Column(length=32)
    String description;
    
    @Column(length=32)
    String packageName;
    
    @Column(name = "effectiveStartDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date effectiveStartDate;
    
    @Column(name = "effectiveEndDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date effectiveEndDate;
    
    //int unitBounce;
    
    //int bonusePoint;

    @Column(columnDefinition = "int default 0")
    int deleteFlag;


    @Column(updatable = false)
    java.sql.Date creationDate;

    @Column
    java.sql.Date lastUpdateDate;

    @Column(length=32, updatable = false)
    String creator;

    @Column(length=32)
    String lastUpdateBy;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getProductPackageId() {
        return productPackageId;
    }

    public void setProductPackageId(String productPackageId) {
        this.productPackageId = productPackageId;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Set<PackageProductMapping> getProductMappingSet() {
        return productMappingSet;
    }

    public void setProductMappingSet(Set<PackageProductMapping> productMappingSet) {
        this.productMappingSet = productMappingSet;
    }

    public java.sql.Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.sql.Date creationDate) {
        this.creationDate = creationDate;
    }

    public java.sql.Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(java.sql.Date lastUpdateDate) {
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
