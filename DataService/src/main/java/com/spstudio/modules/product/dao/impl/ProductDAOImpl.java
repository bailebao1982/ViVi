/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.dao.impl;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.product.dao.ProductDAO;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.entity.ProductSet;
import java.util.List;
import java.util.Set;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public Product addProduct(Product product) {
       
     sessionFactory.getCurrentSession().saveOrUpdate(product);
     return product;
    }

    @Override
    public boolean removeProduct(Product product) {
     
        product.setDeleteFlag(1);
        sessionFactory.getCurrentSession().saveOrUpdate(product);
        return true;
    }

    @Override
    public Product updateProduct(Product product) {
        sessionFactory.getCurrentSession().update(product);
        return product;
    }

    @Override
    public List<Product> queryForPage(int offset, int length, SearchCriteria criteria) {
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
    public int getAllRowCount() {
        Long count = (Long)sessionFactory.getCurrentSession().createQuery("select count(1) from Product where deleteFlag = 0").uniqueResult();
        
        return count.intValue();
    }

    @Override
    public void zapProduct(Product product) {
        this.sessionFactory.getCurrentSession().delete(product);
    }

    @Override
    public ProductPackage addProductPackage(ProductPackage productPackage) {
      /**  
        for(ProductSet productSet:productPackage.getProductSets()){
            this.sessionFactory.getCurrentSession().saveOrUpdate(productSet);
        }
        **/
        this.sessionFactory.getCurrentSession().saveOrUpdate(productPackage);
        return productPackage;
    }

    @Override
    public ProductPackage updateProdctPackage(ProductPackage productPackage) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(productPackage);
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
    public void zapProductPackage(ProductPackage productPackage) {
        this.sessionFactory.getCurrentSession().delete(productPackage);
    }
    
    
}
