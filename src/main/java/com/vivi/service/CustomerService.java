/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.service;

import com.vivi.entity.Customer;
import com.vivi.search.Page;
import com.vivi.search.SearchCriteria;

/**
 *
 * @author wewezhu
 */
public interface CustomerService {
    public void addCustomer(Customer customer);
    
    public Customer findCustomerByCustomerId(String customerId);
    
    public boolean removeCustomer(Customer customer);
    
    public boolean updateCustomer(Customer customer);
    
    public Page<Customer> queryForPage(int currentPage,int pageSize,SearchCriteria sc);
}
