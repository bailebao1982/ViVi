package com.spstudio.wxserver.modules.sp.controller;

import com.spstudio.common.utils.DateUtils;
import com.spstudio.common.utils.StringUtils;
import com.spstudio.modules.member.dto.MemberJsonBean;
import com.spstudio.modules.member.dto.MemberJsonBeanUtil;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.service.PermissionService;
import com.spstudio.modules.sp.dto.EmployeeJsonBean;
import com.spstudio.modules.sp.dto.EmployeeJsonBeanUtil;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.sp.entity.ServiceProviderType;
import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.workorder.dto.WorkOrderJsonBean;
import com.spstudio.modules.workorder.dto.WorkOrderJsonBeanUtil;
import com.spstudio.modules.workorder.entity.WorkOrder;
import com.spstudio.modules.workorder.service.WorkOrderService;
import com.spstudio.wxserver.common.controller.WxBaseController;
import com.spstudio.wxserver.modules.member.bean.UserRegisterBean;
import com.spstudio.wxserver.modules.sp.controller.bean.Sp2MemberJsonBeanUtil;
import com.sun.javafx.sg.prism.NGShape;
import com.sun.jmx.remote.protocol.iiop.ServerProvider;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/serviceprovider")
public class SpController extends WxBaseController {
//    @Autowired
//    private PermissionService permissionService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    protected WxMpService wxMpService;

    @Autowired
    private WorkOrderService workOrderService;

    private ModelAndView _getChoosePage(MemberJsonBean userinfo){
        ModelAndView mav = new ModelAndView();
        mav.addObject("userinfo", userinfo);
        mav.setViewName("/spside/choose");
        return mav;
    }

    private ModelAndView __getEmployeePage(ServiceProvider serviceProvider){
        ModelAndView mav = new ModelAndView();

        MemberJsonBean userinfo = Sp2MemberJsonBeanUtil.toMemberJsonBean(serviceProvider);
        mav.addObject("userinfo", userinfo);

        mav.setViewName("/spside/main");
        return mav;
    }

    private ModelAndView _getErrorView(String msg){
        //
        // TODO:: MISSING USERINFO HERE
        //
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", msg);
        mav.setViewName("common/error");

        this.logger.error(msg);
        return mav;
    }
    /**
     *  response to view event, return my profile webpage
     */
    @RequestMapping(value = "/entry",
            method = RequestMethod.GET)
    public ModelAndView getOAuth2UserInfo(@RequestParam(value = "code") String code) {
        WxMpOAuth2AccessToken accessToken;
        WxMpUser wxMpUser;

        try {
            accessToken = this.wxMpService.oauth2getAccessToken(code);
            wxMpUser = this.wxMpService.getUserService()
                    .userInfo(accessToken.getOpenId(), "zh_CN");

            ServiceProvider serviceProvider = serviceProviderService.findServiceProviderByWechatId(wxMpUser.getOpenId());
            if(serviceProvider != null){
                // should show my info page
                return __getEmployeePage(serviceProvider);
            } else{
                // should lead him to register page
                MemberJsonBean userinfo = new MemberJsonBean();

                userinfo.setMember_name(wxMpUser.getNickname());
                userinfo.setMember_wechat(wxMpUser.getOpenId());
                userinfo.setMember_sex(wxMpUser.getSex());

                String location = String.format(
                        "%s %s %s",
                        wxMpUser.getCity(),
                        wxMpUser.getProvince(),
                        wxMpUser.getCountry()
                );
                userinfo.setMember_address(location);
                userinfo.setMember_profilepic(wxMpUser.getHeadImgUrl());

                return _getChoosePage(userinfo);
            }
        } catch (WxErrorException e) {
            ModelAndView mav = new ModelAndView();
            String msg = "错误：" + e.getError().toString();
            mav.addObject("message", msg);
            mav.setViewName("common/error");

            this.logger.error(e.getError().toString());
            return mav;
        }
    }

    @RequestMapping(value = "/binding",
            method = RequestMethod.GET)
    public ModelAndView binding(ModelAndView mav,
                                @RequestParam String nickname,
                                @RequestParam String gender,
                                @RequestParam String headImgUrl,
                                @RequestParam String openId,
                                @RequestParam String address){
        MemberJsonBean userinfo = new MemberJsonBean();
        userinfo.setMember_name(nickname);
        userinfo.setMember_sex(gender);
        userinfo.setMember_wechat(openId);
        userinfo.setMember_profilepic(headImgUrl);
        userinfo.setMember_address(address);

        mav.addObject("userinfo", userinfo);

        mav.setViewName("/spside/binding");
        return mav;
    }

    @RequestMapping(value = "/binding",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ModelAndView doBinding(UserRegisterBean registerBean){
        ServiceProvider newServiceProvider = serviceProviderService.findServiceProviderByEmployeeNo(registerBean.getMemberno());
        if(newServiceProvider == null){
            newServiceProvider = serviceProviderService.findServiceProviderByTelphone(registerBean.getMemberno());
            if(newServiceProvider == null){
                String msg = "错误：无法找到要绑定的员工, 员工编号【或者手机号】 " + registerBean.getMemberno();
                return _getErrorView(msg);
            }
        }

        newServiceProvider.setSpWechatId(registerBean.getOpenId());
        newServiceProvider.setProfilePicture(registerBean.getHeaderImgUrl());

        serviceProviderService.updateServiceProvider(newServiceProvider);

//        LoginUser loginUser = permissionService.getLoginUserByMemberId(newServiceProvider.getServiceProviderId());
//        if(loginUser != null) {
//            loginUser.setLoginPassword(registerBean.getPassword());
//            permissionService.updateLoginUser(loginUser);
//        }

        return __getEmployeePage(newServiceProvider);
    }

    @RequestMapping(value = "/regieter",
            method = RequestMethod.GET)
    public ModelAndView getRegieterPage(
                                        ModelAndView mav,
                                        @RequestParam String nickname,
                                        @RequestParam String gender,
                                        @RequestParam String headImgUrl,
                                        @RequestParam String openId,
                                        @RequestParam String address) {

        MemberJsonBean userinfo = new MemberJsonBean();
        userinfo.setMember_name(nickname);
        userinfo.setMember_sex(gender);
        userinfo.setMember_wechat(openId);
        userinfo.setMember_profilepic(headImgUrl);
        userinfo.setMember_address(address);

        mav.addObject("userinfo", userinfo);

        mav.setViewName("/spside/register");
        return mav;
    }

    @RequestMapping(value = "/regieter",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView registerEmployee(UserRegisterBean registerBean) {

        ServiceProvider newServiceProvider = new ServiceProvider();
        newServiceProvider.setProfilePicture(registerBean.getHeaderImgUrl());

        newServiceProvider.setSpWechatId(registerBean.getOpenId());
        newServiceProvider.setSpSex(registerBean.getGender());
        newServiceProvider.setSpName(registerBean.getNickname());

        newServiceProvider.setSpAddress(registerBean.getAddress());


        ServiceProviderType serviceProviderType = serviceProviderService.findServiceProviderTypeByType(registerBean.getType());
        newServiceProvider.setSpType(serviceProviderType);

        newServiceProvider.setCreationDate(DateUtils.getDateNow());

        ServiceProvider addedSp = serviceProviderService.addServiceProvider(newServiceProvider);

        ModelAndView mav = new ModelAndView();
        MemberJsonBean uiserinfo = Sp2MemberJsonBeanUtil.toMemberJsonBean(addedSp);
        mav.addObject("userinfo", uiserinfo);
        return mav;
    }

    @RequestMapping(value = "/myinfo/{employee_id}",
            method = RequestMethod.GET)
    public ModelAndView getMyInfo(@PathVariable String employee_id) {
        ServiceProvider serverProvider = serviceProviderService.findServiceProviderById(employee_id);
        if(serverProvider == null){
            ModelAndView mav = new ModelAndView();
            String msg = "错误，不存在这样的员工信息，employee_id: " + employee_id;
            mav.addObject("message", msg);
            mav.setViewName("common/error");

            this.logger.error(msg);
            return mav;
        }else{
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/spside/myinfo");
            return mav;
        }
    }

    @RequestMapping(value = "/update/{employee_id}",
            method = RequestMethod.POST)
    public ModelAndView updateProfile(@PathVariable String employee_id, @RequestBody UserRegisterBean updateBean){
        ServiceProvider serviceProvider = serviceProviderService.findServiceProviderById(employee_id);
        if(serviceProvider != null){
            serviceProvider.setSpTelphone(updateBean.getTelphone());
            serviceProvider.setSpAddress(updateBean.getAddress());
            serviceProvider.setSpName(updateBean.getNickname());

            MemberJsonBean userinfo =
                    Sp2MemberJsonBeanUtil.toMemberJsonBean(serviceProvider);
            ModelAndView mav = new ModelAndView();
            mav.addObject("userinfo", userinfo);
            mav.setViewName("/spside/myinfo");
            return mav;
        }else{
            String msg = "错误：无法找到该员工，employee_id: " + employee_id;
            return _getErrorView(msg);
        }
    }

    @RequestMapping(value = "/createworkorder/{employee_id}",
            method = RequestMethod.GET)
    public ModelAndView createWorkOrder(@PathVariable String employee_id) {
        ServiceProvider serviceProvider = serviceProviderService.findServiceProviderById(employee_id);
        if(serviceProvider != null){
            MemberJsonBean userinfo =
                    Sp2MemberJsonBeanUtil.toMemberJsonBean(serviceProvider);
            ModelAndView mav = new ModelAndView();
            mav.addObject("userinfo", userinfo);
            mav.setViewName("/spside/newwrkrdr");
            return mav;
        }else{
            String msg = "错误：无法找到该员工，employee_id: " + employee_id;
            return _getErrorView(msg);
        }
    }

    @RequestMapping(value = "/createworkorder",
            method = RequestMethod.POST)
    public ModelAndView doCreateWorkOrder(@PathVariable String employee_id) {
        ModelAndView mav = new ModelAndView();

        return mav;
    }

    @RequestMapping(value = "/myworkorders/{employee_id}",
            method = RequestMethod.GET)
    public ModelAndView getMyWorkOrders(@PathVariable String employee_id) {
        ServiceProvider serviceProvider = serviceProviderService.findServiceProviderById(employee_id);
        if(serviceProvider != null){
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/spside/myorders");

            List<WorkOrder> workOrders =
                    workOrderService.findWorkOrderOfServiceProvider(serviceProvider);

            List<WorkOrderJsonBean> myWorkOrders = new ArrayList<WorkOrderJsonBean>();
            for (WorkOrder workOrder: workOrders){
                WorkOrderJsonBean workOrderJsonBean =
                        WorkOrderJsonBeanUtil.toJsonBean(workOrder);
                myWorkOrders.add(workOrderJsonBean);
            }
            mav.addObject("workorders", myWorkOrders);
            return mav;
        }else{
            String msg = "错误：无法找到该员工，employee_id: " + employee_id;
            return _getErrorView(msg);
        }
    }

    @RequestMapping(value = "/detail/{workorder_id}",
            method = RequestMethod.GET)
    public ModelAndView getWorkOrderDetail(@RequestParam String employee_id, @PathVariable String workorder_id) {
        WorkOrder workOrder = workOrderService.findWorkOrderByWordOrderId(workorder_id);
        if(workOrder == null){
            String msg = "错误：无法找到该工单，workorder_id: " + workorder_id;
            return _getErrorView(msg);
        }

        ServiceProvider serviceProvider = serviceProviderService.findServiceProviderById(employee_id);
        if(serviceProvider != null){
            String msg = "错误：无法找到该员工，employee_id: " + employee_id;
            return _getErrorView(msg);
        }

        WorkOrderJsonBean workOrderJsonBean = WorkOrderJsonBeanUtil.toJsonBean(workOrder);
        MemberJsonBean userinfo = Sp2MemberJsonBeanUtil.toMemberJsonBean(serviceProvider);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/workorder/wrkrdrdetail");
        mav.addObject("workorder", workOrderJsonBean);
        mav.addObject("userinfo", userinfo);
        return mav;
    }

}
