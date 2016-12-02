/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.member.service;

import com.vivi.modules.member.entity.Member;
import com.vivi.common.search.Page;
import com.vivi.common.search.SearchCriteria;

/**
 *
 * @author wewezhu
 */
public interface MemberService {
    public void addMember(Member member);
    
    public Member findMemberByMemberId(String memberId);
    
    public boolean removeMember(Member member);
    
    public boolean updateMember(Member member);
    
    public Page<Member> queryForPage(int currentPage, int pageSize, SearchCriteria sc);
}
