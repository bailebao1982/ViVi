package com.spstudio.modules.member.bean.response;

import com.spstudio.modules.member.bean.request.MemberJsonBean;

/**
 * Created by Soul on 2017/2/4.
 */
public class MemberBonusPointBean {
    private int bonuspoint;

    private MemberJsonBean member;

    public int getBonuspoint() {
        return bonuspoint;
    }

    public void setBonuspoint(int bonuspoint) {
        this.bonuspoint = bonuspoint;
    }

    public MemberJsonBean getMember() {
        return member;
    }

    public void setMember(MemberJsonBean member) {
        this.member = member;
    }
}
