/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.service;

import com.spstudio.modules.member.bean.DepositMemberTypeBean;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface MemberService {

    // Member Type Service
    public void addMemberType(MemberType type);

    public List<MemberType> listAllMemberType();

    public MemberType findMemberTypeById(String memberTypeId);

    public MemberType findMemberTypeByType(String memberType);

    public boolean removeMemberType(MemberType type);

    public boolean updateMemberType(MemberType member);

    // Member Service
    public void addMember(Member member);
    
    public Member findMemberByMemberId(String memberId);

    public Member findMemberByWechatId(String nickname);
    
    public boolean removeMember(Member member);

    public boolean removeMemberList(List<String> memberIdList);

    public boolean updateMember(Member member);
    
    public Page<Member> queryForPage(int currentPage, int pageSize, SearchCriteria sc);
    
    public void zapMember(Member member);

    // Member Asset Service
    public List<MemberAsset> findAssetOfMember(Member member);
    public List<MemberAsset> findProductAssetOfMember(Member member, Product product);

    public MemberAsset getDepositAssetOfMember(Member member);

    public MemberAsset addDepositAsset(Member member,int deposit);
    public MemberAsset increaseOrAddDepositAsset(Member member, int deposit);

    public MemberAsset addProductAsset(Member member, Product product, int count);
    public List<MemberAsset> addPackageAsset(Member member, ProductPackage productPackage, int count);

    public boolean removeProductAsset(MemberAsset asset);
    public boolean removeDepositAsset(MemberAsset asset);
    public boolean removePackageAsset(String packageId);
    public boolean removeAssetList(List<String> assetIdList);
    public boolean removeAssetOfMember(Member member);

    public MemberAsset updateProductAsset(MemberAsset asset);
    public MemberAsset updateDepositAsset(MemberAsset asset);
    public List<MemberAsset> updatePackageAsset(String packageId, int count);

    public boolean zapProductAsset(MemberAsset asset);
    public boolean zapDepositAsset(MemberAsset asset);
    public boolean zapPackageAsset(String packageId);
    public boolean zapAssetOfMember(Member member);

    // Bonus Point
    public int getBonusPoint(Member member);

    public void increaseBonusPoint(Member member, int point);

    public void updateBonusPoint(Member member, int newPoint);

    // Member Config Service
    public MemberType getDepositMemberTypeRank(int deposit);
    public boolean createDepositMemberTypeRank(List<DepositMemberTypeBean> beanList);
}
