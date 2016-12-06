package com.spstudio.common.response;

/**
 * Created by Soul on 2016/12/1.
 */
public class ResponseMsgBeanFactory {
    public static <T> ResponseBean<T> getResponseBean(boolean success, T data){
        ResponseBean<T> responseBean = new ResponseBean<T>();
        responseBean.setSuccess(success);
        responseBean.setData(data);
        return responseBean;
    }

    public static ResponseBean<SuccessMessageBean> getSuccessResponseBean(String msg){
        SuccessMessageBean successBean = new SuccessMessageBean();
        successBean.setMessage(msg);
        return getResponseBean(true, successBean);
    }

    public static ResponseBean<ErrorMessageBean> getErrorResponseBean(String code, String msg){
        ErrorMessageBean errorBean = new ErrorMessageBean();
        errorBean.setErrorMessage(msg);
        errorBean.setErrorCode(code);
        return getResponseBean(false, errorBean);
    }

}
