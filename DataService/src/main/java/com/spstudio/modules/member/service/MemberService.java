/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.service;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface MemberService {
    public void addMember(Member member);
    
    public Member findMemberByMemberId(String memberId);

    public Member findMemberByWechatId(String nickname);
    
    public boolean removeMember(Member member);

    public boolean removeMemberList(List<String> memberIdList);

    public boolean updateMember(Member member);
    
    public Page<Member> queryForPage(int currentPage, int pageSize, SearchCriteria sc);
    
    public void zapMember(Member member);
}
