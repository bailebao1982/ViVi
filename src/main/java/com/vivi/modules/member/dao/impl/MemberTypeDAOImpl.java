package com.vivi.modules.member.dao.impl;

import com.vivi.modules.member.dao.MemberTypeDAO;
import com.vivi.modules.member.entity.MemberType;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.util.List;

/**
 * Created by Soul on 2016/12/1.
 */
public class MemberTypeDAOImpl implements MemberTypeDAO {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public MemberType addMemberType(MemberType type) {
        type.setCreationDate(new Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().saveOrUpdate(type);
        return type;
    }

    @Override
    public List<MemberType> listMemberType() {
        String hql = "from MemberType as membertype";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List<MemberType> list = query.list();
        return list;
    }

    @Override
    public MemberType findMemberTypeById(String memberId) {
        String hql = "from MemberType as membertype where membertype.memberTypeId = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", memberId);
        List<MemberType> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public MemberType findMemberTypeByType(String memberType) {
        String hql = "from MemberType as membertype where membertype.typeName = :type";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("type", memberType);
        List<MemberType> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public boolean removeMemberType(MemberType type) {
        sessionFactory.getCurrentSession().delete(type);
        return true;
    }

    @Override
    public boolean updateMemberType(MemberType type) {
        type.setLastUpdateDate(new Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().update(type);
        return true;
    }
}
