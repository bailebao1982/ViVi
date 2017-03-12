package com.spstudio.modules.sp.dao.impl;

import com.spstudio.modules.sp.dao.InviteCodeDAO;
import com.spstudio.modules.sp.entity.SPInviteCode;
import com.spstudio.modules.sp.entity.ServiceProvider;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Soul on 2017/3/12.
 */
public class InviteCodeDAOImpl implements InviteCodeDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<String> getAllSPInviteCodes(ServiceProvider sp) {
        String hql = "from SPInviteCode where serviceProviderId = :serviceProviderId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("serviceProviderId", sp.getServiceProviderId());
        return query.list();
    }

    @Override
    public String getEffectiveInviteCode(ServiceProvider sp, Date effectiveDate) {
        String hql = "from SPInviteCode where serviceProviderId = :serviceProviderId and :eff_date >= effectiveStartDate and :eff_date <= effectiveEndDate";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("serviceProviderId", sp.getServiceProviderId());
        query.setParameter("eff_date", effectiveDate);
        List<SPInviteCode> entitylist = query.list();
        if(entitylist.size() > 0){
            return entitylist.get(0).getInviteCode();
        }
        return null;
    }

    @Override
    public SPInviteCode getInviteCodeEntity(String inviteCode) {
        String hql = "from SPInviteCode where inviteCode = :invite_code";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("invite_code", inviteCode);
        List<SPInviteCode> entitylist = query.list();
        if(entitylist.size() > 0){
            return entitylist.get(0);
        }
        return null;
    }

    @Override
    public SPInviteCode getEffectiveInviteCodeEntity(String inviteCode, Date effectiveDate) {
        String hql = "from SPInviteCode where inviteCode = :invite_code and :eff_date >= effectiveStartDate and :eff_date <= effectiveEndDate";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("invite_code", inviteCode);
        query.setParameter("eff_date", effectiveDate);
        List<SPInviteCode> entitylist = query.list();
        if(entitylist.size() > 0){
            return entitylist.get(0);
        }
        return null;
    }
}
