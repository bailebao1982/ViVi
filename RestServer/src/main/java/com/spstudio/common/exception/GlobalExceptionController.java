/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common.exception;

import com.spstudio.common.response.ErrorMessageBean;
import com.spstudio.common.response.ResponseBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author wewezhu
 */
@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(GenericException.class)
    public @ResponseBody ResponseBean handleAllException(GenericException ex) {

                ResponseBean<ErrorMessageBean> responseBean = new ResponseBean<ErrorMessageBean>();
                responseBean.setSuccess(false);
                responseBean.setData(ex.getResponseBean());
                return responseBean;
	}
}
