package com.spstudio.modules.member.dao;

import com.spstudio.modules.member.entity.MemberType;

import java.util.List;

/**
 * Created by Soul on 2016/12/1.
 */
public interface MemberTypeDAO {
    public MemberType addMemberType(MemberType type);

    public List<MemberType> listMemberType();

    public MemberType findMemberTypeById(String memberTypeId);

    public MemberType findMemberTypeByType(String memberType);

    public boolean removeMemberType(MemberType type);

    public boolean updateMemberType(MemberType member);
}
