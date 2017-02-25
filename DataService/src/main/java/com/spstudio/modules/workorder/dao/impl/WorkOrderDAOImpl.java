/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.workorder.dao.impl;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.workorder.dao.WorkOrderDAO;
import com.spstudio.modules.workorder.entity.WorkOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class WorkOrderDAOImpl implements WorkOrderDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public WorkOrder addWorkOrder(WorkOrder workOrder) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(workOrder);
        return workOrder;
    }

    @Override
    public WorkOrder findWorkOrderByWordOrderId(String workOrderId) {
        String hql = "from WorkOrder where workOrderId = :id and deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", workOrderId);
        List<WorkOrder> list = query.list();
        if(list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public List<WorkOrder> findWorkOrdersOfServiceProvider(ServiceProvider provider) {
        String hql = "from WorkOrder where serviceProviderId = :id and deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", provider.getServiceProviderId());
        List<WorkOrder> list = query.list();
        return list;
    }

    @Override
    public List<WorkOrder> findWorkOrdersOfCustomer(Member member) {
        String hql = "from WorkOrder where memberId = :id and deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", member.getMemberId());
        List<WorkOrder> list = query.list();
        return list;
    }

    @Override
    public List<WorkOrder> findExpiredWorkOrder(Date dt) {
        String hql = "from WorkOrder where effectiveEndDate < :dt";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("dt", dt.toString());
        List<WorkOrder> list = query.list();
        return list;
    }

    @Override
    public WorkOrder updateWorkOrder(WorkOrder workOrder) {
       this.sessionFactory.getCurrentSession().saveOrUpdate(workOrder);
       return workOrder;
    }

    @Override
    public void zapWorkOrder(WorkOrder workOrder) {
        this.sessionFactory.getCurrentSession().delete(workOrder);
    }

    @Override
    public List<WorkOrder> queryForPage(int offset, int length, SearchCriteria criteria) {
        List<WorkOrder> entitylist = new ArrayList<WorkOrder>();
        try{
            StringBuffer queryString = new StringBuffer();
            queryString.append("from WorkOrder where deleteFlag = 0");

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

    @Override
    public int queryForCount(SearchCriteria criteria) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select count(1) from WorkOrder where deleteFlag = 0");

        for(String key:criteria.getItemMap().keySet()){
            queryString.append(" and ");
            queryString.append(criteria.getItemMap().get(key).getSearchCriteriaItem());
        }

        Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
        Long count = (Long)query.uniqueResult();;
        return count.intValue();
    }

}
