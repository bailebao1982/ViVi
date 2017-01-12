/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.session.filter;

import com.spstudio.session.UserSession;
import com.spstudio.session.UserSessionType;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 *
 * @author wewezhu
 */
/** 
 * Session AOP切面  
 *  
 */  
@Component  
@Aspect  
public class SessionAOP {  
      
    @Around(value = "@annotation(com.spstudio.session.UserSession)")  
    public Object aroundManager(ProceedingJoinPoint pj) throws Exception {  
        HttpServletRequest request = SysContent.getRequest();  
        HttpServletResponse response = SysContent.getResponse();  
        HttpSession session = SysContent.getSession();  
  
        String path = request.getContextPath();  
        String basePath = request.getScheme() + "://" + request.getServerName()  
                + ":" + request.getServerPort() + path + "/";  
  
        UserSessionType type = this.getSessionType(pj);  
        if (type == null) {  
            throw new Exception("The value of NeedSession is must.");  
        }  
  
        Object uobj = session.getAttribute("user");  
        Object mobj = session.getAttribute("manager");  
          
        boolean isUser = type == UserSessionType.USER && uobj != null;  
        boolean isManager = type == UserSessionType.MANAGER && mobj != null;  
        boolean isUserOrManager = type == UserSessionType.OR&& (mobj != null || uobj != null);  
        try {  
            if (isUser || isManager || isUserOrManager) {                 
                return pj.proceed();  
            } else { // 会话过期或是session中没用户  
                if (request.getHeader("x-requested-with") != null      
                        && request.getHeader("x-requested-with").equalsIgnoreCase(    //ajax处理       
                                "XMLHttpRequest")) {       
                    response.addHeader("sessionstatus", "timeout");   
                    // 解决EasyUi问题  
                    //response.getWriter().print("{\"rows\":[],\"success\":false,\"total\":0}");       
                }else{//http跳转处理       
                    response.sendRedirect(basePath + "error/nosession");  
                }    
            }  
        } catch (Throwable e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    private UserSessionType getSessionType(ProceedingJoinPoint pj) {  
        // 获取切入的 Method  
        MethodSignature joinPointObject = (MethodSignature) pj.getSignature();  
        Method method = joinPointObject.getMethod();  
        boolean flag = method.isAnnotationPresent(UserSession.class);  
        if (flag) {  
            UserSession annotation = method.getAnnotation(UserSession.class);  
            return annotation.value();  
        }  
        return null;  
    }  
  
}  
