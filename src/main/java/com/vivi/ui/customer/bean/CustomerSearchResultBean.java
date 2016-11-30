/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.ui.customer.bean;

import com.vivi.ui.bean.ErrorMessage;
import com.vivi.ui.bean.StatusResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public class CustomerSearchResultBean {
    private StatusResponse status;
    
    private List<CustomerBean> members;
    
    private int total_pages;
    
    private int total_records;
    
    private int current_page;

    public CustomerSearchResultBean(){
        status = new StatusResponse();
        members = new ArrayList<CustomerBean>();
    }
    
    public void addCustomer(CustomerBean customer){
        this.members.add(customer);
    }
    
    public List<CustomerBean> getMembers() {
        return members;
    }

    public void setMembers(List<CustomerBean> members) {
        this.members = members;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_records() {
        return total_records;
    }

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
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
}
