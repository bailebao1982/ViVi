/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.workorder.service.impl;

import com.spstudio.modules.workorder.dao.WorkOrderDAO;
import com.spstudio.modules.workorder.entity.WorkOrder;
import com.spstudio.modules.workorder.service.WorkOrderService;

/**
 *
 * @author wewezhu
 */
public class WorkOrderServiceImpl implements WorkOrderService {
    private WorkOrderDAO workOrderDAO;

    public WorkOrderDAO getWorkOrderDAO() {
        return workOrderDAO;
    }

    public void setWorkOrderDAO(WorkOrderDAO workOrderDAO) {
        this.workOrderDAO = workOrderDAO;
    }

    @Override
    public WorkOrder addWorkOrder(WorkOrder workOrder) {
        return workOrderDAO.addWorkOrder(workOrder);
    }

    @Override
    public WorkOrder confirmWorkOrder(String workOrderId, String rate, String status) {
        return workOrderDAO.confirmWorkOrder(workOrderId, rate, status);
    }

    @Override
    public WorkOrder findWorkOrdertByWordOrderId(String workOrderId) {
        return workOrderDAO.findWorkOrdertByWordOrderId(workOrderId);
    }
    
    
}
