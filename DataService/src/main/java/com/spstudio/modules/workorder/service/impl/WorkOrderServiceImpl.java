/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.workorder.service.impl;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.utils.DateUtils;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.workorder.dao.WorkOrderDAO;
import com.spstudio.modules.workorder.entity.WorkOrder;
import com.spstudio.modules.workorder.service.WorkOrderService;
import com.spstudio.modules.workorder.service.WorkOrderStatusType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    @Transactional
    public WorkOrder addWorkOrder(WorkOrder workOrder) {
        // TODO: should add logic for member asset deduction
        return workOrderDAO.addWorkOrder(workOrder);
    }

    @Override
    public WorkOrder confirmWorkOrder(String workOrderId, int rate, String confirmComment) {
        WorkOrder workOrder = findWorkOrderByWordOrderId(workOrderId);
        if(workOrder != null){
            //
            // TODO: Real deduction of member asserts should be done here,
            // TODO: so do we need to change the sale module for
            // TODO: member assert check logic ???
            //
            // I think the best logic should be like below:
            // 1. real assets deduction when work order created
            // user confirm --> OK
            // user not confirm, exceed effective end date-> add asset back
            // cancel workorder -> add asset back
            //

            Date now = DateUtils.getDateNow();
            workOrder.setConfirmDate(now);
            workOrder.setRate(rate);
            workOrder.setConfirmComment(confirmComment);
            workOrder.setStatus(WorkOrderStatusType.WO_CONFIRMED.ordinal());

            workOrderDAO.updateWorkOrder(workOrder);
        }
        return workOrder;
    }

    @Override
    @Transactional
    public boolean cancelWorkOrder(WorkOrder workOrder) {
        // cancel workorder -> add asset back

        this.removeWorkOrder(workOrder);
        return false;
    }

    @Override
    public WorkOrder findWorkOrderByWordOrderId(String workOrderId) {
        return workOrderDAO.findWorkOrderByWordOrderId(workOrderId);
    }

    @Override
    public void zapWorkOrder(WorkOrder workOrder) {
       workOrderDAO.zapWorkOrder(workOrder);
    }

    @Override
    public Page<WorkOrder> queryForPage(int currentPage, int pageSize, SearchCriteria sc) {

        Page<WorkOrder> page = new Page<WorkOrder>();
        //当前页开始记录
        int offset = page.countOffset(currentPage,pageSize);
        //分页查询结果集
        List<WorkOrder> list = workOrderDAO.queryForPage(offset, pageSize,sc);
        //总记录数
        int allRow = workOrderDAO.queryForCount(sc);
        //

        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);

        return page;
    }

    @Override
    public List<WorkOrder> findWorkOrderOfServiceProvider(ServiceProvider serviceProvider) {
        return workOrderDAO.findWorkOrdersOfServiceProvider(serviceProvider);
    }

    @Override
    public List<WorkOrder> findWorkOrderOfMember(Member member) {
        return workOrderDAO.findWorkOrdersOfCustomer(member);
    }

    @Override
    public void removeWorkOrder(WorkOrder workOrder) {
        if(workOrder != null){
            workOrder.setDeleteFlag(1);
            workOrderDAO.updateWorkOrder(workOrder);
        }
    }
}
