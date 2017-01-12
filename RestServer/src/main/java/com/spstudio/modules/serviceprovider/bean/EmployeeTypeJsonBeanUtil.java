package com.spstudio.modules.serviceprovider.bean;

import com.spstudio.modules.sp.entity.ServiceProviderType;

/**
 * Created by Soul on 2017/1/12.
 */
public class EmployeeTypeJsonBeanUtil {
    public static EmployeeTypeJsonBean toJsonBean(ServiceProviderType spType){
        EmployeeTypeJsonBean employeeTypeJsonBean = new EmployeeTypeJsonBean();
        employeeTypeJsonBean.setEmployee_type_name(spType.getServiceProviderTypeName());
        employeeTypeJsonBean.setEmployee_type_description(spType.getDescription());
        return employeeTypeJsonBean;
    }

    public static ServiceProviderType toEntityBean(EmployeeTypeJsonBean employeeTypeJsonBean){
        ServiceProviderType spType = new ServiceProviderType();
        spType.setDescription(employeeTypeJsonBean.getEmployee_type_description());
        spType.setServiceProviderTypeName(employeeTypeJsonBean.getEmployee_type_name());
        return spType;
    }
}
