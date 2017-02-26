package com.spstudio.modules.workorder.entity;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.product.entity.Product;


import java.util.Date;
import java.util.Set;
import javax.persistence.*;

import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.sp.entity.ServiceProvider;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author wewezhu
 */
@Entity
@Table(name="T_WorkOrder")
public class WorkOrder {
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    String workOrderId;
    
//    @Column(name = "registerDate", columnDefinition="DATETIME")
//    @Temporal(TemporalType.DATE)
//    Date registerDate;

//    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
//    @JoinTable(name = "member_workorder",
//            joinColumns = {@JoinColumn(name = "workOrderId")},
//            inverseJoinColumns = {@JoinColumn(name = "memberId")})
//    // 可能存在一种工单以班级的形式为多个consumer开放，只要有一个人confirm了就可以了
//  Set<Member> customers;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="serviceProviderId",referencedColumnName="serviceProviderId")
    ServiceProvider serviceProvider;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="memberId",referencedColumnName="memberId")
    Member customer;

//    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
//    @JoinColumn(name = "serviceProviderId")
//    // 可能存在一种服务是多个service provider为一个人服务
//    Set<ServiceProvider> serviceProviders;

    @JoinColumn(name="workOrderId" ,referencedColumnName="workOrderId")
    Set<WorkOrderAssetMapping> assetMappingSet;

    @Column(length=32)
    String comment;

    @Column(length=32)
    int Rate;   // 打分

    // employee create work order
    @Column(name = "serviceDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date serviceDate;

    // employee create work order
    @Column(name = "createDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date createDate;

    // consumer confirm work order
    @Column(name = "confirmDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date confirmDate;

    // when will the work order lose effect
    @Column(name = "effectiveEndDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date effectiveEndDate;

    // UNCONFIRMED, CONFIRMED
    @Column(columnDefinition = "int default 0")
    int Status; // 0: UNCONFIRMED, 1: CONFIRMED

    @Column(length=32)
    String serviceLocation;

    @Column(length=32)
    String confirmComment;

    @Column(columnDefinition = "int default 0")
    int deleteFlag;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "SNAPSHOT1", columnDefinition = "BLOB",nullable=true)
    private byte[] snapshot1;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "SNAPSHOT2", columnDefinition = "BLOB",nullable=true)
    private byte[] snapshot2;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "SNAPSHOT3", columnDefinition = "BLOB",nullable=true)
    private byte[] snapshot3;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "SNAPSHOT4", columnDefinition = "BLOB",nullable=true)
    private byte[] snapshot4;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "SNAPSHOT5", columnDefinition = "BLOB",nullable=true)
    private byte[] snapshot5;
    
//    public Date getRegisterDate() {
//        return registerDate;
//    }
//
//    public void setRegisterDate(Date registerDate) {
//        this.registerDate = registerDate;
//    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int Rate) {
        this.Rate = Rate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public byte[] getSnapshot1() {
        return snapshot1;
    }

    public void setSnapshot1(byte[] snapshot1) {
        this.snapshot1 = snapshot1;
    }

    public byte[] getSnapshot2() {
        return snapshot2;
    }

    public void setSnapshot2(byte[] snapshot2) {
        this.snapshot2 = snapshot2;
    }

    public byte[] getSnapshot3() {
        return snapshot3;
    }

    public void setSnapshot3(byte[] snapshot3) {
        this.snapshot3 = snapshot3;
    }

    public byte[] getSnapshot4() {
        return snapshot4;
    }

    public void setSnapshot4(byte[] snapshot4) {
        this.snapshot4 = snapshot4;
    }

    public byte[] getSnapshot5() {
        return snapshot5;
    }

    public void setSnapshot5(byte[] snapshot5) {
        this.snapshot5 = snapshot5;
    }

    public Member getCustomer() {
        return customer;
    }

    public void setCustomer(Member customer) {
        this.customer = customer;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getConfirmComment() {
        return confirmComment;
    }

    public void setConfirmComment(String confirmComment) {
        this.confirmComment = confirmComment;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public Set<WorkOrderAssetMapping> getAssetMappingSet() {
        return assetMappingSet;
    }

    public void setAssetMappingSet(Set<WorkOrderAssetMapping> assetMappingSet) {
        this.assetMappingSet = assetMappingSet;
    }
}