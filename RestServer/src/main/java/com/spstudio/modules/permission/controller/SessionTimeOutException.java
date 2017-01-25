/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.controller;

/**
 *
 * @author wewezhu
 */
public class SessionTimeOutException extends Exception{
    
    public SessionTimeOutException(String errorMsg){
        super(errorMsg);
    }
}
