package com.spstudio.wxserver.modules.workorder.controller;

import com.spstudio.modules.member.dto.*;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.workorder.dto.WorkOrderJsonBean;
import com.spstudio.modules.workorder.dto.WorkOrderJsonBeanUtil;
import com.spstudio.modules.workorder.entity.WorkOrder;
import com.spstudio.modules.workorder.service.WorkOrderService;
import com.spstudio.wxserver.modules.member.bean.RateBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/3/8.
 */
@Controller
@RequestMapping("/workorder")
public class WorkOrderController {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private WorkOrderService workOrderService;


    private ModelAndView _getErrorView(String msg){
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", msg);
        mav.setViewName("common/error");

        this.logger.error(msg);
        return mav;
    }

    @RequestMapping(value = "/myworkorder/{member_id}",
            method = RequestMethod.GET)
    public ModelAndView getMyWorkOrders(@PathVariable String member_id) {
        Member member = memberService.findMemberByMemberId(member_id);
        if(member != null){
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/workorder/workorders");

            List<WorkOrder> workOrders =
                    workOrderService.findWorkOrderOfMember(member);

            List<WorkOrderJsonBean> myWorkOrders = new ArrayList<WorkOrderJsonBean>();
            for (WorkOrder workOrder: workOrders){
                WorkOrderJsonBean workOrderJsonBean =
                        WorkOrderJsonBeanUtil.toJsonBean(workOrder);
                myWorkOrders.add(workOrderJsonBean);
            }
            mav.addObject("workorders", myWorkOrders);
            return mav;
        }else{
            String msg = "错误：无法找到该会员，member_id: " + member_id;
            return _getErrorView(msg);
        }
    }

    @RequestMapping(value = "/detail/{workorder_id}",
            method = RequestMethod.GET)
    public ModelAndView getWorkOrderDetail(@RequestParam String member_id, @PathVariable String workorder_id) {
        WorkOrder workOrder = workOrderService.findWorkOrderByWordOrderId(workorder_id);
        if(workOrder == null){
            String msg = "错误：无法找到该工单，workorder_id: " + workorder_id;
            return _getErrorView(msg);
        }

        Member member = memberService.findMemberByMemberId(member_id);
        if(member == null){
            String msg = "错误：无法找到该会员，member_id: " + member_id;
            return _getErrorView(msg);
        }

        WorkOrderJsonBean workOrderJsonBean = WorkOrderJsonBeanUtil.toJsonBean(workOrder);
        MemberJsonBean userinfo = MemberJsonBeanUtil.toJsonBean(member);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/workorder/wrkrdrdetail");
        mav.addObject("workorder", workOrderJsonBean);
        mav.addObject("userinfo", userinfo);
        return mav;
    }

    @RequestMapping(value = "/confirm/{workorder_id}",
            method = RequestMethod.POST)
    public ModelAndView confirmWorkOrder(@RequestParam String member_id, @PathVariable String workorder_id) {
        WorkOrder workOrder = workOrderService.confirmWorkOrder(
                workorder_id,
                5,
                "");

        if(workOrder == null){
            String msg = "错误：无法找到该工单，workorder_id: " + workorder_id;
            return _getErrorView(msg);
        }

        Member member = memberService.findMemberByMemberId(member_id);
        if(member == null){
            String msg = "错误：无法找到该会员，member_id: " + member_id;
            return _getErrorView(msg);
        }

        WorkOrderJsonBean workOrderJsonBean = WorkOrderJsonBeanUtil.toJsonBean(workOrder);
        MemberJsonBean userinfo = MemberJsonBeanUtil.toJsonBean(member);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/workorder/wrkrdrrate");
        mav.addObject("userinfo", userinfo);
        mav.addObject("workorder", workOrderJsonBean);
        return mav;
    }

    @RequestMapping(value = "/rate/{workorder_id}",
            method = RequestMethod.POST)
    public ModelAndView rateWorkOrder(@PathVariable String workorder_id, @RequestBody RateBean rateBean) {
        int rate = Integer.valueOf(rateBean.getRate_input());

        WorkOrder workOrder = workOrderService.confirmWorkOrder(
                workorder_id,
                rate,
                rateBean.getRate_comment());

        if(workOrder == null){
            String msg = "错误：无法找到该工单，workorder_id: " + workorder_id;
            return _getErrorView(msg);
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "工单评分成功！");
        mav.setViewName("/common/success");

        return mav;
    }
}
