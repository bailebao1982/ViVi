/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.dao.impl;

import com.vivi.dao.CustomerDAO;
import com.vivi.entity.Customer;
import com.vivi.search.SearchCriteria;
import java.sql.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class CustomerDAOImpl implements CustomerDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public List<Customer> getAllCustomers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Customer findCustomerByCustomerId(String customerId) {
        String hql = "from Customer as customer where customer.id = :id and deleteFlag <>1"; 
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", customerId);
        List<Customer> list = query.list();
        if(list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        customer.setCreationDate(new Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
        return customer;
    }

    @Override
    public boolean removeCustomer(Customer customer) {
        customer.setLastUpdateDate(new Date(System.currentTimeMillis()));
        customer.setDeleteFlag(1);
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
        return true;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        customer.setLastUpdateDate(new Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
        
        return customer;
    }

    @Override
    public List<Customer> queryForPage(int offset, int length,SearchCriteria criteria) {
        List<Customer> entitylist=null;
        try{
            StringBuffer queryString = new StringBuffer();
            queryString.append("from Customer where deleteFlag <>1");
            
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
    
        return 1;
    }
    
}
