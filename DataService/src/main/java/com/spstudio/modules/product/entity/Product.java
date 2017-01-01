/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.entity;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

/**
 *
 * @author wewezhu
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Table(
        name="T_Product",
        uniqueConstraints={@UniqueConstraint(columnNames={"serialno"})}
)
public class Product {
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    @Column(length=32)
    private String productId;

    // 产品编号
    @Column(nullable = false)
    private String serialno;

    // 单价
    @Column
    private float unitPrice;

    // 单格
    @Column
    private int unitBounce;

    // 描述
    @Column(length=32)
    private String description;

    // 名称
    @Column(length=32)
    private String productName;

    // 计量单位(Unit of Measurement)
    @Column(length=32)
    private String uom;

    // ??
    @Column
    private int bonusePoint;

    // ??
    boolean available;

    // 规格
    @Column
    private String specification;

    @Column(columnDefinition = "int default 0")
    int deleteFlag;

    // 类别
    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="productTypeId",referencedColumnName="productTypeId")
    ProductType type;

    @Column(updatable = false)
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public void setBonusePoint(int bonusePoint) {
        this.bonusePoint = bonusePoint;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }


    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
