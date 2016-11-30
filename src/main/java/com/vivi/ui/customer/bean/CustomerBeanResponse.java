/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.ui.customer.bean;

import com.vivi.ui.bean.ErrorMessage;
import com.vivi.ui.bean.StatusResponse;

/**
 *
 * @author wewezhu
 */
public class CustomerBeanResponse {
    private CustomerBean customer;
    
    private StatusResponse status;

    public CustomerBeanResponse(){
        status = new StatusResponse();
    }
    
    public String getCustomerId() {
        return customer.getCustomerId();
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

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }
   
     
}
