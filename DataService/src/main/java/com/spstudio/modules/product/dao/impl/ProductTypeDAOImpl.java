package com.spstudio.modules.product.dao.impl;

import com.spstudio.modules.product.dao.ProductTypeDAO;
import com.spstudio.modules.product.entity.ProductType;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.util.List;

/**
 * Created by Soul on 2017/1/1.
 */
public class ProductTypeDAOImpl implements ProductTypeDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ProductType> getAllProductTypes() {
        String hql = "from ProductType as producttype";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<ProductType> list = query.list();
        return list;
    }

    @Override
    public ProductType findProductTypeByProductTypeId(String productTypeId) {
        String hql = "from ProductType as producttype where producttype.productTypeId = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", productTypeId);
        List<ProductType> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public ProductType findProductTypeByProductTypeName(String typeName) {
        String hql = "from ProductType as producttype where producttype.typeName = :typename";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("typename", typeName);
        List<ProductType> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public ProductType addProductType(ProductType productType) {
        sessionFactory.getCurrentSession().save(productType);
        return productType;
    }

    @Override
    public ProductType updateProductType(ProductType productType) {
        productType.setLastUpdateDate(new Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().update(productType);
        return productType;
    }

    @Override
    public boolean zapProductType(ProductType productType) {
        sessionFactory.getCurrentSession().delete(productType);
        return true;
    }
}
