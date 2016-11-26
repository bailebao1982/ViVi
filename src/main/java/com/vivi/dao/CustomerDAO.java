/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.dao;

import com.vivi.entity.Customer;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface CustomerDAO {
    public List<Customer> getAllCustomers();
    
    public Customer findCustomerByCustomerId(String customerId);
    
    public Customer addCustomer(Customer customer);
    
    public boolean removeCustomer(Customer customer);
    
    public Customer updateCustomer(Customer customer);
}
