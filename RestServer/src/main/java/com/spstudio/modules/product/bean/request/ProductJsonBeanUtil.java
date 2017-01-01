package com.spstudio.modules.product.bean.request;

import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.product.service.ProductTypeService;

/**
 * Created by Soul on 2017/1/1.
 */
public class ProductJsonBeanUtil {
    public static ProductJsonBean toJsonBean(Product product) {
        ProductJsonBean jsonBean = new ProductJsonBean();

        jsonBean.setProduct_serialno(product.getSerialno());
        jsonBean.setProduct_uom(product.getUom());
        jsonBean.setProduct_type(product.getType().getTypeName());
        jsonBean.setProduct_price(product.getUnitPrice());
        jsonBean.setProduct_name(product.getProductName());
        jsonBean.setProduct_description(product.getDescription());

        return jsonBean;
    }

    public static Product toEntityBean(ProductJsonBean productJsonBean, ProductTypeService service){
        Product product = new Product();

        product.setUnitPrice(productJsonBean.getProduct_price());
        product.setSerialno(productJsonBean.getProduct_serialno());
        product.setProductName(productJsonBean.getProduct_name());
        product.setUom(productJsonBean.getProduct_uom());
        product.setDescription(productJsonBean.getProduct_description());

        String productType = productJsonBean.getProduct_type();
        if(!productType.isEmpty()){
            ProductType productTypeEO = service.findProductTypeByProductTypeName(productType);
            if(productTypeEO != null)
                product.setType(productTypeEO);
            else
                product.setType(service.findProductTypeByProductTypeName("default"));
        }else {
            product.setType(service.findProductTypeByProductTypeName("default"));
        }

        return product;
    }
}
