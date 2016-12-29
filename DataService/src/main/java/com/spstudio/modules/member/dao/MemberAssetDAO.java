/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.dao;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.product.entity.Product;

/**
 *
 * @author wewezhu
 */
public interface MemberAssetDAO {
    public MemberAsset addMemeberProductAsset(Member member,Product product,int count);
    
    public MemberAsset addMemeberDepositAsset(Member member,int deposit);
    
    public void zapMemeberAsset(MemberAsset asset);
    
    
}
