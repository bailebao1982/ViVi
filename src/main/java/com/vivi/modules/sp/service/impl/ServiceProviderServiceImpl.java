/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.sp.service.impl;

import com.vivi.modules.sp.dao.ServiceProviderDAO;
import com.vivi.modules.sp.service.ServiceProviderService;

/**
 *
 * @author wewezhu
 */
public class ServiceProviderServiceImpl implements ServiceProviderService{
    private ServiceProviderDAO serviceProviderDAO;

    public ServiceProviderDAO getServiceProviderDAO() {
        return serviceProviderDAO;
    }

    public void setServiceProviderDAO(ServiceProviderDAO serviceProviderDAO) {
        this.serviceProviderDAO = serviceProviderDAO;
    }
    
    
    
}
