/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.stock.service.impl;

import com.vivi.modules.stock.dao.StockDAO;

/**
 *
 * @author wewezhu
 */
public class StockServiceImpl {
    private StockDAO stockDAO;

    public StockDAO getStockDAO() {
        return stockDAO;
    }

    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }
    
    
}
