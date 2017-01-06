package com.spstudio.modules.product.bean.request;

import com.spstudio.modules.product.entity.ProductPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/5.
 */
public class SimplePackageJsonBeanUtil {
    public static SimplePackageJsonBean toJsonBean(ProductPackage pkg) {
        SimplePackageJsonBean jsonBean = new SimplePackageJsonBean();
        jsonBean.setId(pkg.getSerialNo());
        jsonBean.setText(pkg.getPackageName());
        return jsonBean;
    }

    public static List<SimplePackageJsonBean> toJsonBeanList(List<ProductPackage> packages) {
        List<SimplePackageJsonBean> jsonBeanList = new ArrayList<SimplePackageJsonBean>();
        for (ProductPackage pkg : packages){
            jsonBeanList.add(toJsonBean(pkg));
        }
        return jsonBeanList;
    }

}
