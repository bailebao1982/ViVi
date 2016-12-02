/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.member.dao;

import com.vivi.modules.member.entity.Member;
import com.vivi.common.search.SearchCriteria;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface MemberDAO {
    public List<Member> getAllMembers();
    
    public Member findMemberByMemberId(String memberId);
    
    public Member addMember(Member member);
    
    public boolean removeMember(Member member);
    
    public Member updateMember(Member member);
    
    public List<Member> queryForPage(int offset, int length, SearchCriteria criteria);
    
    public int getAllRowCount();
}
