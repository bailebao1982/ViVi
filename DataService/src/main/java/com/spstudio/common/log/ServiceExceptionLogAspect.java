/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common.log;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * @author wewezhu
 */
public class ServiceExceptionLogAspect {

    private static final Logger logger = Logger.getLogger(ServiceExceptionLogAspect.class.getName());

    @Pointcut("execution(* com.spstudio.modules.*.service.impl.*.*(..))")
    public void serviceAspect() {
    }

    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Exception {

        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
        //HttpSession session = request.getSession();  
        //获取用户请求方法的参数并序列化为JSON格式字符串    
        String params = "";

        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {

            for (int i = 0; i < joinPoint.getArgs().length; i++) {

                params += joinPoint.getArgs()[i] + ";";

            }

        }

        StringBuilder bd = new StringBuilder();

        bd.append("异常方法相关信息:" + getServiceMthodDescription(joinPoint)).append("    ###  ").
                append("异常代码:" + e.getClass().getName()).append("    ###   ")
                .append("异常信息:" + e.getMessage()).append("    ###   ")
                .append("参数:" + params);

        logger.error(bd.toString());

    }

    /**      *
     * 获取注解中对方法的描述信息 用于service层注解      *
     *
     *
     * @param joinPoint 切点      *
     * @return 方法描述      *
     * @throws Exception      *
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {

        String targetName = joinPoint.getTarget().getClass().getName();

        String methodName = joinPoint.getSignature().getName();

        Object[] arguments = joinPoint.getArgs();

        Class targetClass = Class.forName(targetName);

        Method[] methods = targetClass.getMethods();

        String description = "异常类是:" + targetName + " ;异常方法名称:" + methodName + " ;异常描述信息:";

        for (Method method : methods) {

            if (method.getName().equals(methodName)) {

                Class[] clazzs = method.getParameterTypes();

                if (clazzs.length == arguments.length) {

                    description += method.getAnnotation(ServiceExceptionLog.class).description();

                    break;

                }

            }

        }

        return description;

    }
}
