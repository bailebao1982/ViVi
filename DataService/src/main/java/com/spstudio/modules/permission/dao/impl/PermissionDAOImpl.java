/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.dao.impl;


import com.spstudio.modules.permission.dao.PermissionDAO;
import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.entity.LoginUserGroup;
import com.spstudio.modules.permission.entity.PermissionRole;
import com.spstudio.modules.permission.entity.Privilege;
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

    @Override
    public LoginUser addLoginUser(LoginUser loginUser) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(loginUser);
        return loginUser;
    }

    @Override
    public LoginUserGroup addLoginUserGroup(LoginUserGroup userGroup) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(userGroup);
        return userGroup;
    }

    @Override
    public LoginUserGroup updateLoginUserGroup(LoginUserGroup userGroup) {
       this.sessionFactory.getCurrentSession().update(userGroup);
       return userGroup;
    }

    @Override
    public LoginUser updateLoginUser(LoginUser loginUser) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(loginUser);
        return loginUser;
    }

    @Override
    public PermissionRole addPermissionRole(PermissionRole permissionRole) {
      this.sessionFactory.getCurrentSession().saveOrUpdate(permissionRole);
      return permissionRole;
    }

    @Override
    public PermissionRole updatePermissonRole(PermissionRole permissionRole) {
       this.sessionFactory.getCurrentSession().saveOrUpdate(permissionRole);
       return permissionRole;
    }

    @Override
    public List<PermissionRole> listAllPermissionRoles() {
       String hql = "from PermissionRole as permissionRole";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<PermissionRole> list = query.list();
        return list;
    }

    @Override
    public List<LoginUserGroup> listAllLoginUserGroups() {
     String hql = "from LoginUserGroup as loginUserGroup";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<LoginUserGroup> list = query.list();
        return list;
    }

    @Override
    public List<Privilege> listAllPrivileges() {
        String hql = "from Privilege as privilege";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<Privilege> list = query.list();
        return list;
    }

    @Override
    public List<LoginUser> listAllLoginUsers() {
        String hql = "from LoginUser as loginUser";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<LoginUser> list = query.list();
        return list;
    }

    @Override
    public LoginUser getLoginUserByLoginName(String loginName) {
          String hql = "from LoginUser where loginUser = :lName";
       
       Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("lName", loginName);
        List<LoginUser> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public Privilege getPrivilegeByFuncationName(String funcationName) {
         String hql = "from Privilege where funcationName = :fName";
       
       Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("fName", funcationName);
        List<Privilege> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public LoginUserGroup getLoginUserGroupByGroupName(String groupName) {
        String hql = "from LoginUserGroup where groupName = :gName";
       
       Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("gName", groupName);
        List<LoginUserGroup> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public PermissionRole getPermissionRoleByRoleName(String roleName) {
      String hql = "from PermissionRole where roleName = :rName";
       
       Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("rName", roleName);
        List<PermissionRole> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public Privilege addPrivilege(Privilege privilege) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(privilege);
        return privilege;
    }
}
