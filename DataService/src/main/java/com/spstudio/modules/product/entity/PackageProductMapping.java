/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.entity;

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
@Table(name="T_PackageMapping")
@DynamicUpdate
@DynamicInsert
public class PackageProductMapping {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    @Column(length=32)
    private String mappingId;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name="productPackageId" ,referencedColumnName="productPackageId")
    private ProductPackage productPackage;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "productId" ,referencedColumnName="productId")
    Product product;
    
    @Column
    int count;

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
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

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
    }
}
