package com.spstudio.modules.product.bean.request;

import com.spstudio.modules.common.bean.Select2OptionJsonBean;
import com.spstudio.modules.product.entity.ProductPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/5.
 */
public class SimplePackageJsonBeanUtil {
    public static Select2OptionJsonBean toJsonBean(ProductPackage pkg) {
        Select2OptionJsonBean jsonBean = new Select2OptionJsonBean();
        jsonBean.setId(pkg.getSerialNo());
        jsonBean.setText(pkg.getPackageName());
        return jsonBean;
    }

    public static List<Select2OptionJsonBean> toJsonBeanList(List<ProductPackage> packages) {
        List<Select2OptionJsonBean> jsonBeanList = new ArrayList<Select2OptionJsonBean>();
        for (ProductPackage pkg : packages){
            jsonBeanList.add(toJsonBean(pkg));
        }
        return jsonBeanList;
    }

}
