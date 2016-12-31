/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_ProductSet")
public class ProductSet {
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    @Column(length=32)
    String productSetId;
    
    
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,  
            CascadeType.REFRESH })
    @JoinColumn(name = "productId")
    Product product;
    
    @Column
    int count;
    
    @ManyToMany(mappedBy = "productSets")
    private Set<ProductPackage> productPackages;

    public String getProductSetId() {
        return productSetId;
    }

    public void setProductSetId(String productSetId) {
        this.productSetId = productSetId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<ProductPackage> getProductPackages() {
        return productPackages;
    }

    public void setProductPackages(Set<ProductPackage> productPackages) {
        this.productPackages = productPackages;
    }
    
    
    
}
