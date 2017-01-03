/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sp.service.impl;

import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.sp.dao.ServiceProviderDAO;
import com.spstudio.modules.sp.entity.ServiceProvider;

/**
 *
 * @author wewezhu
 */
public class ServiceProviderServiceImpl implements ServiceProviderService {
    private ServiceProviderDAO serviceProviderDAO;

    public ServiceProviderDAO getServiceProviderDAO() {
        return serviceProviderDAO;
    }

    public void setServiceProviderDAO(ServiceProviderDAO serviceProviderDAO) {
        this.serviceProviderDAO = serviceProviderDAO;
    }

    @Override
    public ServiceProvider addServiceProvider(ServiceProvider sp) {
        return serviceProviderDAO.addServiceProvider(sp);
    }

    @Override
    public ServiceProvider updateServiceProvider(ServiceProvider sp) {
        return serviceProviderDAO.updateServiceProvider(sp);
    }

    @Override
    public ServiceProvider deleteServiceProvider(ServiceProvider sp) {
        return serviceProviderDAO.deleteServiceProvider(sp);
    }

    @Override
    public void zapServiceProvider(ServiceProvider sp) {
        serviceProviderDAO.zapServiceProvider(sp);
    }
    
    
    
}
