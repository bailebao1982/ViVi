/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.service;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.entity.ProductSet;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author wewezhu
 */
public interface ProductService {
    public List<Product> getAllProducts();
    
    public Product findProductByProductId(String productId);
    
    public Product addProduct(Product product);
    
    public boolean removeProduct(Product product);

    public boolean removeProductList(List<String> idList, String user);
    
    public boolean updateProduct(Product product);
    
    public Page<Product> queryForPage(int offset, int length, SearchCriteria criteria);
    
    public int getAllRowCount();
    
    public void zapProduct(Product product);
    
    public ProductPackage addProductPackage(Set<ProductSet> productSet, int unitPrice,String description,String packageName, Date effectiveStartDate, Date effectiveEndDate);
    
    public ProductPackage updateProdctPackage(ProductPackage productPackage);
    
    public ProductPackage findProductPackageByPackageId(String productPackageId);
    
    public void zapProductPackage(ProductPackage productPackage);
}
