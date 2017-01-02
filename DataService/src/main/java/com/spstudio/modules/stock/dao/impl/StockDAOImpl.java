/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.stock.dao.impl;

import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.stock.dao.StockDAO;
import com.spstudio.modules.stock.entity.Stock;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class StockDAOImpl implements StockDAO{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Stock newStock(Stock stock) {
        this.sessionFactory.getCurrentSession().save(stock);
        return stock;
    }

    @Override
    public Stock inreaseStockInventory(Product product, int num) {
       Stock stock = this.findStockByProduct(product);
       if(stock!=null){
           stock.setInventory(stock.getInventory()+num);
           this.sessionFactory.getCurrentSession().saveOrUpdate(stock);
           return stock;
        }else{
           //TODO add exception
           return null;
       }
    }

    @Override
    public Stock decreaseStockInventory(Product product, int num) {
        Stock stock = this.findStockByProduct(product);
       if(stock!=null){
           if(this.isStockEnoughForDecrease(product, num)){
                stock.setInventory(stock.getInventory()-num);
                this.sessionFactory.getCurrentSession().saveOrUpdate(stock);
                return stock;
           }else{
               //TODO add exception that stock is not enough to delete
               return null;
           }
        }else{
           //TODO add exception
           return null;
       }
    }

    @Override
    public Stock findStockByProduct(Product product) {
        //TODO: add effective date check condition.
         String hql = "from Stock where productId = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", product.getProductId());
        List<Stock> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public boolean isStockEnoughForDecrease(Product product, int num) {
         Stock stock = this.findStockByProduct(product);
       if(stock!=null){
           return stock.getInventory() > num;
        }else{
           //TODO add exception stock not find for this product.
           return false;
       }
    }

    @Override
    public void zapStock(Stock stock) {
        this.sessionFactory.getCurrentSession().delete(stock);
    }

    @Override
    public Stock updateStock(Stock stock) {
       this.sessionFactory.getCurrentSession().saveOrUpdate(stock);
       return stock;
    }
    
    
}
