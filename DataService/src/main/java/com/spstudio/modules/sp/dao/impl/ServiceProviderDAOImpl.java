/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sp.dao.impl;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.sp.dao.ServiceProviderDAO;
import com.spstudio.modules.sp.entity.ServiceProvider;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.util.List;

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
    public List<ServiceProvider> getAllServiceProviders() {
        String hql = "from ServiceProvider where deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    @Override
    public ServiceProvider findServiceProviderById(String spId) {
        String hql = "from ServiceProvider where serviceProviderId = :id and deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", spId);
        List<ServiceProvider> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public ServiceProvider addServiceProvider(ServiceProvider sp) {
        sp.setCreationDate(new Date(System.currentTimeMillis()));
        this.sessionFactory.getCurrentSession().saveOrUpdate(sp);
        return sp;
    }

    @Override
    public ServiceProvider updateServiceProvider(ServiceProvider sp) {
        sp.setLastUpdateDate(new Date(System.currentTimeMillis()));
        this.sessionFactory.getCurrentSession().saveOrUpdate(sp);
        return sp;
    }

    @Override
    public ServiceProvider removeServiceProvider(ServiceProvider sp) {
        sp.setLastUpdateDate(new Date(System.currentTimeMillis()));
        sp.setDeleteFlag(1);
        this.sessionFactory.getCurrentSession().saveOrUpdate(sp);
        return sp;
    }

    @Override
    public boolean removeServiceProviderList(List<String> spIdList) {
        String hql = "update ServiceProvider set deleteFlag = 1, lastUpdateDate = :updateDate where serviceProviderId in :sp_ids";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("updateDate", new Date(System.currentTimeMillis()));
        query.setParameterList("sp_ids", spIdList);
        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public void zapServiceProvider(ServiceProvider sp) {
        this.sessionFactory.getCurrentSession().delete(sp);
    }

    @Override
    public int getAllRowCount() {
        Long count = (Long)sessionFactory.getCurrentSession().
                createQuery("select count(1) from ServiceProvider where deleteFlag = 0").uniqueResult();
        return count.intValue();
    }

    @Override
    public List<ServiceProvider> queryForPage(int offset, int length, SearchCriteria criteria) {
        List<ServiceProvider> entitylist = null;
        try{
            StringBuffer queryString = new StringBuffer();
            queryString.append("from ServiceProvider where deleteFlag = 0");

            for(String key:criteria.getItemMap().keySet()){
                queryString.append(" and ");
                queryString.append(criteria.getItemMap().get(key).getSearchCriteriaItem());
            }

            Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
            query.setFirstResult(offset);
            query.setMaxResults(length);
            entitylist = query.list();

        }catch(RuntimeException re){
            throw re;
        }

        return entitylist;
    }
}
