/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.service.impl;

import com.spstudio.modules.member.dao.MemberDAO;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.permission.dao.PermissionDAO;
import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.service.impl.SysContent;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wewezhu
 */
public class MemberServiceImpl implements MemberService {
    private MemberDAO memberDAO;
    
    private PermissionDAO permissionDAO;

    public MemberDAO getMemberDAO() {
        return memberDAO;
    }

    public void setMemberDAO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public PermissionDAO getPermissionDAO() {
        return permissionDAO;
    }

    public void setPermissionDAO(PermissionDAO permissionDAO) {
        this.permissionDAO = permissionDAO;
    }
    
    

    @Override
    public void addMember(Member member) {
        memberDAO.addMember(member);
        LoginUser loginUser = new LoginUser();
        loginUser.setCreationTime(new Date(System.currentTimeMillis()));
        loginUser.setLoginCount(0);
        loginUser.setLoginPassword(SysContent.DEFAULT_PWD);
        loginUser.setLoginUser(member.getMemberName());
        loginUser.setMemberId(member.getMemberId());
        permissionDAO.addLoginUser(loginUser);
        
    }

    @Override
    public Member findMemberByMemberId(String memberId) {
        return memberDAO.findMemberByMemberId(memberId);
    }

    @Override
    public Member findMemberByWechatId(String wechatId){
        return memberDAO.findMemberByWechatId(wechatId);
    }

    @Override
    public boolean removeMember(Member member) {
        return memberDAO.removeMember(member);
    }

    @Override
    public boolean removeMemberList(List<String> memberIdList) {
        return memberDAO.removeMemberList(memberIdList);
    }

    @Override
    public boolean updateMember(Member member) {
        memberDAO.updateMember(member);
        return true;
    }

    @Override
    public Page<Member> queryForPage(int currentPage, int pageSize, SearchCriteria sc) {
        Page<Member> page = new Page<Member>();
        //当前页开始记录
        int offset = page.countOffset(currentPage,pageSize);  
        //分页查询结果集
        List<Member> list = memberDAO.queryForPage(offset, pageSize,sc);
        //总记录数
        int allRow = list.size();

        page.setPageNo(currentPage);
        page.setPageSize(pageSize);
        page.setTotalRecords(allRow);
        page.setList(list);
        
        return page;
    }

    @Override
    public void zapMember(Member member) {
       memberDAO.zapMember(member);
    }
    
}
