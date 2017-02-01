package com.spstudio.modules.sales.dao.impl;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.utils.DateUtils;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.sales.dao.SaleDiscountDAO;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.sales.entity.SaleDiscount;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/19.
 */
public class SaleDiscountDAOImpl implements SaleDiscountDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private SaleDiscount _createDiscount(Product product, ProductType productType, MemberType type, float discountVal){
        SaleDiscount discount = new SaleDiscount();
        discount.setProduct(product);
        discount.setMemberType(type);
        discount.setDiscountValue(discountVal);
        discount.setProductType(productType);

        discount.setCreationDate(DateUtils.getDateNow());
        discount.setLastUpdateDate(DateUtils.getDateNow());

        sessionFactory.getCurrentSession().save(discount);

        return discount;
    }

    @Override
    public SaleDiscount getDiscountByProduct(String discountId){
        String hql = "from SaleDiscount where discountId = :discount_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("discount_id", discountId);
        List<SaleDiscount> discounts = query.list();
        if(discounts.size() > 0){
            return discounts.get(0);
        }
        return null;
    }

    @Override
    public SaleDiscount getDiscountByProduct(Product product, MemberType type) {
        String hql = "from SaleDiscount where productId = :product_id and memberTypeId = :member_type_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("product_id", product.getProductId());
        query.setParameter("member_type_id", type.getMemberTypeId());
        List<SaleDiscount> discounts = query.list();
        if(discounts.size() > 0){
            return discounts.get(0);
        }
        return null;
    }

    @Override
    public SaleDiscount getDiscountByProductType(ProductType productType, MemberType type) {
        String hql = "from SaleDiscount where productTypeId = :product_type_id and memberTypeId = :member_type_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("product_type_id", productType.getProductTypeId());
        query.setParameter("member_type_id", type.getMemberTypeId());
        List<SaleDiscount> discounts = query.list();
        if(discounts.size() > 0){
            return discounts.get(0);
        }
        return null;
    }

    @Override
    public void updateDiscountByProduct(SaleDiscount discount) {
        sessionFactory.getCurrentSession().update(discount);
    }

    @Override
    public boolean zapDiscount(SaleDiscount discount) {
        sessionFactory.getCurrentSession().delete(discount);
        return true;
    }

    @Override
    public boolean zapDiscountsByProduct(Product product) {
        String hql = "delete from SaleDiscount where productId = :product_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("product_id", product.getProductId());

        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public boolean zapDiscountsByMemberType(MemberType type){
        String hql = "delete from SaleDiscount where memberTypeId = :type_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("type_id", type.getMemberTypeId());

        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public boolean zapDiscountsByProductType(ProductType productType) {
        String hql = "delete from SaleDiscount where productTypeId = :product_type_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("product_type_id", productType.getProductTypeId());

        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public SaleDiscount createDiscount(Product product, MemberType type, float discount) {
        return _createDiscount(product, null, type, discount);
    }

    @Override
    public SaleDiscount createDiscount(ProductType productType, MemberType type, float discount) {
        return _createDiscount(null, productType, type, discount);
    }

    @Override
    public int getQueryCount(SearchCriteria criteria) {
        Integer result = 0;
        try{
            StringBuilder queryString = new StringBuilder();
            queryString.append("select count(1) from SaleDiscount");

            for(String key:criteria.getItemMap().keySet()){
                queryString.append(" and ");
                queryString.append(criteria.getItemMap().get(key).getSearchCriteriaItem());
            }

            Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());

            result = (Integer)query.uniqueResult();
        }catch(RuntimeException ex){
            throw ex;
        }

        return result;
    }

    @Override
    public List<SaleDiscount> queryForPage(int offset, int length, SearchCriteria criteria) {
        List<SaleDiscount> entitylist = new ArrayList<SaleDiscount>();
        try{
            StringBuilder queryString = new StringBuilder();
            queryString.append("from SaleDiscount where 1 = 1");

            for(String key:criteria.getItemMap().keySet()){
                queryString.append(" and ");
                queryString.append(criteria.getItemMap().get(key).getSearchCriteriaItem());
            }

            queryString.append(" order by creationDate");

            Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
            query.setFirstResult(offset);
            query.setMaxResults(length);
            entitylist = query.list();
        }catch(RuntimeException ex){
            throw ex;
        }

        return entitylist;
    }
}
