/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sales.dao;

import com.spstudio.modules.sales.entity.Sales;

/**
 *
 * @author wewezhu
 */
public interface SaleDAO {
    public Sales addSalesRecord(Sales sales);
    
    public void zapSalesRecord(Sales saleRec);
}
