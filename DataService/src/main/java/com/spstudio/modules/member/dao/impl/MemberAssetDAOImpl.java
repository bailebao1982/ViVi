/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.dao.impl;

import com.spstudio.modules.member.dao.MemberAssetDAO;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.product.entity.Product;
import org.hibernate.SessionFactory;

/**
 *
 * @author wewezhu
 */
public class MemberAssetDAOImpl implements MemberAssetDAO{

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public MemberAsset addMemeberProductAsset(Member member, Product product, int count) {
        MemberAsset asset = new MemberAsset();
        asset.setMemeber(member);
        asset.setCount(count);
        asset.setProduct(product);
        
        sessionFactory.getCurrentSession().saveOrUpdate(asset);
        
        return asset;
    }

    @Override
    public MemberAsset addMemeberDepositAsset(Member member, int deposit) {
        MemberAsset asset = new MemberAsset();
        asset.setMemeber(member);
        asset.setDeposit(deposit);
        
        sessionFactory.getCurrentSession().saveOrUpdate(asset);
        
        return asset;
    }
}