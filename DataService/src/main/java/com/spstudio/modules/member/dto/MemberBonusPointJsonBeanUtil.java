package com.spstudio.modules.member.dto;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberBonusPoint;
import com.spstudio.modules.member.service.MemberService;

/**
 * Created by Soul on 2017/1/22.
 */
public class MemberBonusPointJsonBeanUtil {
    public static MemberBonusPointJsonBean toJsonBean(MemberJsonBean member, int bonusPoint){
        MemberBonusPointJsonBean mbpJsonBean = new MemberBonusPointJsonBean();

        mbpJsonBean.setMember(member);
        mbpJsonBean.setMember_bonus_point(bonusPoint);

        return mbpJsonBean;
    }

    public static MemberBonusPointJsonBean toJsonBean(MemberBonusPoint mbp){
        MemberBonusPointJsonBean mbpJsonBean = new MemberBonusPointJsonBean();

        mbpJsonBean.setMember(MemberJsonBeanUtil.toJsonBean(mbp.getMember()));
        mbpJsonBean.setMember_bonus_point(mbp.getBonusPoint());

        return mbpJsonBean;
    }

    public static MemberBonusPoint toEntityBean(MemberBonusPointJsonBean mbpJsonBean, MemberService service){
        MemberBonusPoint memberBonusPoint = new MemberBonusPoint();
        String memberId = mbpJsonBean.getMember().getMember_id();
        Member memberEntity = service.findMemberByMemberId(memberId);
        memberBonusPoint.setBonusPoint(mbpJsonBean.getMember_bonus_point());
        memberBonusPoint.setMember(memberEntity);
        return memberBonusPoint;
    }
}
