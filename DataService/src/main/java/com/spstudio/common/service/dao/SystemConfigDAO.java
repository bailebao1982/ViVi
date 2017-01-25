package com.spstudio.common.service.dao;

import com.spstudio.common.service.entity.SystemConfig;

import java.util.List;

/**
 * Created by Soul on 2017/1/20.
 */
public interface SystemConfigDAO {
    public SystemConfig findConfig(String module, String configKey);

    public List<SystemConfig> listModuleConfigs(String module);
    public List<SystemConfig> findModuleConfig(String module, String configKey);

    public SystemConfig addConfig(SystemConfig config);

    public SystemConfig updateConfig(SystemConfig config);

    public boolean zapConfig(SystemConfig config);

    public boolean zapModuleConfig(String module, String configKey);

}
