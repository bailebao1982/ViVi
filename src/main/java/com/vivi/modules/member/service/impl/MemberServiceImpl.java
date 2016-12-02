/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.member.service.impl;

import com.vivi.modules.member.dao.MemberDAO;
import com.vivi.modules.member.entity.Member;
import com.vivi.common.search.Page;
import com.vivi.common.search.SearchCriteria;
import com.vivi.modules.member.service.MemberService;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public class MemberServiceImpl implements MemberService {
    private MemberDAO memberDAO;

    public MemberDAO getMemberDAO() {
        return memberDAO;
    }

    public void setMemberDAO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public void addMember(Member member) {
        memberDAO.addMember(member);
    }

    @Override
    public Member findMemberByMemberId(String memberId) {
        return memberDAO.findMemberByMemberId(memberId);
    }

    @Override
    public boolean removeMember(Member member) {
        return memberDAO.removeMember(member);
    }

    @Override
    public boolean updateMember(Member member) {
        memberDAO.updateMember(member);
        return true;
    }

    @Override
    public Page<Member> queryForPage(int currentPage, int pageSize, SearchCriteria sc) {
        Page<Member> page = new Page<Member>();
        //总记录数
        int allRow = memberDAO.getAllRowCount();
        //当前页开始记录
        int offset = page.countOffset(currentPage,pageSize);  
        //分页查询结果集
        List<Member> list = memberDAO.queryForPage(offset, pageSize,sc);

        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);
        
        return page;
    }
    
}
