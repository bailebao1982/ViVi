/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.entity;


import com.spstudio.modules.product.entity.Product;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_MemberAsset")
public class MemberAsset {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    @Column(length=32)
    private String memberAssetId;
    
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,  
            CascadeType.REFRESH })
    @JoinColumn(name = "memberId") 
    private Member memeber;
    
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,  
            CascadeType.REFRESH })
    @JoinColumn(name = "productId")
    private Product product;
    
    @Column
    private int deposit;
    
    @Column
    private int count;

    public String getMemberAssetId() {
        return memberAssetId;
    }

    public void setMemberAssetId(String memberAssetId) {
        this.memberAssetId = memberAssetId;
    }

    
    public Member getMemeber() {
        return memeber;
    }

    public void setMemeber(Member memeber) {
        this.memeber = memeber;
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

    
    
    
}
