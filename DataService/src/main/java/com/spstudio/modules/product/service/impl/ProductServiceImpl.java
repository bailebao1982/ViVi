/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.service.impl;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.product.dao.ProductDAO;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.entity.ProductSet;
import com.spstudio.modules.product.service.ProductService;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    public boolean removeProductList(List<String> idList, String user){
        return productDAO.removeProductList(idList, user);
    }

    @Override
    public boolean updateProduct(Product product) {
        productDAO.updateProduct(product);
        return true;
    }

    @Override
    public Page<Product> queryForPage(int currentPage, int pageSize, SearchCriteria criteria) {
        Page<Product> page = new Page<Product>();
        //当前页开始记录
        int offset = page.countOffset(currentPage, pageSize);
        //分页查询结果集
        List<Product> list = productDAO.queryForPage(offset, pageSize,criteria);
        //总记录数
        int allRow = list.size();

        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);

        return page;
    }

    @Override
    public int getAllRowCount() {
        return productDAO.getAllRowCount();
    }

    @Override
    public void zapProduct(Product product) {
         productDAO.zapProduct(product);
    }

    @Override
    public ProductPackage addProductPackage(Set<ProductSet> productSet, int unitPrice, String description, String packageName, Date effectiveStartDate, Date effectiveEndDate) {
        ProductPackage productPackage = new ProductPackage();
        productPackage.setUnitPrice(unitPrice);
        productPackage.setDescription(description);
        productPackage.setEffectiveEndDate(effectiveEndDate);
        productPackage.setEffectiveEndDate(effectiveEndDate);
        productPackage.setPackageName(packageName);
        productPackage.setProductSets(productSet);
       
        
        return productDAO.addProductPackage(productPackage);
        
        
    }

    @Override
    public ProductPackage updateProdctPackage(ProductPackage productPackage) {
        return productDAO.updateProdctPackage(productPackage);
    }

    @Override
    public ProductPackage findProductPackageByPackageId(String productPackageId) {
        return productDAO.findProductPackageByPackageId(productPackageId);
    }

    @Override
    public void zapProductPackage(ProductPackage productPackage) {
         productDAO.zapProductPackage(productPackage);
    }
    
    
}
