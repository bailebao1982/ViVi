/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.controller;

import com.spstudio.common.exception.GenericException;
import com.spstudio.common.exception.InsufficientPriviledgeException;
import com.spstudio.common.exception.SessionTimeOutException;
import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.entity.Privilege;
import com.spstudio.modules.permission.service.PermissionService;
import com.spstudio.modules.permission.service.impl.MessageContent;
import com.spstudio.modules.permission.service.impl.SysContent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author wewezhu
 */
@Aspect
@Component("LoginControlAspect")
public class LoginControlAspect {

    @Resource(name = "permissionService")
    private PermissionService permissionService;

    @Autowired
    private HttpSession session;

    private static Logger logger = Logger.getLogger(LoginControlAspect.class);

    @Around("@annotation(com.spstudio.modules.permission.controller.UserSessionValidator)")
    public Object sessionValidationAdvice(ProceedingJoinPoint pjp) throws GenericException, Throwable{
        Object args[] = pjp.getArgs();
        Object result = null;
        String targetName = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        logger.info("----------------Method　Name-----------------");
        logger.info("Class:" + targetName + " Method:" + methodName);

        //HttpSession session = SysContent.getSession();  
        if (session.getAttribute("username") != null) {
          
                //Logic to check user priviledge
                Privilege checkingFuncationPrivilege = permissionService.findPrivilegeByFuncationName(methodName);

                String userName = (String) session.getAttribute("username");
                LoginUser loginUser = permissionService.getLoginUserByLoginName(userName);

                if (loginUser != null) {
                    Set<Privilege> permissionedPrivileges = permissionService.listPrivilegsByLoginUser(loginUser);
                    if (permissionedPrivileges.contains(checkingFuncationPrivilege)) {
                        result = pjp.proceed(args);
                    } else {
                        throw new InsufficientPriviledgeException("403","no permission");
                    }
                } else {
                    throw new InsufficientPriviledgeException("403","no permission");
                }
            
            return result;
        } else {
//            System.out.println(((MethodSignature)pjp.getSignature()).getReturnType().getSimpleName().toString());  
            String redirectStr = "login";
            String returnType = ((MethodSignature) pjp.getSignature()).getReturnType().getSimpleName().toString();
            if (returnType.equals("String")) {
                return redirectStr;
            } else if (returnType.equals("Map")) {
                Map<String, Object> resMap = new HashMap<>();
                resMap.put("resCode", MessageContent.MSG_CODE_SESSIONOUTOFDATE);
                resMap.put("resMsg", MessageContent.MSG_SESSIONOUTOFDATE);
                return resMap;
            } else {
                throw new SessionTimeOutException(MessageContent.MSG_CODE_SESSIONOUTOFDATE,MessageContent.MSG_SESSIONOUTOFDATE);
            }
        }
    }
}
