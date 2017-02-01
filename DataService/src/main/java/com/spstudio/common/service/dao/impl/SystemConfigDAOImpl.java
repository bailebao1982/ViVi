package com.spstudio.common.service.dao.impl;

import com.spstudio.common.service.dao.SystemConfigDAO;
import com.spstudio.common.service.entity.SystemConfig;
import com.spstudio.common.utils.DateUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by Soul on 2017/1/20.
 */
public class SystemConfigDAOImpl implements SystemConfigDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SystemConfig findConfig(String module, String configKey) {
        List<SystemConfig> configList = findModuleConfig(module, configKey);
        if(configList == null)
            return null;
        return configList.get(0);
    }

    @Override
    public List<SystemConfig> listModuleConfigs(String module) {
        String hql = "from SytemConfig where configModule=:config_module";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("config_module", module);
        List<SystemConfig> configList = query.list();
        if(configList.size() > 0){
            return configList;
        }
        return null;
    }

    @Override
    public List<SystemConfig> findModuleConfig(String module, String configKey) {
        String hql = "from SytemConfig where configModule=:config_module and configKey=:config_key";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("config_module", module);
        query.setParameter("config_key", configKey);
        List<SystemConfig> configList = query.list();
        if(configList.size() > 0){
            return configList;
        }
        return null;
    }

    @Override
    public SystemConfig addConfig(SystemConfig config) {
        Date now = DateUtils.getDateNow();
        config.setCreationDate(now);
        config.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().save(config);
        return config;
    }

    @Override
    public SystemConfig updateConfig(SystemConfig config) {
        Date now = DateUtils.getDateNow();
        config.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().update(config);
        return config;
    }

    @Override
    public boolean zapConfig(SystemConfig config) {
        sessionFactory.getCurrentSession().delete(config);
        return true;
    }

    @Override
    public boolean zapModuleConfig(String module, String configKey){
        String hql = "delete from SytemConfig where configModule=:config_module and configKey=:config_key";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("config_module", module);
        query.setParameter("config_key", configKey);

        int result = query.executeUpdate();
        return (result > 0);
    }
}
