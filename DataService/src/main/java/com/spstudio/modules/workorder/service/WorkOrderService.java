/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.workorder.service;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.workorder.entity.WorkOrder;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface WorkOrderService {
    
    public WorkOrder addWorkOrder(WorkOrder workOrder);
    
    public WorkOrder confirmWorkOrder(String workOrderId, int rate, String confirmComment);
    
    public WorkOrder findWorkOrderByWordOrderId(String workOrderId);

    public List<WorkOrder> findWorkOrderOfServiceProvider(ServiceProvider serviceProvider);

    public List<WorkOrder> findWorkOrderOfMember(Member member);

    public void removeWorkOrder(WorkOrder workOrder);

    public boolean cancelWorkOrder(WorkOrder workOrder);
    
    public void zapWorkOrder(WorkOrder workOrder);

    public Page<WorkOrder> queryForPage(int currentPage, int pageSize, SearchCriteria sc);


}
