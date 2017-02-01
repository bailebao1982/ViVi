package com.spstudio.modules.member.bean.request;

/**
 * Created by Soul on 2017/1/22.
 */
public class MemberBonusPointJsonBean {
    private MemberJsonBean member;
    int member_bonus_point;

    public MemberJsonBean getMember() {
        return member;
    }

    public void setMember(MemberJsonBean member) {
        this.member = member;
    }

    public int getMember_bonus_point() {
        return member_bonus_point;
    }

    public void setMember_bonus_point(int member_bonus_point) {
        this.member_bonus_point = member_bonus_point;
    }
}
