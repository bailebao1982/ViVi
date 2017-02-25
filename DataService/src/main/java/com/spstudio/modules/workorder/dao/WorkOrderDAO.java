/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.workorder.dao;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.workorder.entity.WorkOrder;

import java.util.Date;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface WorkOrderDAO {
    public WorkOrder addWorkOrder(WorkOrder workOrder);
    
    public WorkOrder updateWorkOrder(WorkOrder workOrder);
    
    public WorkOrder findWorkOrderByWordOrderId(String workOrderId);

    public List<WorkOrder> findWorkOrdersOfServiceProvider(ServiceProvider provider);

    public List<WorkOrder> findWorkOrdersOfCustomer(Member member);

    public List<WorkOrder> findExpiredWorkOrder(Date dt);
    
    public void zapWorkOrder(WorkOrder workOrder);

    public List<WorkOrder> queryForPage(int offset, int length, SearchCriteria criteria);

    public int queryForCount(SearchCriteria criteria);
}
