package com.spstudio.modules.product.bean.request;

import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductType;

/**
 * Created by Soul on 2017/1/1.
 */
public class ProductTypeJsonBeanUtil {
    public static ProductTypeJsonBean toJsonBean(ProductType productType) {
        ProductTypeJsonBean jsonBean = new ProductTypeJsonBean();
        jsonBean.setProduct_type_name(productType.getTypeName());
        jsonBean.setProduct_type_description(productType.getDescription());
        return jsonBean;
    }

    public static ProductType toEntityBean(ProductTypeJsonBean productTypeJsonBean){
        ProductType productType = new ProductType();
        productType.setTypeName(productTypeJsonBean.getProduct_type_name());
        productType.setDescription(productTypeJsonBean.getProduct_type_description());
        return productType;
    }
}
