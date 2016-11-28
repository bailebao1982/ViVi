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
        customer = new CustomerBean();
        status = new StatusResponse();
    }
    
    public String getCustomerId() {
        return customer.getCustomerId();
    }

    public void setCustomerId(String customerId) {
       this.customer.setCustomerId(customerId);
    }

    public String getWeixinId() {
        return customer.getMember_wechat();
    }

    public void setWeixinId(String weixinId) {
       this.customer.setMember_wechat(weixinId);
    }

    public String getSex() {
        return customer.getMember_sex();
    }

    public void setSex(String sex) {
       this.customer.setMember_sex(sex);
    }

    public String getAddress() {
        return customer.getMember_address();
    }

    public void setAddress(String Address) {
        this.customer.setMember_address(Address);
        
    }

    public String getType() {
        return customer.getMember_type();
    }

    public void setType(String type) {
        this.customer.setMember_type(type);
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
     
    public String getMember_name() {
        return this.customer.getMember_name();
    }

    public void setMember_name(String member_name) {
        this.customer.setMember_name(member_name);
    }

    public String getMember_telphone() {
        return customer.getMember_telphone();
    }

    public void setMember_telphone(String member_telphone) {
        this.customer.setMember_telphone(member_telphone);
    }

    public String getMember_job() {
   
        return this.customer.getMember_job();
    }

    public void setMember_job(String member_job) {
        this.customer.setMember_job(member_job);
    }

    public String getMember_hobby() {
        return this.customer.getMember_hobby();
    }

    public void setMember_hobby(String member_hobby) {
        this.customer.setMember_hobby(member_hobby);
    }

    public String getMember_inputdate() {
        return customer.getMember_inputdate();
    }

    public void setMember_inputdate(String member_inputdate) {
        this.customer.setMember_inputdate(member_inputdate);
    }

    public String getMember_note() {
        return this.customer.getMember_note();
    }

    public void setMember_note(String member_note) {
        this.customer.setMember_note(member_note);
    }

    public String getMember_birthday() {
        return this.customer.getMember_birthday();
    }

    public void setMember_birthday(String member_birthday) {
        this.customer.setMember_birthday(member_birthday);
        
    }
}
