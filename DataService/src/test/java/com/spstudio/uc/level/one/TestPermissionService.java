/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.uc.level.one;

import com.spstudio.junit.JUnit4ClassRunner;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.entity.LoginUserGroup;
import com.spstudio.modules.permission.entity.PermissionRole;
import com.spstudio.modules.permission.entity.Privilege;
import com.spstudio.modules.permission.service.PermissionService;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wewezhu
 */
@RunWith(JUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:com/spstudio/common/config/spring-service.xml","classpath:com/spstudio/modules/member/config/spring-member.xml","classpath:com/spstudio/modules/permission/config/spring-permission.xml"})  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) 
@Transactional
public class TestPermissionService {
    
    private static Member member;
    
    @Autowired
    @Qualifier("memberService")
    MemberService memberService;
    
    @Autowired
    @Qualifier("permissionService")
    PermissionService permissionService;
    
    
    public TestPermissionService() {
    }
    
    @BeforeClass
    public static void setUpClass() {
      
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
      getOrCreateUserGroups();
        
    }
    
    private Privilege getOrCreatePrivileges(){
        Privilege listMemberTypePriv = permissionService.findPrivilegeByFuncationName("listMemberType");
        if(listMemberTypePriv == null){
            listMemberTypePriv = permissionService.createNewPriv("listMemberType", "listMemberType", "List All Member Type");
        }
        
        return listMemberTypePriv;
    }
    
    private PermissionRole getOrCreatePermissionRoles(){
        Set<Privilege> privs = new HashSet<Privilege>();
        Privilege listMemTypePriv = getOrCreatePrivileges();
        privs.add(listMemTypePriv);
        
        PermissionRole permissionRole = permissionService.getPermissionRoleByRoleName("ReadMemberRole");
        if(permissionRole == null){
            permissionRole = permissionService.createNewPermissionRole("ReadMemberRole", "Read Only Role for Member", privs);
        }
        
        return permissionRole;
        
    }
    
    private LoginUserGroup getOrCreateUserGroups(){
        PermissionRole role = getOrCreatePermissionRoles();
        
        Set<PermissionRole> roles = new HashSet<PermissionRole>();
        roles.add(role);
        
        
        LoginUserGroup userGroup = permissionService.getLoginUserGroupByGroupName("UserGroup");
        if(userGroup == null){
            userGroup = permissionService.createNewLoinUserGroup("UserGroup", "Group for member", roles);
        }
        return userGroup;
    }
    
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreationPermissionService(){
         
        
         Member member = new Member();
         member.setMemberName("TestName");
         member.setAddress("TestAddress");
         member.setWeixinId("TestWxId");
         member.setSex("Male");
         member.setMobile("149994033");
         memberService.addMember(member);
         
         LoginUser loginUser = permissionService.getLoginUserByLoginName("TestName");
         assertNotNull(loginUser);
         
         LoginUserGroup userGroup = permissionService.getLoginUserGroupByGroupName("UserGroup");
         loginUser.setGroup(userGroup);
         
         loginUser = permissionService.addLoginUserToGroup(loginUser, userGroup);
         Set<Privilege> privs = permissionService.listPrivilegsByLoginUser(loginUser);
         
         for(Privilege priv:privs){
             System.out.println(priv.getFuncationName());
         }
         
         assertNotNull(privs);
         
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
