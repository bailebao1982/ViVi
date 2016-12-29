/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.workorder.dao.impl;

import com.spstudio.modules.workorder.dao.WorkOrderDAO;
import com.spstudio.modules.workorder.entity.WorkOrder;
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
    public WorkOrder findWorkOrdertByWordOrderId(String workOrderId) {
        String hql = "from WorkOrder where workOrderId = :id and deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", workOrderId);
        List<WorkOrder> list = query.list();
        if(list.size()>0)
            return list.get(0);
        return null;
    }
    
    @Override
    public WorkOrder confirmWorkOrder(String workOrderId, String rate, String status) {
       WorkOrder wo = this.findWorkOrdertByWordOrderId(workOrderId);
       wo.setRate(rate);
       wo.setStatus(status);
       this.sessionFactory.getCurrentSession().saveOrUpdate(wo);
       return wo;
       
    }

    @Override
    public void zapWorkOrder(WorkOrder workOrder) {
        this.sessionFactory.getCurrentSession().delete(workOrder);
    }
    
    
}
