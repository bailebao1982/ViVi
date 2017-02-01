/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.service;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.entity.LoginUserGroup;
import com.spstudio.modules.permission.entity.PermissionRole;
import com.spstudio.modules.permission.entity.Privilege;
import com.spstudio.modules.sp.entity.ServiceProvider;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wewezhu
 */
public interface PermissionService {
    public Map<String, Object> login(Map<String, Object> params); 
   
    public LoginUser createNewLoginUserFromMember(Member member);
    
    public LoginUser createNewLoginUserFromServiceProvider(ServiceProvider sp);
    
    public LoginUser addLoginUserToGroup(LoginUser loginUser,LoginUserGroup loginUserGroup);
    
    public LoginUserGroup createNewLoinUserGroup(String groupName,String groupDescription,Set<PermissionRole> permissionRoles);
    
    public LoginUserGroup updateLoginUserGroup(LoginUserGroup loginUserGroup);
    
    public boolean removeLoginUserGroup(LoginUserGroup loginUserGroup);
    
    public PermissionRole createNewPermissionRole(String groupName,String groupDescription,Set<Privilege> privileges);
    
    public PermissionRole updatePermissionRole(PermissionRole permissionRole);
    
    public PermissionRole removePermissionRole(PermissionRole permissionRole);
    
    public Privilege updatePrivilege(Privilege privilege);
    
    public List<LoginUser> listAllLoginUsers();

    public List<LoginUserGroup> listAllLoginUserGroups();
    
    public List<PermissionRole> listAllPermissionRoles();
    
    public List<Privilege> listAllPrivileges();
    
    public Set<Privilege> listPrivilegsByLoginUser(LoginUser loginUser);
    
    public LoginUser getLoginUserByLoginName(String loginName);
    
    public Privilege findPrivilegeByFuncationName(String funcationName);
    
    public LoginUserGroup getLoginUserGroupByGroupName(String groupName);
    
    public PermissionRole getPermissionRoleByRoleName(String roleName);
    
    public Privilege createNewPriv(String priName,String funcationName,String desc);
    
}
