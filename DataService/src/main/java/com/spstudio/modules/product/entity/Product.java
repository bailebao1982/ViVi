/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.entity;

import java.util.LinkedHashSet;
import java.util.Set;
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
@Table(name="T_Product")
public class Product {
   
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    @Column(length=32)
    String productId;
    
    @Column
    int unitPrice;
    
    @Column
    int unitBounce;
    
    @Column(length=32)
    String description;
    
    @Column(length=32)
    String productName;
    
    @Column
    int bonusePoint;
    
    //Set<ProductPackage> productPackage = new LinkedHashSet<ProductPackage>();

    boolean available;
    
    @Column(columnDefinition = "int default 0")
    int deleteFlag;
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitBounce() {
        return unitBounce;
    }

    public void setUnitBounce(int unitBounce) {
        this.unitBounce = unitBounce;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getBonusePoint() {
        return bonusePoint;
    }

    public void setBonusePoint(int bonusePoint) {
        this.bonusePoint = bonusePoint;
    }

    //public Set<ProductPackage> getProductPackage() {
    //    return productPackage;
    //}

    //public void setProductPackage(Set<ProductPackage> productPackage) {
    //    this.productPackage = productPackage;
    //}

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    
}
