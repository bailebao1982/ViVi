package com.spstudio.common.service.impl;

import com.spstudio.common.service.SystemConfigService;
import com.spstudio.common.service.dao.SystemConfigDAO;
import com.spstudio.common.service.entity.SystemConfig;

import java.util.List;

/**
 * Created by Soul on 2017/1/20.
 */
public class SystemConfigServiceImpl implements SystemConfigService {

    private final static String CONFIG_MODULE_GLOBAL = "*";

    private SystemConfigDAO configDAO;

    public SystemConfigDAO getConfigDAO() {
        return configDAO;
    }

    public void setConfigDAO(SystemConfigDAO configDAO) {
        this.configDAO = configDAO;
    }

    @Override
    public SystemConfig findGlobalConfig(String configKey) {
        return configDAO.findConfig("*", configKey) ;
    }

    @Override
    public List<SystemConfig> findModuleConfigs(String module) {
        return configDAO.listModuleConfigs(module);
    }

    @Override
    public SystemConfig findModuleSingleConfig(String module, String configKey) {
        return configDAO.findConfig(module, configKey);
    }

    @Override
    public List<SystemConfig> findModuleConfig(String module, String configKey) {
        return configDAO.findModuleConfig(module, configKey);
    }

    private SystemConfig _createConfig(String configModule,
                                       String configKey,
                                       String configName,
                                       String configEntity,
                                       String configCondition,
                                       String configValue,
                                       String configNote){
        SystemConfig config = new SystemConfig();
        config.setConfigKey(configKey);
        config.setConfigName(configName);
        config.setConfigEntity(configEntity);
        config.setConfigCondition(configCondition);
        config.setConfigValue(configValue);
        config.setConfigModule(configModule);
        config.setConfigNote(configNote);

        return config;
    }
    @Override
    public SystemConfig addConfig(SystemConfig config){
        return configDAO.addConfig(config);
    }

    @Override
    public SystemConfig addGlobalConfig(
            String configKey,
            String configName,
            String configEntity,
            String configCondition,
            String configValue,
            String configNote) {
        SystemConfig config = _createConfig(
                CONFIG_MODULE_GLOBAL,
                configKey,
                configName,
                configEntity,
                configCondition,
                configValue,
                configNote);
        return configDAO.addConfig(config);
    }

    @Override
    public SystemConfig addModuleConfig(
            String configKey,
            String configModule,
            String configName,
            String configEntity,
            String configCondition,
            String configValue,
            String configNote) {
        SystemConfig config = _createConfig(
                configModule,
                configKey,
                configName,
                configEntity,
                configCondition,
                configValue,
                configNote);
        return configDAO.addConfig(config);
    }

    @Override
    public SystemConfig updateConfig(SystemConfig config) {
        return configDAO.updateConfig(config);
    }

    @Override
    public boolean zapConfig(SystemConfig config) {
        return configDAO.zapConfig(config);
    }

    @Override
    public boolean zapModuleConfig(String module, String configKey) {
        return configDAO.zapModuleConfig(module, configKey);
    }
}
