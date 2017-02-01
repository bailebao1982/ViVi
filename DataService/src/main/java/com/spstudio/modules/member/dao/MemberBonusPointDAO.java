package com.spstudio.modules.member.dao;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberBonusPoint;

/**
 * Created by Soul on 2017/1/17.
 */
public interface MemberBonusPointDAO {
    public MemberBonusPoint findBonusPointOfMemeber(Member member);
    public MemberBonusPoint addBonusPointRecord(MemberBonusPoint bonusPoint);
    public MemberBonusPoint updateBonusPoint(MemberBonusPoint bonusPoint);
    public void zapBonusPoint(MemberBonusPoint bonusPoint);
}
