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
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.stock.dao.StockDAO;
import com.spstudio.modules.stock.entity.Stock;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public class ProductServiceImpl implements ProductService{
    private ProductDAO productDAO;
    
    private StockDAO stockDAO;

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public StockDAO getStockDAO() {
        return stockDAO;
    }

    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
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
    public Product findProductByProductSerialno(String serialno) {
        return productDAO.findProductByProductSerialno(serialno);
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
    public Product updateProduct(Product product) {
        productDAO.updateProduct(product);
        return product;
    }

    @Override
    public Page<Product> queryProductsForPage(int currentPage, int pageSize, SearchCriteria criteria) {
        //总记录数
        int allRow = productDAO.getAllProductsCount();

        Page<Product> page = new Page<Product>();
        //当前页开始记录
        int offset = page.countOffset(currentPage, pageSize);
        //分页查询结果集
        List<Product> list = productDAO.queryProductsForPage(offset, pageSize,criteria);

        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);

        return page;
    }

    @Override
    public int getAllRowCount() {
        return productDAO.getAllProductsCount();
    }

    @Override
    public void zapProduct(Product product) {
         productDAO.zapProduct(product);
    }

    @Override
    public List<ProductPackage> getAllPackages() {
        return productDAO.getAllPackages();
    }

    @Override
    public ProductPackage addProductPackage(ProductPackage pkg) {
        return productDAO.addProductPackage(pkg);
    }

    @Override
    public ProductPackage updateProdctPackage(ProductPackage productPackage) {
        return productDAO.updateProdctPackage(productPackage);
    }

    @Override
    public boolean removePackage(ProductPackage productPackage) {
        return productDAO.removeProductPackage(productPackage);
    }

    @Override
    public boolean removePackageList(List<String> idList, String user) {
        return productDAO.removeProductPackageList(idList, user);
    }

    @Override
    public ProductPackage findProductPackageByPackageId(String productPackageId) {
        return productDAO.findProductPackageByPackageId(productPackageId);
    }

    @Override
    public ProductPackage findProductPackageByPackageSerialno(String serialno) {
        return productDAO.findProductPackageBySerialno(serialno);
    }

    @Override
    public Page<ProductPackage> queryPackagesForPage(int currentPage, int pageSize, SearchCriteria criteria) {
        Page<ProductPackage> page = new Page<ProductPackage>();
        //当前页开始记录
        int offset = page.countOffset(currentPage, pageSize);
        //分页查询结果集
        List<ProductPackage> list = productDAO.queryPackagesForPage(offset, pageSize, criteria);
        //总记录数
        int allRow = list.size();

        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);

        return page;
    }

    @Override
    public void zapProductPackage(ProductPackage productPackage) {
         productDAO.zapProductPackage(productPackage);
    }

    @Override
    public Product onStoreShelf(Product product) {
        Stock stock = stockDAO.findStockByProduct(product);
        if(stock!= null){
            if(stock.getInventory()>0){
                product.setAvailable(true);
                return this.updateProduct(product);
               
            }else{
                //TODO add exception 
                return product;
            }
        }else{
            //TODO add exception here
            return product;
        }
    }

    @Override
    public Product takeOffStoreShelf(Product product) {
        Stock stock = stockDAO.findStockByProduct(product);
         if(stock!= null){
             product.setAvailable(false);
             return this.updateProduct(product);
             
         }else{
            //TODO add exception here
            return product;
        }
    }

}
