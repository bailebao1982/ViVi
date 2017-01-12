package com.spstudio.modules.sp.dao.impl;

import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.sp.dao.ServiceProviderTypeDAO;
import com.spstudio.modules.sp.entity.ServiceProviderType;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.util.List;

/**
 * Created by Soul on 2017/1/12.
 */
public class ServiceProviderTypeDAOImpl implements ServiceProviderTypeDAO{

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ServiceProviderType addServiceProviderType(ServiceProviderType type) {
        type.setCreationDate(new Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().saveOrUpdate(type);
        return type;
    }

    @Override
    public List<ServiceProviderType> listServiceProviderType() {
        String hql = "from ServiceProviderType as serviceProviderType";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<ServiceProviderType> list = query.list();
        return list;
    }

    @Override
    public ServiceProviderType findServiceProviderTypeById(String spTypeId) {
        String hql = "from ServiceProviderType as serviceProviderType where serviceProviderType.serviceProviderTypeId = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", spTypeId);
        List<ServiceProviderType> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public ServiceProviderType findServiceProviderTypeByType(String spType) {
        String hql = "from ServiceProviderType as serviceProviderType where serviceProviderType.serviceProviderTypeName = :type";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("type", spType);
        List<ServiceProviderType> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public boolean removeServiceProviderType(ServiceProviderType type) {
        sessionFactory.getCurrentSession().delete(type);
        return true;
    }

    @Override
    public boolean updateServiceProviderType(ServiceProviderType type) {
        type.setLastUpdateDate(new Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().update(type);
        return true;
    }
}
