/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sales.dao.impl;

import com.spstudio.modules.sales.dao.SaleDAO;
import com.spstudio.modules.sales.entity.Sales;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class SaleDAOImpl implements SaleDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Sales addSalesRecord(Sales sales) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(sales);
        return sales;
    }

    @Override
    public void zapSalesRecord(Sales saleRec) {
        this.sessionFactory.getCurrentSession().delete(saleRec);
    }
    
    
}
