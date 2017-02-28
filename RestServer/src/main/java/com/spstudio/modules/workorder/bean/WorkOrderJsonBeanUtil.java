package com.spstudio.modules.workorder.bean;

import com.spstudio.common.utils.DateUtils;
import com.spstudio.common.utils.StringUtils;
import com.spstudio.modules.member.bean.request.MemberAssetJsonBeanUtil;
import com.spstudio.modules.member.bean.request.MemberJsonBeanUtil;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.product.bean.request.PackageJsonBeanUtil;
import com.spstudio.modules.product.bean.request.ProductJsonBeanUtil;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.serviceprovider.bean.EmployeeJsonBeanUtil;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.workorder.entity.WorkOrder;
import com.spstudio.modules.workorder.entity.WorkOrderAssetMapping;

import java.text.ParseException;
import java.util.*;

/**
 * Created by Soul on 2017/2/14.
 */
public class WorkOrderJsonBeanUtil {
    public static WorkOrderJsonBean toJsonBean(WorkOrder wkBean){
        WorkOrderJsonBean workOrderJsonBean =
                new WorkOrderJsonBean();

        workOrderJsonBean.setRate(wkBean.getRate());
        workOrderJsonBean.setConfirm_comment(wkBean.getConfirmComment());
        workOrderJsonBean.setNote(wkBean.getComment());
        workOrderJsonBean.setConfirmed(wkBean.getStatus());
        if(wkBean.getConfirmDate() != null){
            String confirm_date = null;
            try {
                confirm_date = StringUtils.date2Str(wkBean.getConfirmDate());
                workOrderJsonBean.setConfirm_date(confirm_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(wkBean.getCreateDate() != null) {
            workOrderJsonBean.setCreate_date(wkBean.getCreateDate().toString());
        }

        if(wkBean.getServiceDate() != null) {
            workOrderJsonBean.setService_date(wkBean.getServiceDate().toString());
        }

        workOrderJsonBean.setService_location(wkBean.getServiceLocation());
        workOrderJsonBean.setWorkorder_id(wkBean.getWorkOrderId());
        if(wkBean.getEffectiveEndDate() != null){
            workOrderJsonBean.setEffective_end_date(wkBean.getEffectiveEndDate().toString());
        }

        workOrderJsonBean.setMember(MemberJsonBeanUtil.toJsonBean(wkBean.getCustomer()));
        workOrderJsonBean.setEmployee(EmployeeJsonBeanUtil.toJsonBean(wkBean.getServiceProvider()));

        String assetsSummary = "";
        int maxAssetsCount = 0;
        List<AssetCountJsonBean> assets = new ArrayList<>();
        for(WorkOrderAssetMapping assetMapping: wkBean.getAssetMappingSet()){
            AssetCountJsonBean assetCountJsonBean = new AssetCountJsonBean();
            assetCountJsonBean.setCount(assetMapping.getCount());
            assetCountJsonBean.setAsset(MemberAssetJsonBeanUtil.toJsonBean(
                    assetMapping.getAsset()
            ));
            if(maxAssetsCount < 3) {
                assetsSummary += assetMapping.getAsset().getProduct().getProductName();
                maxAssetsCount++;
            }

            assets.add(assetCountJsonBean);
        }

        workOrderJsonBean.setAssets(assets);
        if(wkBean.getAssetMappingSet().size() > maxAssetsCount){
            assetsSummary = assetsSummary + "...";
        }
        workOrderJsonBean.setAssets_summary(assetsSummary);

        return workOrderJsonBean;
    }

    public static WorkOrder toEntityBean(WorkOrderJsonBean jsonBean,
                                         ServiceProviderService spService,
                                         MemberService memberService) throws ParseException {
        WorkOrder workOrderEntity = new WorkOrder();

        workOrderEntity.setStatus(jsonBean.getConfirmed());
        workOrderEntity.setRate(jsonBean.getRate());
        workOrderEntity.setComment(jsonBean.getNote());
        workOrderEntity.setConfirmComment(jsonBean.getConfirm_comment());
        workOrderEntity.setServiceLocation(jsonBean.getService_location());

        if(jsonBean.getConfirm_date() != null &&
           !jsonBean.getConfirm_date().isEmpty()){
            Date confirmDate = StringUtils.str2Date(jsonBean.getConfirm_date());
            workOrderEntity.setConfirmDate(confirmDate);
        }

        if(jsonBean.getService_date() != null &&
                !jsonBean.getService_date().isEmpty()){
            Date serviceDate = StringUtils.str2Date(jsonBean.getService_date());
            workOrderEntity.setServiceDate(serviceDate);
        }

        if(jsonBean.getEffective_end_date() != null &&
           !jsonBean.getEffective_end_date().isEmpty()){
            Date effEndDate = StringUtils.str2Date(jsonBean.getEffective_end_date());
            workOrderEntity.setEffectiveEndDate(effEndDate);
        }

        ServiceProvider serviceProvider =
                spService.findServiceProviderById(jsonBean.getEmployee().getEmployee_id());
        workOrderEntity.setServiceProvider(serviceProvider);

        Member member =
                memberService.findMemberByMemberId(jsonBean.getMember().getMember_id());
        workOrderEntity.setCustomer(member);

        List<AssetCountJsonBean> assetsBeans = jsonBean.getAssets();
        Set<WorkOrderAssetMapping> mappingSet = new HashSet<>();

        for (AssetCountJsonBean assetCountBean: assetsBeans){
            WorkOrderAssetMapping mapping = new WorkOrderAssetMapping();
            mapping.setCount(assetCountBean.getCount());
            mapping.setWorkOrder(workOrderEntity);
            mapping.setAsset(memberService.findAssetById(assetCountBean.getAsset().getAsset_id()));
            mappingSet.add(mapping);
        }

        workOrderEntity.setAssetMappingSet(mappingSet);

        return workOrderEntity;
    }
}
