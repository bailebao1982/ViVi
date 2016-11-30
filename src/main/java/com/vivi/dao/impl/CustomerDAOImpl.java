/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.dao.impl;

import com.vivi.dao.CustomerDAO;
import com.vivi.entity.Customer;
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
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
        return customer;
    }

    @Override
    public boolean removeCustomer(Customer customer) {
        customer.setDeleteFlag(1);
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
        return true;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
        
        return customer;
    }
    
}
