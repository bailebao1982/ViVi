/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.dao;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.entity.ProductSet;
import java.util.List;
import java.util.Set;

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
    
    public void zapProduct(Product product);
    
    public ProductPackage addProductPackage(ProductPackage productPackage);
    
    public ProductPackage updateProdctPackage(ProductPackage productPackage);
    
    public ProductPackage findProductPackageByPackageId(String productPackageId);
    
    public void zapProductPackage(ProductPackage productPackage);
     
}
