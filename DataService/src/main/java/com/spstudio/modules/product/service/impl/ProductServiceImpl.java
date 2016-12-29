/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.service.impl;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.product.dao.ProductDAO;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.service.ProductService;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public class ProductServiceImpl implements ProductService{
    private ProductDAO productDAO;

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public Product findProductByProductId(String productId) {
       return productDAO.findProductByProductId(productId);
    }

    @Override
    public Product addProduct(Product product) {
        return productDAO.addProduct(product);
    }

    @Override
    public boolean removeProduct(Product product) {
        return productDAO.removeProduct(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    @Override
    public List<Product> queryForPage(int offset, int length, SearchCriteria criteria) {
        return productDAO.queryForPage(offset, length, criteria);
    }

    @Override
    public int getAllRowCount() {
        return productDAO.getAllRowCount();
    }

    @Override
    public void zapProduct(Product product) {
         productDAO.zapProduct(product);
    }
    
    
}
