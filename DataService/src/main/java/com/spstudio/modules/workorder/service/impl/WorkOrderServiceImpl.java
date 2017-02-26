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
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.workorder.dao.WorkOrderDAO;
import com.spstudio.modules.workorder.entity.WorkOrder;
import com.spstudio.modules.workorder.entity.WorkOrderAssetMapping;
import com.spstudio.modules.workorder.exception.InsuffientPackageAssetException;
import com.spstudio.modules.workorder.exception.InsuffientProductAssetException;
import com.spstudio.modules.workorder.exception.AssetNotFoundException;
import com.spstudio.modules.workorder.service.WorkOrderService;
import com.spstudio.modules.workorder.service.WorkOrderStatusType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author wewezhu
 */
public class WorkOrderServiceImpl implements WorkOrderService {

    private static final org.apache.log4j.Logger logger = Logger.getLogger(WorkOrderServiceImpl.class);

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

    @Autowired
    private MemberService memberService;

    @Autowired
    private WorkOrderDAO workOrderDAO;

    public WorkOrderDAO getWorkOrderDAO() {
        return workOrderDAO;
    }

    public void setWorkOrderDAO(WorkOrderDAO workOrderDAO) {
        this.workOrderDAO = workOrderDAO;
    }

    @Override
    @Transactional
    public WorkOrder addWorkOrder(WorkOrder workOrder) throws AssetNotFoundException, InsuffientProductAssetException, InsuffientPackageAssetException{
        // TODO: should add logic for member asset deduction
        Member member = workOrder.getCustomer();
        Set<WorkOrderAssetMapping> assetMappings = workOrder.getAssetMappingSet();
        if(member == null){
            logger.error("addWorkOrder: member is null");
            return null;
        }

        if(assetMappings.size() == 0){
            logger.error("addWorkOrder: asset is null");
            return null;
        }

        boolean assetFound = false;
        for(WorkOrderAssetMapping assetMapping: assetMappings){
            MemberAsset asset = assetMapping.getAsset();
            int assetCount = asset.getCount();
            int deductCount = assetMapping.getCount();

            if(assetCount > deductCount){
                asset.setCount(assetCount - deductCount);
                memberService.updateProductAsset(asset);
            }else{
                throw new InsuffientProductAssetException();
            }
            assetFound = true;
            break;
        }

        if(!assetFound){
            throw new AssetNotFoundException();
        }

        return workOrderDAO.addWorkOrder(workOrder);
    }

    @Override
    public WorkOrder confirmWorkOrder(String workOrderId, int rate, String confirmComment) {
        WorkOrder workOrder = findWorkOrderByWordOrderId(workOrderId);
        if(workOrder != null){
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
    public boolean cancelWorkOrder(WorkOrder workOrder) throws AssetNotFoundException{
        // cancel workorder -> add asset back
        Member member = workOrder.getCustomer();
        Set<WorkOrderAssetMapping> assetMappings = workOrder.getAssetMappingSet();
        if(member == null){
            logger.error("addWorkOrder: member is null");
            return false;
        }

        if(assetMappings.size() > 0){
            logger.error("addWorkOrder: asset is null");
            return false;
        }


        for(WorkOrderAssetMapping assetMapping: assetMappings){
            MemberAsset asset = assetMapping.getAsset();
            int assetCount = asset.getCount();
            int increaseCount = assetMapping.getCount();

            asset.setCount(assetCount + increaseCount);
            memberService.updateProductAsset(asset);
        }

        this.removeWorkOrder(workOrder);
        return true;
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

    @Scheduled(cron = "0 0 23 ? * *") // 每天23:00:00执行该定时计划
    public void expireWorkOrder(){
        Date now = DateUtils.getDateNow();

        List<WorkOrder> workOrders = workOrderDAO.findExpiredWorkOrder(now);

        for (WorkOrder workOrder : workOrders){
            // TODO: Dd we need to return the asset to member?
            workOrder.setStatus(WorkOrderStatusType.WO_EXPIRED.ordinal());
            workOrder.setConfirmDate(now);

            workOrderDAO.updateWorkOrder(workOrder);
        }

    }
}
