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
    @JoinColumn(name = "productId")
    Product product;
    
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,  
            CascadeType.REFRESH })
    @JoinColumn(name = "memberId")
    Member member;
    
    int salesCount;
    
    //TODO: change to be ref object
    String saler;
    
    @Column(name = "salesDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date salesDate;

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
    
    
    
    
}
