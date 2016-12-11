/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.dao;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.product.entity.Product;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface ProductDAO {
    public List<Product> getAllProducts();
    
    public Product findProductByProductId(String productId);
    
    public Product addProduct(Product product);
    
    public boolean removeProduct(Product product);
    
    public Product updateProduct(Product product);
    
    public List<Product> queryForPage(int offset, int length, SearchCriteria criteria);
    
    public int getAllRowCount();
}
