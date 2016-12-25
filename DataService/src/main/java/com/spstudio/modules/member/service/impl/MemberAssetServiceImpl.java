/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.service.impl;

import com.spstudio.modules.member.dao.MemberAssetDAO;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.service.MemberAssetService;
import com.spstudio.modules.product.entity.Product;

/**
 *
 * @author wewezhu
 */
public class MemberAssetServiceImpl implements MemberAssetService{
    private MemberAssetDAO memeberAssetDAO;

    public MemberAssetDAO getMemeberAssetDAO() {
        return memeberAssetDAO;
    }

    public void setMemeberAssetDAO(MemberAssetDAO memeberAssetDAO) {
        this.memeberAssetDAO = memeberAssetDAO;
    }

    @Override
    public MemberAsset addMemeberProductAsset(Member member, Product product, int count) {
        return memeberAssetDAO.addMemeberProductAsset(member, product, count);
    }

    @Override
    public MemberAsset addMemeberDepositAsset(Member member, int deposit) {
      
        return memeberAssetDAO.addMemeberDepositAsset(member, deposit);
    }
    
    
}
