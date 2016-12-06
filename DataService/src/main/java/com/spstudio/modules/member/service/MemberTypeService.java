package com.spstudio.modules.member.service;

import com.spstudio.modules.member.entity.MemberType;

import java.util.List;

/**
 * Created by Soul on 2016/12/1.
 */
public interface MemberTypeService {
    public void addMemberType(MemberType type);

    public List<MemberType> listAllMemberType();

    public MemberType findMemberTypeById(String memberId);

    public MemberType findMemberTypeByType(String memberType);

    public boolean removeMemberType(MemberType type);

    public boolean updateMemberType(MemberType member);

}
