/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.entity;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author wewezhu
 */
public class Product {
   
    int unitPrice;
    
    int unitBounce;
    
    String description;
    
    String productName;
    
    int bonusePoint;
    
    Set<ProductPackage> productPackage = new LinkedHashSet<ProductPackage>();
}
