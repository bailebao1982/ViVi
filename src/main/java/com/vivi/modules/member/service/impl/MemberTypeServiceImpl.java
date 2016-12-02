package com.vivi.modules.member.service.impl;

import com.vivi.modules.member.dao.MemberTypeDAO;
import com.vivi.modules.member.entity.MemberType;
import com.vivi.modules.member.service.MemberTypeService;

import java.util.List;

/**
 * Created by Soul on 2016/12/1.
 */
public class MemberTypeServiceImpl implements MemberTypeService{
    private MemberTypeDAO memberTypeDAO;

    public MemberTypeDAO getMemberTypeDAO() {
        return memberTypeDAO;
    }

    public void setMemberTypeDAO(MemberTypeDAO memberTypeDAO) {
        this.memberTypeDAO = memberTypeDAO;
    }

    @Override
    public void addMemberType(MemberType type) {
        memberTypeDAO.addMemberType(type);
    }

    @Override
    public List<MemberType> listAllMemberType() {
        return memberTypeDAO.listMemberType();
    }

    @Override
    public MemberType findMemberTypeById(String memberId) {
        return memberTypeDAO.findMemberTypeById(memberId);
    }

    @Override
    public MemberType findMemberTypeByType(String memberType) {
        return memberTypeDAO.findMemberTypeByType(memberType);
    }

    @Override
    public boolean removeMemberType(MemberType type) {
        return memberTypeDAO.removeMemberType(type);
    }

    @Override
    public boolean updateMemberType(MemberType member) {
        return memberTypeDAO.updateMemberType(member);
    }
}
