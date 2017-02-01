package com.spstudio.modules.product.bean.request;

import com.spstudio.modules.common.bean.Select2OptionJsonBean;
import com.spstudio.modules.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/4.
 */
public class SimpleProductJsonBeanUtil {
    public static Select2OptionJsonBean toJsonBean(Product product) {
        Select2OptionJsonBean jsonBean = new Select2OptionJsonBean();
        String text = String.format("%s:%s", product.getSerialno(), product.getProductName());
        jsonBean.setId(text);
        jsonBean.setText(text);
        return jsonBean;
    }

    public static List<Select2OptionJsonBean> toJsonBeanList(List<Product> products) {
        List<Select2OptionJsonBean> jsonBeanList = new ArrayList<Select2OptionJsonBean>();
        for (Product product : products){
            jsonBeanList.add(toJsonBean(product));
        }
        return jsonBeanList;
    }
}
