package com.spstudio.modules.workorder.exception;

/**
 * Created by Soul on 2017/2/25.
 */
public class InsuffientProductAssetException extends Exception  {
    final static private String msg = "product asset is not enough for decreasing!";

    public InsuffientProductAssetException(){
        super(msg);
    }

    public InsuffientProductAssetException (Throwable cause) {
        super (msg, cause);
    }
}
