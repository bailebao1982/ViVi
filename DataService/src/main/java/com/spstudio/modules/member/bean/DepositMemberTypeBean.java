package com.spstudio.modules.member.bean;

import com.spstudio.modules.member.entity.MemberType;

/**
 * Created by Soul on 2017/1/20.
 */
public class DepositMemberTypeBean {
    private int deposit;

    private MemberType memberType;

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }
}
