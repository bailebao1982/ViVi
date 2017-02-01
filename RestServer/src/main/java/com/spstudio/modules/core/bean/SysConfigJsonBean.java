package com.spstudio.modules.core.bean;

/**
 * Created by Soul on 2017/1/23.
 */
public class SysConfigJsonBean {
    private String config_key;

    private String config_module;

    private String config_name;

    private String config_condition;

    private String config_value;

    private String config_note;

    public String getConfig_key() {
        return config_key;
    }

    public void setConfig_key(String config_key) {
        this.config_key = config_key;
    }

    public String getConfig_name() {
        return config_name;
    }

    public void setConfig_name(String config_name) {
        this.config_name = config_name;
    }

    public String getConfig_condition() {
        return config_condition;
    }

    public void setConfig_condition(String config_condition) {
        this.config_condition = config_condition;
    }

    public String getConfig_value() {
        return config_value;
    }

    public void setConfig_value(String config_value) {
        this.config_value = config_value;
    }

    public String getConfig_note() {
        return config_note;
    }

    public void setConfig_note(String config_note) {
        this.config_note = config_note;
    }

    public String getConfig_module() {
        return config_module;
    }

    public void setConfig_module(String config_module) {
        this.config_module = config_module;
    }
}
