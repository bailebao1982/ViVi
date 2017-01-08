/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.junit;

import java.io.FileNotFoundException;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 *
 * @author wewezhu
 */
public class JUnit4ClassRunner extends SpringJUnit4ClassRunner{
     static {  
        try {  
            Log4jConfigurer.initLogging("classpath:com/spstudio/common/config/log4j.properties");  
        } catch (FileNotFoundException ex) {  
            System.err.println("Cannot Initialize log4j");  
        }  
    }  

    public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
      
}
