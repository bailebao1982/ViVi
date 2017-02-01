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

import com.spstudio.modules.stock.exceptions.StockNotEnoughException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.omg.CORBA.SetOverrideType;

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
    public Stock increaseStockInventory(Product product, int num) {
       if(product == null) return null;

       Stock stock = this.findStockByProduct(product);
       if(stock == null) {
           stock = new Stock();
           stock.setProduct(product);
           stock.setIsinfinite(false);
           stock.setInventory(num);
           newStock(stock);
           return stock;
        }else{
           if(num > 0){
               stock.setInventory(stock.getInventory() + num);
               this.sessionFactory.getCurrentSession().saveOrUpdate(stock);
           }
           return stock;
       }
    }

    @Override
    public Stock decreaseStockInventory(Product product, int num) throws StockNotEnoughException {
       if(product == null) return null;

       Stock stock = this.findStockByProduct(product);
       if(stock == null){
           // Just create a new stock
           stock = new Stock();
           stock.setProduct(product);
           stock.setIsinfinite(false);
           stock.setInventory(0);
           newStock(stock);
           throw new StockNotEnoughException();
        }else{
           if(this.isStockEnoughForDecrease(product, num)){
                if(stock.isinfinite()){
                    return stock;
                }

               stock.setInventory(stock.getInventory() - num);
               this.sessionFactory.getCurrentSession().saveOrUpdate(stock);
               return stock;
           }else{
               throw new StockNotEnoughException();
           }
       }
    }

    @Override
    public Stock findStockByProduct(Product product) {
        String hql = "from Stock where productId = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", product.getProductId());
        List<Stock> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        else{
            Stock stock = new Stock();
            stock.setProduct(product);
            stock.setIsinfinite(false);
            stock.setInventory(0);
            return newStock(stock);
        }
    }

    @Override
    public boolean isStockEnoughForDecrease(Product product, int num) {
       Stock stock = this.findStockByProduct(product);
       if(stock != null){
           return stock.isinfinite() || stock.getInventory() > num;
        }else{
           stock = new Stock();
           stock.setProduct(product);
           stock.setIsinfinite(false);
           stock.setInventory(0);
           newStock(stock);
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
