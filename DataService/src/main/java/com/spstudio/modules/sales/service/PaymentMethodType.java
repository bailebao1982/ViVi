package com.spstudio.modules.sales.service;

/**
 * Created by Soul on 2017/1/22.
 */
public enum PaymentMethodType {
    PAYMENT_METHOD_CASH,
    PAYMENT_METHOD_BONUSPOINT,
    PAYMENT_METHOD_DEPOSIT,
    PAYMENT_METHOD_NONE;

    public static PaymentMethodType fromInteger(int x)
    {
        PaymentMethodType[] As = PaymentMethodType.values();
        if(As.length <= x){
            return PAYMENT_METHOD_NONE;
        }
        return As[x];
    }
}
