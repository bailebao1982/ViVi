package com.spstudio.modules.workorder.exception;

/**
 * Created by Soul on 2017/2/25.
 */
public class InsuffientPackageAssetException  extends Exception {
    final static private String msg = "package asset is not enough for decreasing!";

    public InsuffientPackageAssetException(){
        super(msg);
    }

    public InsuffientPackageAssetException (Throwable cause) {
        super (msg, cause);
    }
}
