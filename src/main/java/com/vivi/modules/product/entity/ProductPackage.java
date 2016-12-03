/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.product.entity;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author wewezhu
 */
public class ProductPackage {
    
    Set<Product> products = new LinkedHashSet<Product>();
    
    int unitPrice;
    
    String description; 
    
    String packageName;
    
    Date effectiveStartDate;
    
    Date effectiveEndDate;
    
    int unitBounce;
    
    int bonusePoint;
    
    
}
