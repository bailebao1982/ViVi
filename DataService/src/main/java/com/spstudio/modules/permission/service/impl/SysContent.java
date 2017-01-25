/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wewezhu
 */
public class SysContent {
    private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();  
    private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();  
  
    public static HttpServletRequest getRequest() {  
        return requestLocal.get();  
    }  
  
    public static void setRequest(HttpServletRequest request) {  
        requestLocal.set(request);  
    }  
  
    public static HttpServletResponse getResponse() {  
        return responseLocal.get();  
    }  
  
    public static void setResponse(HttpServletResponse response) {  
        responseLocal.set(response);  
    }  
  
    public static HttpSession getSession() {  
        return requestLocal.get().getSession();  
    }  
}
