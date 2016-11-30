/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.ui.controller;

import com.vivi.entity.Customer;
import com.vivi.service.CustomerService;
import com.vivi.ui.bean.BaseMessageBean;
import com.vivi.ui.bean.StatusResponse;
import com.vivi.ui.customer.bean.CustomerBean;
import com.vivi.ui.customer.bean.CustomerBeanResponse;
import com.vivi.ui.customer.bean.CustomerBeanUtil;
import com.vivi.ui.customer.bean.CustomerSearchResultBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wewezhu
 */
@RestController
public class CustomerController {
    
    @Resource(name="customerService")
    private CustomerService customerService;

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    
    @RequestMapping(value = "/customers", method = RequestMethod.GET,headers="Accept=application/json")  
    public @ResponseBody List<CustomerBean> getCustomers(){
        List<CustomerBean> listOfCustomers = new ArrayList<CustomerBean>();
        listOfCustomers = createCountryList();
        
        return listOfCustomers;
    }
    
    
    @RequestMapping(value = "/add_member", method = RequestMethod.PUT,headers="Accept=application/json") 
    public @ResponseBody StatusResponse addCustomer(@ModelAttribute CustomerBean customer){
        StatusResponse status = new StatusResponse();
        
        Customer newCustomer = CustomerBeanUtil.parserCustomerBean(customer);
        customerService.addCustomer(newCustomer);
        
        status.setErrorCode("000");
        status.setErrorMessage("Not");
        
        return status;
    }
    
    @RequestMapping(value = "/update_member/{member_id}", method = RequestMethod.POST,headers="Accept=application/json") 
    public @ResponseBody StatusResponse updateCustomer(@ModelAttribute CustomerBean customer,@PathVariable String member_id){
        StatusResponse status = new StatusResponse();
        
        Customer updateCustomer = CustomerBeanUtil.parserCustomerBean(customer);
        updateCustomer.setCustomerId(member_id);
        
        customerService.updateCustomer(updateCustomer);
        
        status.setErrorCode("0010");
        status.setErrorMessage("Not");
        
        return status;
    }
    
    @RequestMapping(value = "/del_member/{member_id}", method = RequestMethod.DELETE,headers="Accept=application/json") 
    public @ResponseBody StatusResponse removeCustomer(@PathVariable String member_id){
        StatusResponse status = new StatusResponse();
        
        Customer removeCustomer = customerService.findCustomerByCustomerId(member_id);
        customerService.removeCustomer(removeCustomer);
        
        status.setErrorCode("0020");
        status.setErrorMessage("Not");
        
        return status;
    }
    
    @RequestMapping(value = "/member_detail/{member_id}", method = RequestMethod.GET,headers="Accept=application/json") 
    public @ResponseBody CustomerBeanResponse getCustomerByCustomerId(@PathVariable String member_id){
       
        Customer customer = customerService.findCustomerByCustomerId(member_id);
        
        CustomerBean responseCustomerBean = CustomerBeanUtil.createCustomerBean(customer);
        
        CustomerBeanResponse response = new CustomerBeanResponse();
        
        response.setCustomer(responseCustomerBean);
        response.setSuccess("Success");
        
        return response;
    }
    
    @RequestMapping(value = "/list_memebers", method = RequestMethod.GET,headers="Accept=application/json") 
    public @ResponseBody CustomerSearchResultBean searchCustomerByCondition(int page,int page_size,String name,String type,String birthday,String telepone,String email,String start_date,String end_date){
        CustomerSearchResultBean result = new CustomerSearchResultBean();
        
        CustomerBean customer = new CustomerBean();
        
        
        customer.setMember_name(name);
        customer.setMember_type(type);
        customer.setMember_birthday(birthday);
        customer.setMember_telphone(telepone);
        result.addCustomer(customer);
        result.setCurrent_page(page);
        
        return result;    
    }
    
    // Utiliy method to create country list.  
 public List<CustomerBean> createCountryList()  
 {  
     
     ArrayList<CustomerBean> customers = new ArrayList<CustomerBean>();
     
     CustomerBean customer = new CustomerBean();
     customer.setMember_address("Test1");
     customer.setCustomerId("33333");
     customer.setMember_sex("F");
     customer.setMember_type("eieiei");
     
     customers.add(customer);
     
     return customers;
     
 }

}
