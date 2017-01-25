package com.spstudio.common.service.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Soul on 2017/1/20.
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="T_SystemConfig",
        uniqueConstraints={@UniqueConstraint(columnNames={"configKey"})}
)
public class SystemConfig {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    @Column(length=32)
    private String configId;

    @Column
    private String configModule;

    @Column
    private String configKey;

    @Column
    private String configName;

    // to whom I'm gonna restrict
    @Column
    private String configEntity;

    // when should the config take effect
    @Column
    private String configCondition;

    // which value should be changed to
    @Column
    private String configValue;

    @Column
    private String configNote;

    @Column(updatable = false)
    private Date creationDate;

    @Column
    private Date lastUpdateDate;


    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigEntity() {
        return configEntity;
    }

    public void setConfigEntity(String configEntity) {
        this.configEntity = configEntity;
    }

    public String getConfigCondition() {
        return configCondition;
    }

    public void setConfigCondition(String configCondition) {
        this.configCondition = configCondition;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
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

    public String getConfigModule() {
        return configModule;
    }

    public void setConfigModule(String configModule) {
        this.configModule = configModule;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigNote() {
        return configNote;
    }

    public void setConfigNote(String configNote) {
        this.configNote = configNote;
    }
}
