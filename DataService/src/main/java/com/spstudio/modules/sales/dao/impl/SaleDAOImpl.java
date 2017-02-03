/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sales.dao.impl;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.sales.dao.SaleDAO;
import com.spstudio.modules.sales.entity.Sales;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Sales addSalesRecord(Sales saleRec) {
        Date now = new Date(System.currentTimeMillis());
        saleRec.setLastUpdateDate(now);
        saleRec.setSalesDate(now);

        this.sessionFactory.getCurrentSession().saveOrUpdate(saleRec);
        return saleRec;
    }

    @Override
    public void zapSalesRecord(Sales saleRec) {
        this.sessionFactory.getCurrentSession().delete(saleRec);
    }

    @Override
    public Sales findSaleRecordsById(String saleId){
        String hql = "from Sales where saleId = :sale_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("sale_id", saleId);
        List<Sales> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public List<Sales> findSaleRecordsByMember(Member member) {
        String hql = "from Sales where memberId = :member_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("member_id", member.getMemberId());
        List<Sales> list = query.list();
        if(list.size() > 0) return list;
        return null;
    }

    @Override
    public Sales updateSalesRecord(Sales sales) {
        Date now = new Date(System.currentTimeMillis());
        sales.setLastUpdateDate(now);

        this.sessionFactory.getCurrentSession().update(sales);

        return sales;
    }

    @Override
    public Sales removeSalesRecord(Sales sales) {
        Date now = new Date(System.currentTimeMillis());
        sales.setLastUpdateDate(now);
        sales.setDeleteFlag(1);

        this.sessionFactory.getCurrentSession().update(sales);
        return sales;
    }

    @Override
    public boolean removeSalesRecords(List<String> salesIdList) {
        String hql = "update Sales set deleteFlag = 1, lastUpdateDate = :updateDate where saleId in :sale_ids";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("updateDate", new Date(System.currentTimeMillis()));
        query.setParameterList("sale_ids", salesIdList);

        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public boolean zapSalesRecordOfMember(Member member) {
        String hql = "delete from Sales where memberId = :member_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("member_id", member.getMemberId());

        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public List<Sales> queryForPage(int offset, int length, SearchCriteria criteria) {
        List<Sales> entitylist = new ArrayList<Sales>();
        try{
            StringBuilder queryString = new StringBuilder();
            queryString.append("from Sales where deleteFlag = 0");

            for(String key:criteria.getItemMap().keySet()){
                queryString.append(" and ");
                queryString.append(criteria.getItemMap().get(key).getSearchCriteriaItem());
            }

            queryString.append(" order by salesDate desc");

            Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
            query.setFirstResult(offset);
            query.setMaxResults(length);
            entitylist = query.list();
        }catch(RuntimeException ex){
            throw ex;
        }

        return entitylist;
    }

    @Override
    public int getQueryCount(SearchCriteria criteria){
        Integer result = 0;
        try{
            StringBuilder queryString = new StringBuilder();
            queryString.append("select count(1) from Sales where deleteFlag = 0");

            for(String key:criteria.getItemMap().keySet()){
                queryString.append(" and ");
                queryString.append(criteria.getItemMap().get(key).getSearchCriteriaItem());
            }

            Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());

            Long rows = (Long)query.uniqueResult();
            result = rows.intValue();
        }catch(RuntimeException ex){
            throw ex;
        }

        return result;
    }
}
