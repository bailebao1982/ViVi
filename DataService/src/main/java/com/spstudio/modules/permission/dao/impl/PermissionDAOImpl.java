/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.dao.impl;

import com.spstudio.modules.permission.dao.PermissionDAO;
import com.spstudio.modules.permission.entity.LoginUser;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class PermissionDAOImpl implements PermissionDAO{

     private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    //TODO: for future, it will return a list of user-role map.
    @Override
    public boolean login(Map<String, Object> params) {
       String username = (String)params.get("username");
       String password = (String)params.get("password");
        
       String hql = "from LoginUser where loginUser = :id and loginPassword = :pwd";
       
       Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", username);
        query.setString("pwd", password);
        List<LoginUser> list = query.list();
        if(list.size() > 0)
            return true;
        return false;
    }
    
}
