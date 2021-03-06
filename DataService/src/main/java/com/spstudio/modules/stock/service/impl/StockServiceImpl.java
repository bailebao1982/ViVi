/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.stock.service.impl;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.product.dao.ProductDAO;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.stock.dao.StockDAO;
import com.spstudio.modules.stock.entity.Stock;
import com.spstudio.modules.stock.exceptions.StockNotEnoughException;
import com.spstudio.modules.stock.service.StockService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public class StockServiceImpl implements StockService{
    private StockDAO stockDAO;

    private ProductDAO productDAO;
    
    public StockDAO getStockDAO() {
        return stockDAO;
    }

    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public Stock addNewStock(Product product, int inventory, boolean isInfinite) {
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setInventory(inventory);
        stock.setIsinfinite(isInfinite);
        return stockDAO.newStock(stock);
    }

    @Override
    public Stock increaseStockNum(Product product, int num) {
        return stockDAO.increaseStockInventory(product, num);
    }

    @Override
    public Stock decreaseStockNum(Product product, int num) {
        Stock stock = null;
        try {
            stock = stockDAO.decreaseStockInventory(product, num);
            if(stock.getInventory() == 0){
                product.setAvailable(false);
                productDAO.updateProduct(product);
            }
        } catch (StockNotEnoughException e) {
            e.printStackTrace();
        }
        return stock;
    }

    @Override
    public boolean isStockEnoughForDecrease(Product product, int num) {
       return stockDAO.isStockEnoughForDecrease(product, num);
    }

    @Override
    public Stock findStockByProduct(Product product) {
        return stockDAO.findStockByProduct(product);
    }

    @Override
    public Stock findStockByProductId(String productId) {
        Product prdt = productDAO.findProductByProductId(productId);
        return findStockByProduct(prdt);
    }

    @Override
    public void zapStock(Stock stock) {
        stockDAO.zapStock(stock);
    }

    @Override
    public Stock updateStock(Stock stock) {
        return stockDAO.updateStock(stock);
    }

    @Override
    public Page<Stock> queryStocksForPage(int currentPage, int pageSize, SearchCriteria criteria) {
        //总记录数
        int allRow = productDAO.getAllProductsCount();

        Page<Stock> page = new Page<Stock>();
        //当前页开始记录
        int offset = page.countOffset(currentPage, pageSize);
        //分页查询结果集
        List<Product> list = productDAO.queryProductsForPage(offset, pageSize,criteria);

        List<Stock> stockList = new ArrayList<Stock>();

        for (Product prdt : list){
            stockList.add(findStockByProduct(prdt));
        }
        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(stockList);

        return page;
    }
}
