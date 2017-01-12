package com.spstudio.modules.stock.entity;

import com.spstudio.modules.product.entity.Product;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Soul on 2017/1/11.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="T_StockHistory")
public class StockHistory {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    String stockEventId;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "productId")
    Product product;

    // 类别
    // 出库，入库 等
    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="stockEventTypeId",referencedColumnName="stockEventTypeId")
    StockEventType type;

    // 出入库数量
    @Column
    int quantity;

    // 本单位负责人
    @Column
    String charger;

    // 供应商(maybe Later will have create 供应商管理 模块)
    @Column
    String supplier;

    // 供应商接洽人
    @Column
    String contact;

    @Column
    String creator;

    // 交易发生日
    @Column
    Date creationDate;

    @Column
    Date lastUpdateDate;

    @Column(length=32)
    String lastUpdateBy;

    public String getStockEventId() {
        return stockEventId;
    }

    public void setStockEventId(String stockEventId) {
        this.stockEventId = stockEventId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public StockEventType getType() {
        return type;
    }

    public void setType(StockEventType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
