/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.stock.entity;

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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="T_Stock")
public class Stock {
    
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    String stockId;
    
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,  
            CascadeType.REFRESH })
    @JoinColumn(name = "productId")
    Product product;
    
    @Column
    int inventory;

    @Column
    boolean isinfinite;

    public boolean isinfinite() {
        return isinfinite;
    }

    public void setIsinfinite(boolean isinfinite) {
        this.isinfinite = isinfinite;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
}
