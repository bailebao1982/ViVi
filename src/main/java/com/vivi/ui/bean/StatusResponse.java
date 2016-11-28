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
    
    private ErrorMessage error = new ErrorMessage();

    public StatusResponse(){ 
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
        this.error.setErrorCode(errorCode);
    }
    
     public void setErrorMessage(String errorMessage) {
         this.error.setErrorMessage(errorMessage);
    }
    
}
