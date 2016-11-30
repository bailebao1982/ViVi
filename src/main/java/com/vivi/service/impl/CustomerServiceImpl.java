/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.service.impl;

import com.vivi.dao.CustomerDAO;
import com.vivi.entity.Customer;
import com.vivi.service.CustomerService;

/**
 *
 * @author wewezhu
 */
public class CustomerServiceImpl implements CustomerService{
    private CustomerDAO customerDAO;

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    @Override
    public Customer findCustomerByCustomerId(String customerId) {
        return customerDAO.findCustomerByCustomerId(customerId);
    }

    @Override
    public boolean removeCustomer(Customer customer) {
        return customerDAO.removeCustomer(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
        return true;
    }
    
    
    
}
