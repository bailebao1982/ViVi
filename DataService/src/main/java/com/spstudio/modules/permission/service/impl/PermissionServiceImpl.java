/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.service.impl;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.permission.dao.PermissionDAO;
import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.entity.LoginUserGroup;
import com.spstudio.modules.permission.entity.PermissionRole;
import com.spstudio.modules.permission.entity.Privilege;
import com.spstudio.modules.permission.service.PermissionService;
import com.spstudio.modules.sp.entity.ServiceProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wewezhu
 */
public class PermissionServiceImpl implements PermissionService{
    private PermissionDAO permissionDAO;  

    public PermissionDAO getPermissionDAO() {
        return permissionDAO;
    }

    public void setPermissionDAO(PermissionDAO permissionDAO) {
        this.permissionDAO = permissionDAO;
    }
    
    
    @Override  
    public Map<String, Object> login(Map<String, Object> params) {  
        Map<String,Object> resMap = new HashMap<>();  
        boolean findUser = permissionDAO.login(params);  
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

    @Override
    public LoginUser createNewLoginUserFromMember(Member member) {
       LoginUser newLoginUser = new LoginUser();
       newLoginUser.setLoginCount(0);
       newLoginUser.setLoginPassword(SysContent.DEFAULT_PWD);
       newLoginUser.setMemberId(member.getMemberId());
       newLoginUser.setLoginUser(member.getMemberName());
       newLoginUser.setCreationTime(new Date(System.currentTimeMillis()));
       
       return permissionDAO.addLoginUser(newLoginUser);
    }

    @Override
    public LoginUser createNewLoginUserFromServiceProvider(ServiceProvider sp) {
       LoginUser newLoginUser = new LoginUser();
       newLoginUser.setLoginCount(0);
       newLoginUser.setLoginPassword(SysContent.DEFAULT_PWD);
       newLoginUser.setCreationTime(new Date(System.currentTimeMillis()));
       newLoginUser.setMemberId(sp.getServiceProviderId());
       newLoginUser.setLoginUser(sp.getSpName());
       
       return permissionDAO.addLoginUser(newLoginUser);
       
    }

    @Override
    public LoginUser addLoginUserToGroup(LoginUser loginUser, LoginUserGroup loginUserGroup) {
        loginUser.setGroup(loginUserGroup);
        return permissionDAO.updateLoginUser(loginUser);
    }

    @Override
    public LoginUserGroup createNewLoinUserGroup(String groupName, String groupDescription, Set<PermissionRole> permissionRoles) {
        LoginUserGroup newLoginUserGroup = new LoginUserGroup();
        newLoginUserGroup.setGroupName(groupName);
        newLoginUserGroup.setGroupDescription(groupDescription);
        newLoginUserGroup.setCeationDate(new Date(System.currentTimeMillis()));
        newLoginUserGroup.setRoles(permissionRoles);
        
        return permissionDAO.addLoginUserGroup(newLoginUserGroup);
        
        
    }

    @Override
    public LoginUserGroup updateLoginUserGroup(LoginUserGroup loginUserGroup) {
       return permissionDAO.updateLoginUserGroup(loginUserGroup);
    }

    @Override
    public boolean removeLoginUserGroup(LoginUserGroup loginUserGroup) {
       //loginDao
       return false;
    }

    @Override
    public PermissionRole createNewPermissionRole(String roleName, String roleDescription, Set<Privilege> privileges) {
        PermissionRole role = new PermissionRole();
        role.setRoleName(roleName);
        role.setRoleDescription(roleDescription);
        role.setPriviledges(privileges);
        
        return permissionDAO.addPermissionRole(role);
        
    }

    @Override
    public PermissionRole updatePermissionRole(PermissionRole permissionRole) {
       return permissionDAO.updatePermissonRole(permissionRole);
    }

    @Override
    public PermissionRole removePermissionRole(PermissionRole permissionRole) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Privilege updatePrivilege(Privilege privilege) {
        return null;
    }

    @Override
    public List<LoginUser> listAllLoginUsers() {
        return permissionDAO.listAllLoginUsers();
    }


    @Override
    public List<LoginUserGroup> listAllLoginUserGroups() {
       return permissionDAO.listAllLoginUserGroups();
    }

    @Override
    public List<PermissionRole> listAllPermissionRoles() {
        return permissionDAO.listAllPermissionRoles();
    }

    @Override
    public List<Privilege> listAllPrivileges() {
       return permissionDAO.listAllPrivileges();
    }

    @Override
    public Set<Privilege> listPrivilegsByLoginUser(LoginUser loginUser) {
       Set<Privilege> priviledges = new HashSet<Privilege>();
       
       Set<PermissionRole> roles = loginUser.getGroup().getRoles();
       
       for(PermissionRole role:roles){
           priviledges.addAll(role.getPriviledges());
       }
       
        return priviledges;
    }

    @Override
    public LoginUser getLoginUserByLoginName(String loginName) {
       return permissionDAO.getLoginUserByLoginName(loginName);
    }

    @Override
    public Privilege findPrivilegeByFuncationName(String funcationName) {
        return permissionDAO.getPrivilegeByFuncationName(funcationName);
    }

    @Override
    public LoginUserGroup getLoginUserGroupByGroupName(String groupName) {
       return permissionDAO.getLoginUserGroupByGroupName(groupName);
    }

    @Override
    public PermissionRole getPermissionRoleByRoleName(String roleName) {
      return permissionDAO.getPermissionRoleByRoleName(roleName);
    }

    @Override
    public Privilege createNewPriv(String priName, String funcationName,String desc) {
      Privilege privilege = new Privilege();
      privilege.setFuncationName(funcationName);
      privilege.setPrivilegeName(priName);
      privilege.setPrivilegeDescription(desc);
       return permissionDAO.addPrivilege(privilege);
    }

   
}
