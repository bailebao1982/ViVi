/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.stock.dao;

import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.stock.entity.Stock;

/**
 *
 * @author wewezhu
 */
public interface StockDAO {
    public Stock newStock(Stock stock);
    
    public Stock inreaseStockInventory(Product product, int num);
    
    public Stock decreaseStockInventory(Product product,int num);
    
    public Stock findStockByProduct(Product product);
    
    public boolean isStockEnoughForDecrease(Product product,int num);
    
    public void zapStock(Stock stock);
    
    public Stock updateStock(Stock stock);
    
    
    
}
