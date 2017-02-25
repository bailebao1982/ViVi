/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.dao;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface MemberAssetDAO {
    public List<MemberAsset> findAssetOfMember(Member member);

    public List<MemberAsset> findProductAssetOfMember(Member member, Product product);

    public MemberAsset findDepositAssetOfMember(Member member);

    public MemberAsset addAsset(MemberAsset asset);

    public boolean removeAsset(MemberAsset asset);

    public boolean removePackageAsset(String packageId);

    public boolean removeAssetList(List<String> assetIdList);

    public boolean removeAssetOfMember(Member member);

    public MemberAsset updateAsset(MemberAsset asset);

    public List<MemberAsset> updatePackageAsset(String packageId, int count);

    //public List<MemberAsset> queryForPage(int offset, int length, SearchCriteria criteria);

    public void zapAsset(MemberAsset asset);

    public boolean zapPackageAsset(String packageId);

    public boolean zapAssetOfMember(Member member);
}
