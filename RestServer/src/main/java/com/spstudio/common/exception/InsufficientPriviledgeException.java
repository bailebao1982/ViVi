/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common.exception;

/**
 *
 * @author wewezhu
 */
public class InsufficientPriviledgeException extends GenericException{
    public InsufficientPriviledgeException(String code,String message){
        super(code,message);
    }
}
