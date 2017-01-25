package com.spstudio.modules.member.dao.impl;

import com.spstudio.modules.member.dao.MemberBonusPointDAO;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberBonusPoint;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by Soul on 2017/1/17.
 */
public class MemberBonusPointDAOImpl implements MemberBonusPointDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public MemberBonusPoint findBonusPointOfMemeber(Member member) {
        String hql = "from MemberBonusPoint where memberId=:member_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("member_id", member.getMemberId());
        List<MemberBonusPoint> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public MemberBonusPoint addBonusPointRecord(MemberBonusPoint bonusPoint) {
        Date now = new Date(System.currentTimeMillis());
        bonusPoint.setCreationDate(now);
        bonusPoint.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().save(bonusPoint);
        return bonusPoint;
    }

    @Override
    public MemberBonusPoint updateBonusPoint(MemberBonusPoint bonusPoint) {
        Date now = new Date(System.currentTimeMillis());
        bonusPoint.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().update(bonusPoint);
        return bonusPoint;
    }

    @Override
    public void zapBonusPoint(MemberBonusPoint bonusPoint) {
        sessionFactory.getCurrentSession().delete(bonusPoint);
    }
}
