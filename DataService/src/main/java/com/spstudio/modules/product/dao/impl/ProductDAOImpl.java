/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.dao.impl;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.product.dao.ProductDAO;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class ProductDAOImpl implements ProductDAO{
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Product> getAllProducts() {
        String hql = "from Product where deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    @Override
    public Product findProductByProductId(String productId) {
        String hql = "from Product where productId = :id and deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", productId);
        List<Product> list = query.list();
        if(list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public Product findProductByProductSerialno(String serialno) {
        String hql = "from Product where serialno = :serialno and deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("serialno", serialno);
        List<Product> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        java.sql.Date now = new java.sql.Date(new Date().getTime());
        product.setCreationDate(now);
        product.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().saveOrUpdate(product);
        return product;
    }

    @Override
    public boolean removeProduct(Product product) {
        product.setDeleteFlag(1);
        java.sql.Date now = new java.sql.Date(new Date().getTime());
        product.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().update(product);
        return true;
    }

    @Override
    public boolean removeProductList(List<String> idList, String updater){
        String hql = "update Product set deleteFlag = 1, lastUpdateDate = :updateDate, lastUpdateBy = :updater where productId in :product_ids";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("updateDate", new java.sql.Date(System.currentTimeMillis()));
        query.setParameter("updater", updater);
        query.setParameterList("product_ids", idList);
        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public Product updateProduct(Product product) {
        java.sql.Date now = new java.sql.Date(new Date().getTime());
        product.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().update(product);
        return product;
    }

    @Override
    public List<Product> queryProductsForPage(int offset, int length, SearchCriteria criteria) {
        List<Product> entitylist = null;
        try{
            StringBuffer queryString = new StringBuffer();
            queryString.append("from Product where deleteFlag = 0");
            
            for(String key:criteria.getItemMap().keySet()){
                queryString.append(" and ");
                 queryString.append(criteria.getItemMap().get(key).getSearchCriteriaItem());  
            }
            
            Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
            query.setFirstResult(offset);
            query.setMaxResults(length);
            entitylist = query.list();
            
        }catch(RuntimeException re){
            throw re;
        }
        return entitylist;
    }

    @Override
    public int getAllProductsCount() {
        Long count = (Long)sessionFactory.getCurrentSession().createQuery("select count(1) from Product where deleteFlag = 0").uniqueResult();
        return count.intValue();
    }

    @Override
    public void zapProduct(Product product) {
        this.sessionFactory.getCurrentSession().delete(product);
    }

    @Override
    public ProductPackage addProductPackage(ProductPackage productPackage) {
        java.sql.Date now = new java.sql.Date(new Date().getTime());
        productPackage.setCreationDate(now);
        productPackage.setLastUpdateDate(now);

        this.sessionFactory.getCurrentSession().saveOrUpdate(productPackage);
        return productPackage;
    }

    @Override
    public boolean removeProductPackage(ProductPackage productPackage) {
        java.sql.Date now = new java.sql.Date(new Date().getTime());
        productPackage.setLastUpdateDate(now);
        productPackage.setDeleteFlag(1);

        this.sessionFactory.getCurrentSession().update(productPackage);
        return true;
    }

    @Override
    public boolean removeProductPackageList(List<String> ids, String user) {
        String hql = "update ProductPackage set deleteFlag = 1, lastUpdateDate = :updateDate, lastUpdateBy = :updater where productPackageId in :ids";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("updateDate", new java.sql.Date(System.currentTimeMillis()));
        query.setParameter("updater", user);
        query.setParameterList("ids", ids);
        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public ProductPackage updateProdctPackage(ProductPackage productPackage) {
        java.sql.Date now = new java.sql.Date(new Date().getTime());
        productPackage.setLastUpdateDate(now);

        this.sessionFactory.getCurrentSession().update(productPackage);
        return productPackage;
    }

    @Override
    public ProductPackage findProductPackageByPackageId(String productPackageId) {
        String hql = "from ProductPackage where productPackageId = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", productPackageId);
        List<ProductPackage> list = query.list();
        if(list.size()>0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public ProductPackage findProductPackageBySerialno(String serialno) {
        String hql = "from ProductPackage where serialno = :serialno";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("serialno", serialno);
        List<ProductPackage> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public List<ProductPackage> getAllPackages() {
        String hql = "from ProductPackage where deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    @Override
    public void zapProductPackage(ProductPackage productPackage) {
        this.sessionFactory.getCurrentSession().delete(productPackage);
    }


    @Override
    public List<ProductPackage> queryPackagesForPage(int offset, int length, SearchCriteria criteria) {
        List<ProductPackage> entitylist = null;
        try{
            StringBuffer queryString = new StringBuffer();
            queryString.append("from ProductPackage where deleteFlag = 0");

            for(String key:criteria.getItemMap().keySet()){
                queryString.append(" and ");
                queryString.append(criteria.getItemMap().get(key).getSearchCriteriaItem());
            }

            Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
            query.setFirstResult(offset);
            query.setMaxResults(length);
            entitylist = query.list();

        }catch(RuntimeException re){
            throw re;
        }
        return entitylist;
    }

    @Override
    public int getAllPackagesCount() {
        Long count = (Long)sessionFactory.getCurrentSession().createQuery("select count(1) from ProductPackage where deleteFlag = 0").uniqueResult();
        return count.intValue();
    }

}
