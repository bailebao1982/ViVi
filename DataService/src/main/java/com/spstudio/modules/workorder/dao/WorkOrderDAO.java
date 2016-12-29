/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.workorder.dao;

import com.spstudio.modules.workorder.entity.WorkOrder;

/**
 *
 * @author wewezhu
 */
public interface WorkOrderDAO {
    public WorkOrder addWorkOrder(WorkOrder workOrder);
    
    public WorkOrder confirmWorkOrder(String workOrderId,String rate,String status);
    
    public WorkOrder findWorkOrdertByWordOrderId(String workOrderId);
    
    public void zapWorkOrder(WorkOrder workOrder);
}
