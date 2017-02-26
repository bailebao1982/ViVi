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
import com.spstudio.modules.product.entity.ProductPackage;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;

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
    public MemberAsset addAsset(MemberAsset asset){
        Date now = new Date(System.currentTimeMillis());
        asset.setCreationDate(now);
        asset.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().save(asset);

        return asset;
    }

    @Override
    public boolean removeAsset(MemberAsset asset) {
        asset.setDeleteFlag(1);

        Date now = new Date(System.currentTimeMillis());
        asset.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().update(asset);
        return true;
    }

    @Override
    public boolean removeAssetList(List<String> assetIdList) {
        String hql = "update MemberAsset set deleteFlag = 1, lastUpdateDate = :updateDate where memberAssetId in :asset_ids";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("updateDate", new Date(System.currentTimeMillis()));
        query.setParameterList("asset_ids", assetIdList);
        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public boolean removeAssetOfMember(Member member) {
        String hql = "update MemberAsset set deleteFlag = 1, lastUpdateDate = :updateDate where memberId = :member_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("updateDate", new Date(System.currentTimeMillis()));
        query.setParameter("member_id", member.getMemberId());
        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public MemberAsset updateAsset(MemberAsset asset) {
        Date now = new Date(System.currentTimeMillis());
        asset.setLastUpdateDate(now);

        sessionFactory.getCurrentSession().update(asset);
        return asset;
    }

    @Override
    public MemberAsset findAssetById(String assetId) {
        String hql = "from MemberAsset where memberAssetId = :asset_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("asset_id", assetId);
        List<MemberAsset> list = query.list();
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<MemberAsset> findAssetOfMember(Member member){
        String hql = "from MemberAsset where memberId = :member_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("member_id", member.getMemberId());
        List<MemberAsset> list = query.list();
        return list;
    }

    @Override
    public List<MemberAsset> findProductAssetOfMember(Member member, Product product) {
        String hql = "from MemberAsset where memberId = :member_id and productId = :product_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("member_id", member.getMemberId());
        query.setString("product_id", product.getProductId());
        List<MemberAsset> list = query.list();
        return list;
    }

    @Override
    public MemberAsset findDepositAssetOfMember(Member member) {
        String hql = "from MemberAsset where memberId = :member_id and assetType = 2";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("member_id", member.getMemberId());
        List<MemberAsset> list = query.list();
        if(list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public void zapAsset(MemberAsset asset) {
        this.sessionFactory.getCurrentSession().delete(asset);
    }

    @Override
    public boolean zapAssetOfMember(Member member) {
        String hql = "delete from MemberAsset where memberId = :member_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("member_id", member.getMemberId());
        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public boolean removePackageAsset(String packageId) {
        String hql = "update MemberAsset set deleteFlag = 1 where productPackageId = :package_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("package_id", packageId);
        int result = query.executeUpdate();
        return (result > 0);
    }

    @Override
    public List<MemberAsset> updatePackageAsset(String packageId, int count) {
        String hql = "from MemberAsset where productPackageId = :package_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("package_id", packageId);
        List<MemberAsset> list = query.list();

        for (MemberAsset asset: list){
            asset.setCount(count);
            updateAsset(asset);
        }

        return list;
    }

    @Override
    public boolean zapPackageAsset(String packageId) {
        String hql = "delete from MemberAsset where productPackageId = :package_id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("package_id", packageId);
        int result = query.executeUpdate();
        return (result > 0);
    }
}