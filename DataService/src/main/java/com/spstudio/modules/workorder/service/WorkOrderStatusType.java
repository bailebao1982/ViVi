package com.spstudio.modules.workorder.service;

/**
 * Created by Soul on 2017/2/13.
 */
public enum WorkOrderStatusType {
    WO_UNCONFIRMED,
    WO_CONFIRMED,
    WO_EXPIRED,
    WO_UNKNOWN;

    public static WorkOrderStatusType fromInteger(int x)
    {
        WorkOrderStatusType[] As = WorkOrderStatusType.values();
        if(As.length <= x){
            return WO_UNKNOWN;
        }else if(x < 0){
            return WO_UNKNOWN;
        }
        return As[x];
    }
}
