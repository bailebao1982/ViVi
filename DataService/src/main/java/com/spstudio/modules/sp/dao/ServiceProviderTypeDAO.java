package com.spstudio.modules.sp.dao;

import com.spstudio.modules.sp.entity.ServiceProviderType;

import java.util.List;

/**
 * Created by Soul on 2017/1/12.
 */
public interface ServiceProviderTypeDAO {
    public ServiceProviderType addServiceProviderType(ServiceProviderType type);

    public List<ServiceProviderType> listServiceProviderType();

    public ServiceProviderType findServiceProviderTypeById(String spTypeId);

    public ServiceProviderType findServiceProviderTypeByType(String spType);

    public boolean removeServiceProviderType(ServiceProviderType type);

    public boolean updateServiceProviderType(ServiceProviderType spType);
}
