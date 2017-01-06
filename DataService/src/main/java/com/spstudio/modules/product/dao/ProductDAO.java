/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.dao;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface ProductDAO {
    public List<Product> getAllProducts();
    
    public Product findProductByProductId(String productId);

    public Product findProductByProductSerialno(String serialno);
    
    public Product addProduct(Product product);
    
    public boolean removeProduct(Product product);

    public boolean removeProductList(List<String> id, String user);
    
    public Product updateProduct(Product product);
    
    public List<Product> queryProductsForPage(int offset, int length, SearchCriteria criteria);
    
    public int getAllProductsCount();

    public void zapProduct(Product product);

    public List<ProductPackage> getAllPackages();

    public ProductPackage addProductPackage(ProductPackage productPackage);

    public boolean removeProductPackage(ProductPackage productPackage);

    public boolean removeProductPackageList(List<String> ids, String user);
    
    public ProductPackage updateProdctPackage(ProductPackage productPackage);

    public List<ProductPackage> queryPackagesForPage(int offset, int length, SearchCriteria criteria);

    public ProductPackage findProductPackageByPackageId(String productPackageId);

    public ProductPackage findProductPackageBySerialno(String serialno);

    public int getAllPackagesCount();
    
    public void zapProductPackage(ProductPackage productPackage);
     
}
