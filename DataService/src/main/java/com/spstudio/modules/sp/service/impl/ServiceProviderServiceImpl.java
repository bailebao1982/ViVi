/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sp.service.impl;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.sp.dao.ServiceProviderTypeDAO;
import com.spstudio.modules.sp.entity.ServiceProviderType;
import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.sp.dao.ServiceProviderDAO;
import com.spstudio.modules.sp.entity.ServiceProvider;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public class ServiceProviderServiceImpl implements ServiceProviderService {
    private ServiceProviderDAO serviceProviderDAO;

    private ServiceProviderTypeDAO serviceProviderTypeDAO;

    public ServiceProviderDAO getServiceProviderDAO() {
        return serviceProviderDAO;
    }

    public void setServiceProviderDAO(ServiceProviderDAO serviceProviderDAO) {
        this.serviceProviderDAO = serviceProviderDAO;
    }

    public ServiceProviderTypeDAO getServiceProviderTypeDAO() {
        return serviceProviderTypeDAO;
    }

    public void setServiceProviderTypeDAO(ServiceProviderTypeDAO serviceProviderTypeDAO) {
        this.serviceProviderTypeDAO = serviceProviderTypeDAO;
    }

    @Override
    public List<ServiceProviderType> listServiceProviderTypes() {
        return serviceProviderTypeDAO.listServiceProviderType();
    }

    @Override
    public ServiceProviderType findServiceProviderTypeById(String spTypeId) {
        return serviceProviderTypeDAO.findServiceProviderTypeById(spTypeId);
    }

    @Override
    public ServiceProviderType findServiceProviderTypeByType(String spType) {
        return serviceProviderTypeDAO.findServiceProviderTypeByType(spType);
    }

    @Override
    public ServiceProviderType addServiceProviderType(ServiceProviderType spType) {
        return serviceProviderTypeDAO.addServiceProviderType(spType);
    }

    @Override
    public boolean removeServiceProviderType(ServiceProviderType type) {
        return serviceProviderTypeDAO.removeServiceProviderType(type);
    }

    @Override
    public boolean removeServiceProviderInList(List<String> spIdList) {
        return serviceProviderDAO.removeServiceProviderList(spIdList);
    }

    @Override
    public ServiceProvider findServiceProviderById(String spId) {
        return serviceProviderDAO.findServiceProviderById(spId);
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
    public ServiceProvider removeServiceProvider(ServiceProvider sp) {
        return serviceProviderDAO.removeServiceProvider(sp);
    }

    @Override
    public Page<ServiceProvider> queryForPage(int currentPage, int pageSize, SearchCriteria sc) {
        Page<ServiceProvider> page = new Page<ServiceProvider>();
        //当前页开始记录
        int offset = page.countOffset(currentPage,pageSize);
        //分页查询结果集
        List<ServiceProvider> list = serviceProviderDAO.queryForPage(offset, pageSize,sc);
        //总记录数
        int allRow = list.size();

        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);

        return page;
    }

    @Override
    public void zapServiceProvider(ServiceProvider sp) {
        serviceProviderDAO.zapServiceProvider(sp);
    }

}
