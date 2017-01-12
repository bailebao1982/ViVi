/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.service;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import java.util.List;


/**
 *
 * @author wewezhu
 */
public interface ProductService {
    public List<Product> getAllProducts();
    
    public Product findProductByProductId(String productId);

    public Product findProductByProductSerialno(String serialno);
    
    public Product addProduct(Product product);
    
    public boolean removeProduct(Product product);

    public boolean removeProductList(List<String> idList, String user);
    
    public Product updateProduct(Product product);
    
    public Page<Product> queryProductsForPage(int offset, int length, SearchCriteria criteria);
    
    public int getAllRowCount();
    
    public void zapProduct(Product product);

    public List<ProductPackage> getAllPackages();

    public ProductPackage addProductPackage(ProductPackage pkg);
    
    public ProductPackage updateProdctPackage(ProductPackage productPackage);

    public boolean removePackage(ProductPackage productPackage);

    public boolean removePackageList(List<String> idList, String user);

    public ProductPackage findProductPackageByPackageId(String productPackageId);

    public ProductPackage findProductPackageByPackageSerialno(String serialno);

    public Page<ProductPackage> queryPackagesForPage(int offset, int length, SearchCriteria criteria);

    public void zapProductPackage(ProductPackage productPackage);
    
    public Product onStoreShelf(Product prodct);
    
    public Product takeOffStoreShelf(Product product);
}
