package com.spstudio.modules.sales.exception;

/**
 * Created by Soul on 2017/2/7.
 */
public class InsufficientBonusPointException extends Exception {
    final static private String msg = "bonus point is not enough for decreasing!";

    public InsufficientBonusPointException(){
        super(msg);
    }

    public InsufficientBonusPointException (Throwable cause) {
        super (msg, cause);
    }
}
