package com.spstudio.modules.serviceprovider.bean;

import com.spstudio.modules.common.bean.Select2OptionJsonBean;
import com.spstudio.modules.sp.entity.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/2/19.
 */
public class SimpleEmployeeJsonBeanUtil {
    public static Select2OptionJsonBean toJsonBean(ServiceProvider sp) {
        Select2OptionJsonBean jsonBean = new Select2OptionJsonBean();
        jsonBean.setId(sp.getServiceProviderId());
        jsonBean.setText(sp.getSpName());
        return jsonBean;
    }

    public static List<Select2OptionJsonBean> toJsonBeanList(List<ServiceProvider> spList) {
        List<Select2OptionJsonBean> jsonBeanList = new ArrayList<Select2OptionJsonBean>();
        for (ServiceProvider sp : spList){
            jsonBeanList.add(toJsonBean(sp));
        }
        return jsonBeanList;
    }
}
