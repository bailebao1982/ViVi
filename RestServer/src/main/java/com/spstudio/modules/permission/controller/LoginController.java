/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.controller;

import com.spstudio.modules.permission.service.PermissionService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wewezhu
 */
@RestController  
@RequestMapping(value = "/login" , method = {RequestMethod.GET,RequestMethod.POST}, headers="Accept=application/json")  
public class LoginController {
    @Resource(name="permissionService")
    private PermissionService permissionService;
     
     @RequestMapping(value = "/ssmlogin")  
    public Map<String,Object> ssmLogin(String username,String password){  
        Map<String,Object> params = new HashMap<String,Object>();  
        System.out.println(username);  
        System.out.println(password);  
        params.put("username",username);  
        params.put("password",password);  
        return permissionService.login(params); 
    }
}
