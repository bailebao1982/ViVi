/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.entity;

import java.util.Date;
import java.util.LinkedHashSet;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_ProductPackage")
public class ProductPackage {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    @Column(length=32)
    String productPackageId;
    
    
     @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "productPackage_productSet", 
	   joinColumns = {@JoinColumn(name = "productPackageId")}, 
	   inverseJoinColumns = {@JoinColumn(name = "productSetId")})
    Set<ProductSet> productSets;
    
    @Column
    int unitPrice;
    
    @Column(length=32)
    String description; 
    
    @Column(length=32)
    String packageName;
    
    @Column(name = "salesDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date effectiveStartDate;
    
    
    @Column(name = "salesDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date effectiveEndDate;
    
    //int unitBounce;
    
    //int bonusePoint;

    public String getProductId() {
        return productPackageId;
    }

    public void setProductId(String productPackageId) {
        this.productPackageId = productPackageId;
    }

    public Set<ProductSet> getProductSets() {
        return productSets;
    }

    public void setProductSets(Set<ProductSet> productSets) {
        this.productSets = productSets;
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
    
    
    
}
