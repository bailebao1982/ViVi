package com.spstudio.modules.product.service.impl;

import com.spstudio.modules.product.dao.ProductTypeDAO;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.product.service.ProductTypeService;

import java.util.List;

/**
 * Created by Soul on 2017/1/1.
 */
public class ProductTypeServiceImpl implements ProductTypeService{
    private ProductTypeDAO productTypeDAO;

    public ProductTypeDAO getProductTypeDAO() {
        return productTypeDAO;
    }

    public void setProductTypeDAO(ProductTypeDAO productTypeDAO) {
        this.productTypeDAO = productTypeDAO;
    }

    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeDAO.getAllProductTypes();
    }

    @Override
    public ProductType findProductTypeByProductTypeId(String productTypeId) {
        return productTypeDAO.findProductTypeByProductTypeId(productTypeId);
    }

    @Override
    public ProductType findProductTypeByProductTypeName(String typeName) {
        return productTypeDAO.findProductTypeByProductTypeName(typeName);
    }

    @Override
    public ProductType addProductType(ProductType productType) {
        return productTypeDAO.addProductType(productType);
    }

    @Override
    public boolean zapProductType(ProductType productType) {
        return productTypeDAO.zapProductType(productType);
    }

    @Override
    public ProductType updateProductType(ProductType productType) {
        return productTypeDAO.updateProductType(productType);
    }
}
