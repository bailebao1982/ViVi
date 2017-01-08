/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.session;

/**
 *
 * @author wewezhu
 */
public enum UserSessionType {
    /** 
     * 两者任意一个 
     */  
    OR,  
  
    /** 
     * 会员用户 
     */  
    USER,  
  
    /** 
     * 管理员 
     */  
    MANAGER
}