package com.spstudio.modules.workorder.exception;

/**
 * Created by Soul on 2017/2/25.
 */
public class AssetNotFoundException extends Exception  {
    final static private String msg = "no such asset found for decreasing!";

    public AssetNotFoundException(){
        super(msg);
    }

    public AssetNotFoundException(Throwable cause) {
        super (msg, cause);
    }
}
