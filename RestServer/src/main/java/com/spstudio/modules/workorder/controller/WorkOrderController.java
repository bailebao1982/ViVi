package com.spstudio.modules.workorder.controller;

import com.spstudio.common.response.ResponseBean;
import com.spstudio.common.response.ResponseMsgBeanFactory;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchConditionEnum;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.search.SearchCriteriaItem;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.workorder.dto.WorkOrderConfirmJsonBean;
import com.spstudio.modules.workorder.dto.WorkOrderJsonBean;
import com.spstudio.modules.workorder.dto.WorkOrderJsonBeanUtil;
import com.spstudio.modules.workorder.entity.WorkOrder;
import com.spstudio.modules.workorder.exception.AssetNotFoundException;
import com.spstudio.modules.workorder.exception.InsuffientProductAssetException;
import com.spstudio.modules.workorder.service.WorkOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Soul on 2017/2/13.
 */
@RestController
@RequestMapping("/workorder")
public class WorkOrderController {
    @Resource(name="serviceProviderService")
    private ServiceProviderService serviceProviderService;

    @Resource(name="memberService")
    private MemberService memberService;

    @Resource(name="productService")
    private ProductService productService;

    @Resource(name="workOrderService")
    private WorkOrderService workOrderService;

    private final static Map<String, String> errorMap =
            new HashMap<String, String>(){
                {
                    put("7001",  "日期格式错误");
                    put("7002",  "未能找到该工单，工单Id: %s");
                    put("7003",  "工单（%s）取消失败");
                    put("7004",  "用户套餐资产(%s)中无足够的产品(%s)");
                    put("7005",  "用户资产中无足够的产品(%s)");
                    put("7006",  "用户资产中无此类型的资产");
                }
            };

    public ServiceProviderService getServiceProviderService() {
        return serviceProviderService;
    }

    public void setServiceProviderService(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    public MemberService getMemberService() {
        return memberService;
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public WorkOrderService getWorkOrderService() {
        return workOrderService;
    }

    public void setWorkOrderService(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @RequestMapping(value = "/add_workorder",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addWorkOrder(
            @RequestBody WorkOrderJsonBean workOrderJsonBean){
        WorkOrder workOrder = null;
        try {
            workOrder = WorkOrderJsonBeanUtil.toEntityBean(
                    workOrderJsonBean,
                    serviceProviderService,
                    memberService
                    );

            workOrderService.addWorkOrder(workOrder);

            return ResponseMsgBeanFactory.getSuccessResponseBean(
                    "工单创建成功！"
            );
        } catch (ParseException e) {
            // Confirm Date parse error
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "7001",
                    errorMap.get("7001")
            );
        } catch (InsuffientProductAssetException e) {
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "7005",
                    e.getMessage()
            );
        } catch (AssetNotFoundException e) {
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "7006",
                    String.format(errorMap.get("7006"))
            );
        }
    }

    @RequestMapping(value = "/confirm_workorder/{workorder_id}",
            method = RequestMethod.POST,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean confirmWorkOrder(
            @PathVariable String workorder_id,
            @RequestBody WorkOrderConfirmJsonBean confirmJsonBean){
        WorkOrder workOrder =
                workOrderService.confirmWorkOrder(
                        workorder_id,
                        confirmJsonBean.getRate(),
                        confirmJsonBean.getNote()
                );
        if(workOrder == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "7002",
                    String.format(errorMap.get("7002"), workorder_id)
            );
        }else{
            return ResponseMsgBeanFactory.getResponseBean(
                    true,
                    WorkOrderJsonBeanUtil.toJsonBean(workOrder)
            );
        }
    }

    @RequestMapping(value = "/cancel_workorder/{workorder_id}",
            method = RequestMethod.DELETE,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean cancelWorkOrder(@PathVariable String workorder_id){
        WorkOrder workOrder =
                workOrderService.findWorkOrderByWordOrderId(workorder_id);
        if(workOrder == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                "7002",
                String.format(errorMap.get("7002"), workorder_id)
            );
        }

        try {
            boolean result = workOrderService.cancelWorkOrder(workOrder);
            if(result){
                return ResponseMsgBeanFactory.getSuccessResponseBean(
                        "工单取消成功!"
                );
            }else{
                return ResponseMsgBeanFactory.getErrorResponseBean(
                        "7003",
                        String.format(errorMap.get("7003"), workorder_id)
                );
            }
        } catch (AssetNotFoundException e) {
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "7006",
                    String.format(errorMap.get("7006"))
            );
        }
    }

    @RequestMapping(value = "/list_workorders",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody
    ResponseBean listWorkOrders(@RequestParam(value="page", required=true) int page,
                                @RequestParam(value="page_size", required=true) int page_size,
                                @RequestParam(value="employee_id", required=false) String employee_id,
                                @RequestParam(value="member_id", required=false) String member_id,
                                @RequestParam(value="start_date", required=false) String start_date,
                                @RequestParam(value="end_date", required=false) String end_date,
                                @RequestParam(value="confirm_start_date", required=false) String confirm_start_date,
                                @RequestParam(value="confirm_end_date", required=false) String confirm_end_date){
        SearchCriteria sc = new SearchCriteria();

        if(member_id!= null &&
                !member_id.isEmpty())
            sc.addSearchCriterialItem("member_id",
                    new SearchCriteriaItem("member_id", member_id, SearchConditionEnum.Equal)
            );

        if(employee_id!= null &&
                !employee_id.isEmpty())
            sc.addSearchCriterialItem("employee_id",
                    new SearchCriteriaItem("employee_id", employee_id, SearchConditionEnum.Equal)
            );

        if(start_date != null &&
                !start_date.isEmpty())
            sc.addSearchCriterialItem("start_date",
                    new SearchCriteriaItem("serviceDate", start_date, SearchConditionEnum.LargeOrEqual)
            );

        if(end_date!=null &&
                !end_date.isEmpty())
            sc.addSearchCriterialItem("end_date",
                    new SearchCriteriaItem("serviceDate", end_date, SearchConditionEnum.SmallOrEqual)
            );

        if(confirm_start_date != null &&
                !confirm_start_date.isEmpty())
            sc.addSearchCriterialItem("start_date",
                    new SearchCriteriaItem("confirmDate", confirm_start_date, SearchConditionEnum.LargeOrEqual)
            );

        if(confirm_end_date!=null &&
                !confirm_end_date.isEmpty())
            sc.addSearchCriterialItem("end_date",
                    new SearchCriteriaItem("confirmDate", confirm_end_date, SearchConditionEnum.SmallOrEqual)
            );


        Page<WorkOrder> resultPageBean = workOrderService.queryForPage(page, page_size, sc);

        List<WorkOrderJsonBean> workorderListJsonBean = new ArrayList<WorkOrderJsonBean>();
        for (WorkOrder workOrder: resultPageBean.getList()){
             workorderListJsonBean.add(WorkOrderJsonBeanUtil.toJsonBean(workOrder));
        }

        Page<WorkOrderJsonBean> returnPage = new Page<WorkOrderJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        returnPage.setList(workorderListJsonBean);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }
}
