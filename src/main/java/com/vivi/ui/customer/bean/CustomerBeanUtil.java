/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.ui.customer.bean;

import com.vivi.entity.Customer;
import com.vivi.ui.bean.ErrorMessage;
import com.vivi.ui.customer.bean.CustomerBean;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author wewezhu
 */
public class CustomerBeanUtil {
    public static CustomerBean createCustomerBean(Customer customer){
        CustomerBean customerBean = new CustomerBean();
        customerBean.setCustomerId(customer.getCustomerId());
        customerBean.setMember_address(customer.getAddress());
        if(customer.getBirthDay() != null)
            customerBean.setMember_birthday(customer.getBirthDay().toString());
        customerBean.setMember_hobby(customer.getHobby());
        if(customer.getCreationDate()!=null)
            customerBean.setMember_inputdate(customer.getCreationDate().toString());
        customerBean.setMember_job(customer.getJob());
        customerBean.setMember_name(customer.getCustomerName());
        customerBean.setMember_note(customer.getRemark());
        customerBean.setMember_sex(customer.getSex());
        customerBean.setMember_telphone(customer.getMobile());
        customerBean.setMember_wechat(customer.getWeixinId());
        //customerBean.setMember_type(member_type);
        
        return customerBean;
    }
    
    public static Customer parserCustomerBean(CustomerBean customerBean){
        Customer customer = new Customer();
      
        customer.setAddress(customerBean.getMember_address());
        if(customerBean.getMember_birthday() != null)
            customer.setBirthDay(java.sql.Date.valueOf(customerBean.getMember_birthday()));
        customer.setCustomerName(customerBean.getMember_name());
        customer.setHobby(customerBean.getMember_hobby());
        if(customerBean.getMember_inputdate()!=null)
            customer.setCreationDate(java.sql.Date.valueOf(customerBean.getMember_inputdate()));
        customer.setJob(customerBean.getMember_job());
        customer.setMobile(customerBean.getMember_telphone());
        customer.setRemark(customerBean.getMember_note());
        customer.setSex(customerBean.getMember_sex());
        
        //customer.setType(type);
        customer.setWeixinId(customerBean.getMember_wechat());
        
        return customer;
    }
    
    public static ErrorMessage getBeanStatus(){
        
        return null;
    }
}
