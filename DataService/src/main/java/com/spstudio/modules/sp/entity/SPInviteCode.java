package com.spstudio.modules.sp.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Soul on 2017/3/12.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="T_ServiceProvider",
       uniqueConstraints={@UniqueConstraint(columnNames={"inviteCode"})}
)
public class SPInviteCode {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    String inviteCodeId;

    @ManyToOne(cascade = { CascadeType.DETACH })
    @JoinColumn(name = "serviceProviderId")
    private ServiceProvider serviceProvider;

    @Column(updatable = false)
    private String inviteCode;

    @Column
    private Date effectiveStartDate;

    @Column
    private Date effectiveEndDate;

    public String getInviteCodeId() {
        return inviteCodeId;
    }

    public void setInviteCodeId(String inviteCodeId) {
        this.inviteCodeId = inviteCodeId;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }
}
