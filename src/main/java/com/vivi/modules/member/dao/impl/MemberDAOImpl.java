/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.member.dao.impl;

import com.vivi.modules.member.dao.MemberDAO;
import com.vivi.modules.member.entity.Member;
import com.vivi.common.search.SearchCriteria;
import java.sql.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class MemberDAOImpl implements MemberDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public List<Member> getAllMembers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Member findMemberByMemberId(String memberId) {
        String hql = "from Member where memberId = :id and deleteFlag = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", memberId);
        List<Member> list = query.list();
        if(list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public Member addMember(Member member) {
        member.setCreationDate(new Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().saveOrUpdate(member);
        return member;
    }

    @Override
    public boolean removeMember(Member member) {
        member.setLastUpdateDate(new Date(System.currentTimeMillis()));
        member.setDeleteFlag(1);
        // Just update delete flag
        sessionFactory.getCurrentSession().update(member);
        return true;
    }

    @Override
    public boolean removeMemberList(List<String> memberIdList){
        String hql = "update Member set deleteFlag = 1, lastUpdateDate = :updateDate where memberId in :member_ids";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("updateDate", new Date(System.currentTimeMillis()));
        query.setParameterList("member_ids", memberIdList);
        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public Member updateMember(Member member) {
        member.setLastUpdateDate(new Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().update(member);
        
        return member;
    }

    @Override
    public List<Member> queryForPage(int offset, int length, SearchCriteria criteria) {
        List<Member> entitylist = null;
        try{
            StringBuffer queryString = new StringBuffer();
            queryString.append("from Member where deleteFlag = 0");
            
            for(String key:criteria.getItemMap().keySet()){
                queryString.append(" and ");
                 queryString.append(criteria.getItemMap().get(key).getSearchCriteriaItem());  
            }
            
            Query query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
            query.setFirstResult(offset);
            query.setMaxResults(length);
            entitylist = query.list();
            
        }catch(RuntimeException re){
            throw re;
        }
        
        return entitylist;
    }

    @Override
    public int getAllRowCount() {
        Long count = (Long)sessionFactory.getCurrentSession().createQuery("select count(1) from Member where deleteFlag = 0").uniqueResult();
        return count.intValue();
    }
    
}
