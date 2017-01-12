package com.spstudio.modules.stock.exceptions;

/**
 * Created by Soul on 2017/1/12.
 */
public class StockNotEnoughException extends Exception{

    final static private String msg = "Product stock is not enough for decreasing!";

    public StockNotEnoughException(){
        super(msg);
    }

    public StockNotEnoughException (Throwable cause) {
        super (msg, cause);
    }
}
