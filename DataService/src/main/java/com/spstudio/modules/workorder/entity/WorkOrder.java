/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.workorder.entity;

import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.sp.entity.ServiceProvider;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

/**
 *
 * @author wewezhu
 */
public class WorkOrder {
    Date registerDate;
    
    Time registerTime;
    
    Set<ServiceProvider> sp;
    
    Set<Member> customer;
    
    Product consumeProduct;
    
    String comment;
    
    String Rate;
    
    Date createDate;
    
    Time creationTime;
    
    String Status;
    
    Date confirmTime;
    
    String serviceLocation;
}
