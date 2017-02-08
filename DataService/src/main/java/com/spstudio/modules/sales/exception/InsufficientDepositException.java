package com.spstudio.modules.sales.exception;

/**
 * Created by Soul on 2017/2/7.
 */
public class InsufficientDepositException  extends Exception{
    final static private String msg = "deposit is not enough for decreasing!";

    public InsufficientDepositException(){
        super(msg);
    }

    public InsufficientDepositException (Throwable cause) {
        super (msg, cause);
    }
}
