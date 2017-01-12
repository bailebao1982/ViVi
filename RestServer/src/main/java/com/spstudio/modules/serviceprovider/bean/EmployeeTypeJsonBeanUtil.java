package com.spstudio.modules.serviceprovider.bean;

import com.spstudio.modules.common.bean.CommonTypeJsonBean;
import com.spstudio.modules.sp.entity.ServiceProviderType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/12.
 */
public class EmployeeTypeJsonBeanUtil {
    public static CommonTypeJsonBean toJsonBean(ServiceProviderType spType){
        CommonTypeJsonBean employeeTypeJsonBean = new CommonTypeJsonBean();
        employeeTypeJsonBean.setTypeName(spType.getServiceProviderTypeName());
        employeeTypeJsonBean.setDescription(spType.getDescription());
        return employeeTypeJsonBean;
    }

    public static List<CommonTypeJsonBean> toJsonBeanList(List<ServiceProviderType> spTypeList){
        List<CommonTypeJsonBean> empTypeList = new ArrayList<CommonTypeJsonBean>();
        for (ServiceProviderType spType : spTypeList){
            empTypeList.add(toJsonBean(spType));
        }
        return empTypeList;
    }

    public static ServiceProviderType toEntityBean(CommonTypeJsonBean employeeTypeJsonBean){
        ServiceProviderType spType = new ServiceProviderType();
        spType.setDescription(employeeTypeJsonBean.getDescription());
        spType.setServiceProviderTypeName(employeeTypeJsonBean.getTypeName());
        return spType;
    }
}
