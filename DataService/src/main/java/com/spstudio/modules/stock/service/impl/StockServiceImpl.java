/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.stock.service.impl;

import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.stock.dao.StockDAO;
import com.spstudio.modules.stock.entity.Stock;
import com.spstudio.modules.stock.service.StockService;
import java.util.Date;

/**
 *
 * @author wewezhu
 */
public class StockServiceImpl implements StockService{
    private StockDAO stockDAO;

    public StockDAO getStockDAO() {
        return stockDAO;
    }

    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    @Override
    public Stock addNewStock(Product product, int inventory, Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stock increaseStockNum(Product product, int num) {
        return stockDAO.inreaseStockInventory(product, num);
    }

    @Override
    public Stock decreaseStockNum(Product product, int num) {
       return stockDAO.decreaseStockInventory(product, num);
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
    public void zapStock(Stock stock) {
        stockDAO.zapStock(stock);
    }

    @Override
    public Stock updateStock(Stock stock) {
        return stockDAO.updateStock(stock);
    }
    
    
}
