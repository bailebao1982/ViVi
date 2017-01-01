package com.spstudio.modules.product.service;

import com.spstudio.modules.product.entity.ProductType;

import java.util.List;

/**
 * Created by Soul on 2017/1/1.
 */
public interface ProductTypeService {
    public List<ProductType> getAllProductTypes();

    public ProductType findProductTypeByProductTypeId(String productTypeId);

    public ProductType findProductTypeByProductTypeName(String typeName);

    public ProductType addProductType(ProductType productType);

    public boolean zapProductType(ProductType productType);

    public ProductType updateProductType(ProductType productType);
}
