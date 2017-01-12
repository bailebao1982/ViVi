/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sp.dao;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.sp.entity.ServiceProvider;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface ServiceProviderDAO {

    public List<ServiceProvider> getAllServiceProviders();

    public ServiceProvider findServiceProviderById(String spId);

    public ServiceProvider addServiceProvider(ServiceProvider sp);
    
    public ServiceProvider updateServiceProvider(ServiceProvider sp);
    
    public ServiceProvider removeServiceProvider(ServiceProvider sp);

    public boolean removeServiceProviderList(List<String> spIdList);
    
    public void zapServiceProvider(ServiceProvider sp);

    public int getAllRowCount();

    public List<ServiceProvider> queryForPage(int offset, int length, SearchCriteria criteria);
    
}
