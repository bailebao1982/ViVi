package com.spstudio.modules.common.bean;

import java.util.List;

/**
 * Created by Soul on 2017/1/24.
 */
public class Select2ResponseJsonBeanUtil {
    public static Select2ResponseJsonBean toJsonBean(int total,
                                                     List<Select2OptionJsonBean> items){

        Select2ResponseJsonBean jsonBean =
                new Select2ResponseJsonBean();

        jsonBean.setTotal_count(total);
        jsonBean.setItems(items);

        return jsonBean;
    }
}
