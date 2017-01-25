package com.spstudio.modules.core.bean;

import com.spstudio.common.service.entity.SystemConfig;

/**
 * Created by Soul on 2017/1/23.
 */
public class SysConfigJsonBeanUtil {
    public static SysConfigJsonBean toJsonBean(SystemConfig configBean){
        SysConfigJsonBean sysConfig = new SysConfigJsonBean();

        sysConfig.setConfig_module(configBean.getConfigModule());
        sysConfig.setConfig_key(configBean.getConfigKey());
        sysConfig.setConfig_name(configBean.getConfigName());
        sysConfig.setConfig_note(configBean.getConfigNote());
        sysConfig.setConfig_value(configBean.getConfigValue());
        sysConfig.setConfig_condition(configBean.getConfigCondition());

        return sysConfig;
    }

    public static SystemConfig toEntityBean(SysConfigJsonBean jsonBean){
        SystemConfig sysEntityBean = new SystemConfig();

        sysEntityBean.setConfigKey(jsonBean.getConfig_key());
        sysEntityBean.setConfigModule(jsonBean.getConfig_module());
        sysEntityBean.setConfigValue(jsonBean.getConfig_value());
        sysEntityBean.setConfigNote(jsonBean.getConfig_note());
        sysEntityBean.setConfigName(jsonBean.getConfig_name());
        sysEntityBean.setConfigCondition(jsonBean.getConfig_condition());

        return sysEntityBean;
    }
}
