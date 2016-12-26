package com.spstudio.modules.workorder.entity;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.product.entity.Product;


import java.sql.Time;
import java.util.Date;
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
    
    @ManyToMany(mappedBy = "workOrders")
    Set<Member> customer;

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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Set<Member> getCustomer() {
        return customer;
    }

    public void setCustomer(Set<Member> customer) {
        this.customer = customer;
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
    
    
    
}