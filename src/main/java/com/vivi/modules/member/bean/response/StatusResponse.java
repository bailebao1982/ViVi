/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.member.bean.response;

import com.vivi.common.response.ErrorMessageBean;

/**
 *
 * @author wewezhu
 */
public class StatusResponse {
    
    private String success;
    
    private ErrorMessageBean error;

    public StatusResponse(){ 
    }
    
    private ErrorMessageBean getErrorMessageInstance(){
        if(error!=null)
            return error;
        else{
            error = new ErrorMessageBean();
            return error;
        }
    }
    
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ErrorMessageBean getError() {
        return error;
    }

    public void setError(ErrorMessageBean error) {
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
