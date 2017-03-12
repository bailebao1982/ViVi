/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sp.service;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.sp.entity.SPInviteCode;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.sp.entity.ServiceProviderType;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface ServiceProviderService {
    public List<ServiceProviderType> listServiceProviderTypes();

    public ServiceProviderType findServiceProviderTypeById(String spTypeId);

    public ServiceProviderType findServiceProviderTypeByType(String spType);

    public ServiceProviderType addServiceProviderType(ServiceProviderType spType);

    public boolean removeServiceProviderType(ServiceProviderType type);

    public ServiceProvider findServiceProviderById(String spId);

    public ServiceProvider addServiceProvider(ServiceProvider sp);
    
    public ServiceProvider updateServiceProvider(ServiceProvider sp);
    
    public ServiceProvider removeServiceProvider(ServiceProvider sp);

    public boolean removeServiceProviderInList(List<String> spIdList);

    public Page<ServiceProvider> queryForPage(int currentPage, int pageSize, SearchCriteria sc);
    
    public void zapServiceProvider(ServiceProvider sp);

    public SPInviteCode getEffectiveInviteCode(String inviteCode);
}
