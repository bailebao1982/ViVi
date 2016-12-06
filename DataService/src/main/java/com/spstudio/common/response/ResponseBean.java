package com.spstudio.common.response;

/**
 * Created by Soul on 2016/12/1.
 */
public class ResponseBean<T> {
    private boolean success;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T pData){
        this.data = pData;
    }
}
