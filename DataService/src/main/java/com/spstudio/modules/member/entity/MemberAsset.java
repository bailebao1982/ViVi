/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.entity;


import com.spstudio.modules.product.entity.Product;

import javax.persistence.*;

import com.spstudio.modules.product.entity.ProductPackage;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_MemberAsset",
        uniqueConstraints={@UniqueConstraint(columnNames={"memberId", "assetType"})})
public class MemberAsset {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    @Column(length=32)
    private String memberAssetId;
    
    @ManyToOne(cascade = { CascadeType.DETACH })
    @JoinColumn(name = "memberId") 
    private Member member;

    /**
     * assetType:
     * 0 ---- product
     * 1 ---- package
     * 2 ---- deposit
     */
    @Column
    private int assetType;

    // 产品
    @ManyToOne(cascade = { CascadeType.DETACH})
    @JoinColumn(name = "productId")
    private Product product;

    // 套餐
    @ManyToOne(cascade = { CascadeType.DETACH})
    @JoinColumn(name = "productPackageId")
    private ProductPackage productPackage;

    // 存款
    @Column(columnDefinition = "int default 0")
    private int deposit;

    // 数量
    @Column(columnDefinition = "int default 0")
    private int count;

    @Column(columnDefinition = "int default 0")
    private int deleteFlag;

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(updatable = false)
    private Date creationDate;

    @Column
    private Date lastUpdateDate;

    @Column(length=32)
    private String creator;

    @Column(length=32)
    private String lastUpdateBy;

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
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

    public String getMemberAssetId() {
        return memberAssetId;
    }

    public void setMemberAssetId(String memberAssetId) {
        this.memberAssetId = memberAssetId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAssetType() {
        return assetType;
    }

    public void setAssetType(int assetType) {
        this.assetType = assetType;
    }
}
