/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.ui.bean;

/**
 *
 * @author wewezhu
 */
public class StatusResponse {
    
    private String success;
    
    private ErrorMessage error;

    public StatusResponse(){ 
    }
    
    private ErrorMessage getErrorMessageInstance(){
        if(error!=null)
            return error;
        else{
            error = new ErrorMessage();
            return error;
        }
    }
    
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ErrorMessage getError() {
        return error;
    }

    public void setError(ErrorMessage error) {
        this.error = error;
    }
    
    public void setErrorCode(String errorCode) {
        if(this.error!= null)
        this.getErrorMessageInstance().setErrorCode(errorCode);
    }
    
     public void setErrorMessage(String errorMessage) {
         this.getErrorMessageInstance().setErrorMessage(errorMessage);
    }
    
}
