package com.spstudio.modules.product.bean.request;

import com.spstudio.modules.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/4.
 */
public class SimpleProductJsonBeanUtil {
    public static SimpleProductJsonBean toJsonBean(Product product) {
        SimpleProductJsonBean jsonBean = new SimpleProductJsonBean();
        String text = String.format("%s:%s", product.getSerialno(), product.getProductName());
        jsonBean.setId(text);
        jsonBean.setText(text);
        return jsonBean;
    }

    public static List<SimpleProductJsonBean> toJsonBeanList(List<Product> products) {
        List<SimpleProductJsonBean> jsonBeanList = new ArrayList<SimpleProductJsonBean>();
        for (Product product : products){
            jsonBeanList.add(toJsonBean(product));
        }
        return jsonBeanList;
    }
}
