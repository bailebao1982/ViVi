/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common.exception;

import com.spstudio.common.response.ErrorMessageBean;

/**
 *
 * @author wewezhu
 */
public class GenericException extends Exception{
    
    private String exceptionCode;
    
    private String errMsg;
    
    public GenericException(String code,String msg){
        this.errMsg = msg;
        this.exceptionCode = code;
    }
    
    public GenericException(){
        
    }
    
    public ErrorMessageBean getResponseBean(){
        ErrorMessageBean errMsgBean = new ErrorMessageBean();
        errMsgBean.setErrorCode(exceptionCode);
        errMsgBean.setErrorMessage(errMsg);
        return errMsgBean;
    }
}
