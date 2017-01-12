/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.stock.service;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.stock.entity.Stock;
import com.spstudio.modules.stock.exceptions.StockNotEnoughException;

import java.util.Date;

/**
 *
 * @author wewezhu
 */
public interface StockService {
    public Stock addNewStock(Product product, int inventory, boolean isInfinite);
    
    public Stock increaseStockNum(Product product,int num);
    
    public Stock decreaseStockNum(Product product,int num) throws StockNotEnoughException;
    
    public boolean isStockEnoughForDecrease(Product product,int num);
    
    public Stock findStockByProduct(Product product);

    public Stock findStockByProductId(String productId);
    
    public void zapStock(Stock stock);
    
    public Stock updateStock(Stock stock);

    public Page<Stock> queryStocksForPage(int offset, int length, SearchCriteria criteria);
    
}
