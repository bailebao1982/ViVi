package com.spstudio.modules.stock.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Soul on 2017/1/11.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="T_StockEventType")
public class StockEventType {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    String stockEventTypeId;

    @Column
    String stockEventTypeName;

    @Column(columnDefinition = "int default 1")
    // 0  stand for minus
    // 1  stand for plus
    int calculationMethod;

    @Column
    String stockEventTypeNote;


    public String getStockEventTypeId() {
        return stockEventTypeId;
    }

    public void setStockEventTypeId(String stockEventTypeId) {
        this.stockEventTypeId = stockEventTypeId;
    }

    public String getStockEventTypeName() {
        return stockEventTypeName;
    }

    public void setStockEventTypeName(String stockEventTypeName) {
        this.stockEventTypeName = stockEventTypeName;
    }

    public int getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(int calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public String getStockEventTypeNote() {
        return stockEventTypeNote;
    }

    public void setStockEventTypeNote(String stockEventTypeNote) {
        this.stockEventTypeNote = stockEventTypeNote;
    }
}
