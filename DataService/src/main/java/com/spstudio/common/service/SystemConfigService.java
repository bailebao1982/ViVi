package com.spstudio.common.service;

import com.spstudio.common.service.entity.SystemConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Soul on 2017/1/20.
 */
@Service
public interface SystemConfigService {
    public SystemConfig findGlobalConfig(String configKey);

    public List<SystemConfig> findModuleConfigs(String module);
    public SystemConfig findModuleSingleConfig(String module, String configKey);
    public List<SystemConfig> findModuleConfig(String module, String configKey);

    public SystemConfig addConfig(SystemConfig config);
    public SystemConfig addGlobalConfig(String configKey, String configName, String configEntity, String configCondition, String configValue, String Note);
    public SystemConfig addModuleConfig(String configKey, String configModule, String configName, String configEntity, String configCondition, String configValue, String Note);


    public SystemConfig updateConfig(SystemConfig config);

    public boolean zapConfig(SystemConfig config);
    public boolean zapModuleConfig(String module, String configKey);
}
