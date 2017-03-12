package com.spstudio.modules.member.dto;


/**
 * Created by Soul on 2017/2/4.
 */
public class MemberDepositBean {
    private int deposit;
    private MemberJsonBean member;

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public MemberJsonBean getMember() {
        return member;
    }

    public void setMember(MemberJsonBean member) {
        this.member = member;
    }
}
