package com.spstudio.modules.workorder.entity;

import com.spstudio.modules.member.entity.MemberAsset;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Soul on 2017/2/26.
 */
@Entity
@Table(name="T_WorkOrderMapping")
@DynamicUpdate
@DynamicInsert
public class WorkOrderAssetMapping {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    @Column(length=32)
    private String mappingId;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name="workOrderId" ,referencedColumnName="workOrderId")
    private WorkOrder workOrder;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "memberAssetId" ,referencedColumnName="memberAssetId")
    MemberAsset asset;

    @Column
    int count;

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public MemberAsset getAsset() {
        return asset;
    }

    public void setAsset(MemberAsset asset) {
        this.asset = asset;
    }
}
