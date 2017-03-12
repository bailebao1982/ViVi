/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.dao;

import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.entity.LoginUserGroup;
import com.spstudio.modules.permission.entity.PermissionRole;
import com.spstudio.modules.permission.entity.Privilege;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wewezhu
 */
public interface PermissionDAO {
    public boolean login(Map<String, Object> params);
    
    public LoginUser addLoginUser(LoginUser loginUser);
    
    public LoginUserGroup addLoginUserGroup(LoginUserGroup userGroup);
    
    public LoginUserGroup updateLoginUserGroup(LoginUserGroup userGroup);
    
    public LoginUser updateLoginUser(LoginUser loginUser);
    
    public PermissionRole addPermissionRole(PermissionRole permissionRole);
    
    public PermissionRole updatePermissonRole(PermissionRole permissionRole);
    
    public List<PermissionRole> listAllPermissionRoles();
    
    public List<LoginUserGroup> listAllLoginUserGroups();
    
    public List<Privilege> listAllPrivileges();
    
    public List<LoginUser> listAllLoginUsers();
    
    public LoginUser getLoginUserByLoginName(String loginName);

    public LoginUser getLoginUserByMemberId(String memberId);
    
    public Privilege getPrivilegeByFuncationName(String funcationName);
    
    public LoginUserGroup getLoginUserGroupByGroupName(String groupName);
    
    public PermissionRole getPermissionRoleByRoleName(String roleName);
    
    public Privilege addPrivilege(Privilege privilege);
    
    
}
