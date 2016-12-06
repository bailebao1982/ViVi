/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.service.impl;

import com.spstudio.modules.product.dao.ProductDAO;

/**
 *
 * @author wewezhu
 */
public class ProductServiceImpl {
    private ProductDAO productDAO;

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    
    
}
