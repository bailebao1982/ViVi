/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.service.impl;

import com.spstudio.modules.permission.dao.PermissionDAO;
import com.spstudio.modules.permission.service.PermissionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wewezhu
 */
public class PermissionServiceImpl implements PermissionService{
    private PermissionDAO loginDao;  

    public PermissionDAO getLoginDao() {
        return loginDao;
    }

    public void setLoginDao(PermissionDAO loginDao) {
        this.loginDao = loginDao;
    }
    
    
    @Override  
    public Map<String, Object> login(Map<String, Object> params) {  
        Map<String,Object> resMap = new HashMap<>();  
        boolean findUser = loginDao.login(params);  
        if(!findUser){  
            resMap.put("resCode", MessageContent.MSG_CODE_ERROR);  
            resMap.put("resMsg",MessageContent.MSG_USERNAMEORPASSWORDWRONG);  
        }else {  
            HttpSession session = SysContent.getSession();  
            session.setAttribute("username",params.get("username").toString());  
            session.setAttribute("password",params.get("password").toString());  
            resMap.put("resCode",MessageContent.MSG_CODE_SUCCESS);  
            resMap.put("resMsg",MessageContent.MSG_SUCCESS);  
        }  
        return resMap;  
    }  
}
