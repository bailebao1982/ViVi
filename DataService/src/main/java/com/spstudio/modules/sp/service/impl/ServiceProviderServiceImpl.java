/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sp.service.impl;

import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.sp.dao.ServiceProviderDAO;

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
    
    
    
}
