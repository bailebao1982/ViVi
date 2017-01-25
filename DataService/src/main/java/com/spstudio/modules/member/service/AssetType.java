package com.spstudio.modules.member.service;

/**
 * Created by Soul on 2017/1/17.
 */
public enum AssetType {
    ASSET_PRODUCT_TYPE,
    ASSET_PACKAGE_TYPE,
    ASSET_DEPOSIT_TYPE,
    ASSET_NONE_TYPE;

    public static AssetType fromInteger(int x)
    {
        AssetType[] As = AssetType.values();
        if(As.length <= x){
            return ASSET_NONE_TYPE;
        }
        return As[x];
    }
}
