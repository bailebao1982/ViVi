/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.ui.customer.bean;

import com.vivi.entity.Customer;
import com.vivi.search.Page;
import com.vivi.ui.bean.ErrorMessage;
import com.vivi.ui.bean.StatusResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public class CustomerSearchResultBean{
    private StatusResponse status;

    private Page<Customer> customerPage;
    
    public CustomerSearchResultBean(){
        status = new StatusResponse();
    }
    
    public String getSuccess() {
        return this.status.getSuccess();
    }

    public void setSuccess(String success) {
        this.status.setSuccess(success);
    }

    public ErrorMessage getError() {
        return this.status.getError();
    }

    public void setError(ErrorMessage error) {
        this.status.setError(error);
    }
    
    public void setErrorCode(String errorCode) {
        this.status.setErrorCode(errorCode);
    }
    
     public void setErrorMessage(String errorMessage) {
         this.status.setErrorMessage(errorMessage);
    }

    public List<CustomerBean> getCustomerPage() {
        ArrayList<CustomerBean> customers = new ArrayList<CustomerBean>();
        for(Customer customer: customerPage.getList()){
            customers.add(CustomerBeanUtil.createCustomerBean(customer));
        }
        System.out.println("getCustomerPage.getPageSize:"+customerPage.getPageSize());
        return customers;
    }

    public int getTotal_pages() {
        return customerPage.getTotalPages();
    }

    public int getTotal_records() {
        return customerPage.getTotalRecords();
    }

    public int getCurrent_page() {
        return customerPage.getPageNo();
    }

    public void setCustomerPage(Page<Customer> customerPage) {
        this.customerPage = customerPage;
    }
     
     
}
