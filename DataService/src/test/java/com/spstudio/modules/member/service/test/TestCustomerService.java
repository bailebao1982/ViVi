/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.service.test;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author wewezhu
 */
public class TestCustomerService {
    
    private static SessionFactory sessionFactory;
    
    public TestCustomerService() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("setUp()...");
        ApplicationContext factory = new FileSystemXmlApplicationContext( "src/main/java/com/com.spstudio/config/spring-*.xml");
        sessionFactory = (SessionFactory) factory.getBean("sessionFactory");
        
    }
    
    @After
    public void tearDown() {
         System.out.println("tearDown()...");
         sessionFactory.close();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() {}
}
