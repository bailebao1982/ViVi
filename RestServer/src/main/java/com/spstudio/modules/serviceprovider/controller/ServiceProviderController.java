package com.spstudio.modules.serviceprovider.controller;


import com.spstudio.common.response.ResponseBean;
import com.spstudio.common.response.ResponseMsgBeanFactory;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchConditionEnum;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.search.SearchCriteriaItem;
import com.spstudio.modules.common.bean.CommonTypeJsonBean;
import com.spstudio.modules.serviceprovider.bean.*;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.sp.entity.ServiceProviderType;
import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.vender.entity.Vender;
import com.spstudio.modules.vender.service.VenderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/12.
 */
@RestController
@RequestMapping("/employee")
public class ServiceProviderController {
    @Resource(name="serviceProviderService")
    private ServiceProviderService serviceProviderService;

    @Resource(name="venderService")
    private VenderService venderService;

    @RequestMapping(value = "/list_employee_type",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean listEmployeeType(){
        List<ServiceProviderType> lst = serviceProviderService.listServiceProviderTypes();
        List<CommonTypeJsonBean> resList = EmployeeTypeJsonBeanUtil.toJsonBeanList(lst);
        return ResponseMsgBeanFactory.getResponseBean(
                true,
                resList
        );
    }

    @RequestMapping(value = "/add_employee_type",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addEmployeeType(@RequestBody CommonTypeJsonBean employeeTypeJsonBean){
        ServiceProviderType spType = EmployeeTypeJsonBeanUtil.toEntityBean(employeeTypeJsonBean);
        serviceProviderService.addServiceProviderType(spType);
        return ResponseMsgBeanFactory.getSuccessResponseBean("添加员工类型成功");
    }

    @RequestMapping(value = "/del_employee_type/{employee_type_id}",
            method = RequestMethod.DELETE)
    @CrossOrigin
    public @ResponseBody ResponseBean delEmployeeType(@PathVariable String employee_type_id){
        ServiceProviderType spType = serviceProviderService.findServiceProviderTypeById(employee_type_id);
        if(spType == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "3001",
                    "未发现该员工类别, 类型id: " + employee_type_id
            );
        }else{
            serviceProviderService.removeServiceProviderType(spType);
            return ResponseMsgBeanFactory.getSuccessResponseBean("删除员工类型成功");
        }
    }

    @RequestMapping(value = "/list_employees",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean listEmployees(@RequestParam(value="page", required=true) int page,
                                                    @RequestParam(value="page_size", required=true) int page_size,
                                                    @RequestParam(value="employee_no", required=false) String employee_no,
                                                    @RequestParam(value="name", required=false) String name,
                                                    @RequestParam(value="type", required=false) String type){
        SearchCriteria sc = new SearchCriteria();
        if(employee_no != null &&
           !employee_no.isEmpty()){
            sc.addSearchCriterialItem("spWorkNo",
                    new SearchCriteriaItem("spWorkNo", employee_no, SearchConditionEnum.Equal)
            );
        }

        if(name != null &&
           !name.isEmpty()){
            sc.addSearchCriterialItem("spName",
                    new SearchCriteriaItem("spName", name, SearchConditionEnum.Like)
            );
        }

        if(type != null &&
           !type.isEmpty()){
            ServiceProviderType spType = serviceProviderService.findServiceProviderTypeByType(type);
            sc.addSearchCriterialItem("type",
                    new SearchCriteriaItem("serviceProviderTypeId", spType.getServiceProviderTypeId(), SearchConditionEnum.Equal)
            );
        }

        Page<ServiceProvider> resultPageBean = serviceProviderService.queryForPage(page, page_size, sc);
        Page<EmployeeJsonBean> returnPage = new Page<EmployeeJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        List<EmployeeJsonBean> returnArray = new ArrayList<EmployeeJsonBean>();
        for (ServiceProvider sp : resultPageBean.getList()){
            returnArray.add(EmployeeJsonBeanUtil.toJsonBean(sp));
        }
        returnPage.setList(returnArray);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }

    @RequestMapping(value = "/add_employee",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addEmployee(@RequestBody EmployeeJsonBean employeeJsonBean){
        final String defaultVenderNo = "viva";

        Vender vivaVender = venderService.findVenderByNo(defaultVenderNo); // hard code to viva as currently we only have one vender
        if(vivaVender == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "3002",
                    "无默认公司, 公司NO.: " + defaultVenderNo
            );
        }else{
            ServiceProvider sp =
                    EmployeeJsonBeanUtil.toEntityBean(
                        employeeJsonBean,
                        vivaVender,
                        serviceProviderService);
            if(sp == null){
                return ResponseMsgBeanFactory.getErrorResponseBean(
                        "3003",
                        "员工数据类型转换出错"
                );
            }else{
                serviceProviderService.addServiceProvider(sp);
                return ResponseMsgBeanFactory.getSuccessResponseBean("添加员工成功!");
            }
        }
    }

    @RequestMapping(value = "/del_employee/{employee_id}",
            method = RequestMethod.DELETE)
    @CrossOrigin
    public @ResponseBody ResponseBean delEmployee(@PathVariable String employee_id){
        ServiceProvider sp = serviceProviderService.findServiceProviderById(employee_id);
        if(sp != null){
            serviceProviderService.removeServiceProvider(sp);
            return ResponseMsgBeanFactory.getSuccessResponseBean("员工删除成功!");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "3004",
                    "未能找到该员工，员工id:" + employee_id
            );
        }
    }

    @RequestMapping(value = "/batdel_employee",
            method = RequestMethod.POST,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean batDelEmployee(@RequestBody EmployeeIdListBean employeeIdList){
        boolean result = serviceProviderService.removeServiceProviderInList(employeeIdList.getEmployee_id_list());
        if(result){
            return ResponseMsgBeanFactory.getSuccessResponseBean("员工列表删除成功!");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "3006",
                    "员工列表删除失败"
            );
        }
    }

    @RequestMapping(value = "/update_employee/{employee_id}",
            method = RequestMethod.POST,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean updateEmployee(@PathVariable String employee_id, @RequestBody EmployeeJsonBean employeeJsonBean){
        ServiceProvider sp = serviceProviderService.findServiceProviderById(employee_id);
        if(sp != null){
            final String defaultVenderNo = "viva";

            Vender vivaVender = venderService.findVenderByNo(defaultVenderNo); // hard code to viva as currently we only have one vender
            if(vivaVender == null){
                return ResponseMsgBeanFactory.getErrorResponseBean(
                        "3002",
                        "无默认公司, 公司NO.: " + defaultVenderNo
                );
            }else{
                ServiceProvider newSP = EmployeeJsonBeanUtil.toEntityBean(
                        employeeJsonBean,
                        vivaVender,
                        serviceProviderService);

                if(newSP.getSpType() != null)
                    sp.setSpType(newSP.getSpType());
                if(newSP.getSpWorkNo() != null &&
                        !newSP.getSpWorkNo().isEmpty())
                    sp.setSpWorkNo(newSP.getSpWorkNo());
                if(newSP.getSpTelphone() != null &&
                        !newSP.getSpTelphone().isEmpty())
                    sp.setSpTelphone(newSP.getSpTelphone());
                if(newSP.getSpAddress() != null &&
                        !newSP.getSpAddress().isEmpty())
                    sp.setSpAddress(newSP.getSpAddress());
                if(sp.getSpBirthDay() != null)
                    sp.setSpBirthDay(newSP.getSpBirthDay());
                if(newSP.getSpName() != null &&
                        !newSP.getSpName().isEmpty())
                    sp.setSpName(newSP.getSpName());
                if(newSP.getSpNote() != null &&
                        !newSP.getSpNote().isEmpty())
                    sp.setSpNote(newSP.getSpNote());
                if(newSP.getSpSex() != null &&
                        !newSP.getSpSex().isEmpty())
                    sp.setSpSex(newSP.getSpSex());

                serviceProviderService.updateServiceProvider(sp);
                return ResponseMsgBeanFactory.getSuccessResponseBean("员工更新成功");
            }
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "3004",
                    "未能找到该员工，员工id:" + employee_id
            );
        }
    }
}
