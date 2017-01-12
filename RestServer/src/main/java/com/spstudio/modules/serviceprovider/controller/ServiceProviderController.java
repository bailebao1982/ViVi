package com.spstudio.modules.serviceprovider.controller;


import com.spstudio.common.response.ResponseBean;
import com.spstudio.common.response.ResponseMsgBeanFactory;
import com.spstudio.modules.serviceprovider.bean.EmployeeIdListBean;
import com.spstudio.modules.serviceprovider.bean.EmployeeJsonBean;
import com.spstudio.modules.serviceprovider.bean.EmployeeTypeJsonBean;
import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.vender.entity.Vender;
import com.spstudio.modules.vender.service.VenderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
        return ResponseMsgBeanFactory.getErrorResponseBean(
                "3300",
                "尚未实现该操作"
        );
    }

    @RequestMapping(value = "/add_employee_type",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addEmployeeType(EmployeeTypeJsonBean employeeTypeJsonBean){
        return ResponseMsgBeanFactory.getErrorResponseBean(
                "3300",
                "尚未实现该操作"
        );
    }

    @RequestMapping(value = "/del_employee_type/{employee_type_id}",
            method = RequestMethod.DELETE)
    @CrossOrigin
    public @ResponseBody ResponseBean delEmployeeType(@PathVariable String employee_type_id){
        return ResponseMsgBeanFactory.getErrorResponseBean(
                "3300",
                "尚未实现该操作"
        );
    }

    @RequestMapping(value = "/list_employees",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean listEmployees(){
        return ResponseMsgBeanFactory.getErrorResponseBean(
                "3300",
                "尚未实现该操作"
        );
    }

    @RequestMapping(value = "/add_employee",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addEmployeeType(EmployeeJsonBean employeeJsonBean){
        return ResponseMsgBeanFactory.getErrorResponseBean(
                "3300",
                "尚未实现该操作"
        );
    }

    @RequestMapping(value = "/del_employee/{employee_id}",
            method = RequestMethod.DELETE)
    @CrossOrigin
    public @ResponseBody ResponseBean delEmployee(@PathVariable String employee_id){
        return ResponseMsgBeanFactory.getErrorResponseBean(
                "3300",
                "尚未实现该操作"
        );
    }

    @RequestMapping(value = "/batdel_employee",
            method = RequestMethod.POST,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean batDelEmployee(EmployeeIdListBean employeeIdList){
        return ResponseMsgBeanFactory.getErrorResponseBean(
                "3300",
                "尚未实现该操作"
        );
    }

    @RequestMapping(value = "/update_employee/{employee_id}",
            method = RequestMethod.POST,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean updateEmployee(@PathVariable String employee_id, EmployeeJsonBean employeeJsonBean){
        return ResponseMsgBeanFactory.getErrorResponseBean(
                "3300",
                "尚未实现该操作"
        );
    }
}
