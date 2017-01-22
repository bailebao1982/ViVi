/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.controller;

import com.spstudio.modules.permission.service.impl.MessageContent;
import com.spstudio.modules.permission.service.impl.SysContent;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 *
 * @author wewezhu
 */
@Aspect  
@Component("LoginControlAspect") 
public class LoginControlAspect {
     private static Logger logger = Logger.getLogger(LoginControlAspect.class);  
     
     @Around("@annotation(com.spstudio.modules.permission.controller.UserSessionValidator)")
      public Object sessionTimeOutAdvice(ProceedingJoinPoint pjp) throws SessionTimeOutException{  
        Object args[] = pjp.getArgs();  
        Object result = null;  
        String targetName = pjp.getTarget().getClass().getSimpleName();  
        String methodName = pjp.getSignature().getName();  
        logger.info("----------------Method　Name-----------------");  
        logger.info("Class:" + targetName + " Method:" + methodName);  
  
        HttpSession session = SysContent.getSession();  
        if (session.getAttribute("username") != null) {  
            try {  
                result = pjp.proceed(args);  
            } catch (Throwable e) {  
                e.printStackTrace();  
            }  
            return result;  
        } else {  
//            System.out.println(((MethodSignature)pjp.getSignature()).getReturnType().getSimpleName().toString());  
            String redirectStr = "login";  
            String returnType = ((MethodSignature)pjp.getSignature()).getReturnType().getSimpleName().toString();  
            if(returnType.equals("String")){  
                return redirectStr;  
            }else if(returnType.equals("Map")){  
                Map<String,Object> resMap = new HashMap<>();  
                resMap.put("resCode", MessageContent.MSG_CODE_SESSIONOUTOFDATE);  
                resMap.put("resMsg",MessageContent.MSG_SESSIONOUTOFDATE);  
                return resMap;  
            }else {  
                throw new SessionTimeOutException(MessageContent.MSG_SESSIONOUTOFDATE);  
            }  
        } 
      }
}
