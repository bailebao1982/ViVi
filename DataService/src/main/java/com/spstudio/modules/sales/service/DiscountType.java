package com.spstudio.modules.sales.service;

/**
 * Created by Soul on 2017/1/22.
 */
public enum DiscountType {
    PRODUCT_TYPE_DISCOUNT,
    PRODUCT_DISCOUNT,
    NON_DISCOUNT,
    ALL_DISCOUNTS;

    public static DiscountType fromInteger(int x)
    {
        DiscountType[] As = DiscountType.values();
        if(As.length <= x){
            return NON_DISCOUNT;
        }else if(x < 0){
            return ALL_DISCOUNTS;
        }
        return As[x];
    }
}
