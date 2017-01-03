/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sp.dao;

import com.spstudio.modules.sp.entity.ServiceProvider;

/**
 *
 * @author wewezhu
 */
public interface ServiceProviderDAO {
    public ServiceProvider addServiceProvider(ServiceProvider sp);
    
    public ServiceProvider updateServiceProvider(ServiceProvider sp);
    
    public ServiceProvider deleteServiceProvider(ServiceProvider sp);
    
    public void zapServiceProvider(ServiceProvider sp);
    
}
