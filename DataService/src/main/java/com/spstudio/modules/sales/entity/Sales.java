/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sales.entity;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.product.entity.Product;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.spstudio.modules.product.entity.ProductPackage;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_Sales")
public class Sales {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    @Column(length=32)
    String saleId;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "memberId")
    Member member;

    /**
     * assetType:
     * 0 ---- product
     * 1 ---- package
     * 2 ---- deposit
     */
    @Column(length=32,
            columnDefinition = "int default 0")
    int saleType;

    /**
     * paymentMethodType:
     * 0 ---- cash
     * 1 ---- bonus point
     * 2 ---- deposit
     */
    @Column(length=32,
            columnDefinition = "int default 0")
    int paymentMethodType;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "productId")
    Product product;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "discountId")
    SaleDiscount discount;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "productPackageId")
    ProductPackage productPackage;

    @Column(length=32,
            columnDefinition = "int default 0")
    int deposit;

    @Column(length=32,
            columnDefinition = "int default 0")
    int salesCount;

//    @Column(length=32,
//            columnDefinition = "int default 0")
//    int salesCount;

    // 真实的价格
    @Column
    float price;

    @Column(length=32,
            columnDefinition = "int default 0")
    int deleteFlag;
    
    //TODO: change to be ref object
    String saler;
    
    @Column(name = "salesDate",
            columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date salesDate;

    @Column
    private Date lastUpdateDate;

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getSaleType() {
        return saleType;
    }

    public void setSaleType(int saleType) {
        this.saleType = saleType;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public SaleDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(SaleDiscount discount) {
        this.discount = discount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(int paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }
}
