package com.spstudio.modules.workorder.entity;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.product.entity.Product;


import java.sql.Time;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name="T_WorkOrder")
public class WorkOrder {
    @Id  
    @GeneratedValue(generator="system-uuid")  
    @GenericGenerator(name = "system-uuid",strategy="uuid")  
    String workOrderId;
    
    @Column(name = "registerDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date registerDate;
    
    
    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "member_workorder", 
	   joinColumns = {@JoinColumn(name = "workOrderId")}, 
	   inverseJoinColumns = {@JoinColumn(name = "memberId")})        
    Set<Member> customers;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,  
            CascadeType.REFRESH })
    @JoinColumn(name = "productId")
    Product consumeProduct;

    @Column(length=32)
    String comment;

    @Column(length=32)
    String Rate;

    @Column(name = "createDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date createDate;

    @Column(length=32)
    String Status;

    @Column(name = "confirmTime", columnDefinition="DATETIME")
    @Temporal(TemporalType.DATE)
    Date confirmTime;

    @Column(length=32)
    String serviceLocation;
    
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
    
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Set<Member> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Member> customers) {
        this.customers = customers;
    }

    public Product getConsumeProduct() {
        return consumeProduct;
    }

    public void setConsumeProduct(Product consumeProduct) {
        this.consumeProduct = consumeProduct;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String Rate) {
        this.Rate = Rate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
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
    
    
    
}