/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sp.dao.impl;

import com.spstudio.modules.sp.dao.ServiceProviderDAO;
import com.spstudio.modules.sp.entity.ServiceProvider;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class ServiceProviderDAOImpl implements ServiceProviderDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ServiceProvider addServiceProvider(ServiceProvider sp) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(sp);
        return sp;
    }

    @Override
    public ServiceProvider updateServiceProvider(ServiceProvider sp) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(sp);
        return sp;
    }

    @Override
    public ServiceProvider deleteServiceProvider(ServiceProvider sp) {
        sp.setDeleteFlag(1);
        this.sessionFactory.getCurrentSession().saveOrUpdate(sp);
        return sp;
    }

    @Override
    public void zapServiceProvider(ServiceProvider sp) {
        this.sessionFactory.getCurrentSession().delete(sp);
    }
    
    
}
