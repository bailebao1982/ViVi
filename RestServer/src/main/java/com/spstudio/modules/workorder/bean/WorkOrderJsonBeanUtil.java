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
        workOrderJsonBean.setConfirm_date(wkBean.getConfirmDate().toString());
        workOrderJsonBean.setCreate_date(wkBean.getCreateDate().toString());
        workOrderJsonBean.setService_date(wkBean.getServiceDate().toString());
        workOrderJsonBean.setService_location(wkBean.getServiceLocation());
        workOrderJsonBean.setWorkorder_id(wkBean.getWorkOrderId());
        workOrderJsonBean.setEffective_end_date(wkBean.getEffectiveEndDate().toString());

        workOrderJsonBean.setMember(MemberJsonBeanUtil.toJsonBean(wkBean.getCustomer()));
        workOrderJsonBean.setEmployee(EmployeeJsonBeanUtil.toJsonBean(wkBean.getServiceProvider()));

        List<AssetCountJsonBean> assets = new ArrayList<>();
        for(WorkOrderAssetMapping assetMapping: wkBean.getAssetMappingSet()){
            AssetCountJsonBean assetCountJsonBean = new AssetCountJsonBean();
            assetCountJsonBean.setCount(assetMapping.getCount());
            assetCountJsonBean.setAsset(MemberAssetJsonBeanUtil.toJsonBean(
                    assetMapping.getAsset()
            ));
        }

        workOrderJsonBean.setAssets(assets);

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
