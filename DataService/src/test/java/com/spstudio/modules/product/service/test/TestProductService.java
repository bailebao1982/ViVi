/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.product.service.test;

import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.service.ProductService;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author wewezhu
 */
public class TestProductService {
    
    private static SessionFactory sessionFactory;
    
    private static ProductService productService;
    
    private static Product product;
    
    public TestProductService() {
    }
    
    @BeforeClass
    public static void setUpClass() {
       
        ApplicationContext factory = new FileSystemXmlApplicationContext( new String[]{"src/main/java/com/spstudio/common/config/spring-*.xml","src/main/java/com/spstudio/modules/*/config/spring-*.xml"});
        sessionFactory = (SessionFactory) factory.getBean("sessionFactory");
        productService = (ProductService)factory.getBean("productService");
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        product = new Product();
        product.setProductName("TestProduct");
        product.setUnitPrice(280);
        product.setUnitBounce(1);
        product.setDescription("TestDescription");
        product.setBonusePoint(1);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     //@Test
     public void crudProduct() {
         
        Product newAddProduct = productService.addProduct(product);
        Product findProduct = productService.findProductByProductId(newAddProduct.getProductId());
        assertNotNull(findProduct);
        
        productService.removeProduct(findProduct);
        
        
     }
}
