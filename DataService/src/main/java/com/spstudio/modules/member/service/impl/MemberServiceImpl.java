/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.service.impl;

import com.spstudio.common.service.SystemConfigService;
import com.spstudio.common.service.entity.SystemConfig;
import com.spstudio.common.utils.DateUtils;
import com.spstudio.modules.member.bean.DepositMemberTypeBean;
import com.spstudio.modules.member.bean.DepositMemberTypeBeanUtil;
import com.spstudio.modules.member.config.Configuration;
import com.spstudio.modules.member.dao.MemberAssetDAO;
import com.spstudio.modules.member.dao.MemberBonusPointDAO;
import com.spstudio.modules.member.dao.MemberDAO;
import com.spstudio.modules.member.dao.MemberTypeDAO;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.entity.MemberBonusPoint;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.member.service.AssetType;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.permission.dao.PermissionDAO;
import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.service.impl.SysContent;
import java.util.Date;
import java.util.List;
import com.spstudio.modules.product.entity.PackageProductMapping;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @author wewezhu
 */
public class MemberServiceImpl implements MemberService {
    private MemberDAO memberDAO;
    
    private PermissionDAO permissionDAO;

    private MemberBonusPointDAO bonusPointDAO;

    public MemberBonusPointDAO getBonusPointDAO() {
        return bonusPointDAO;
    }

    public void setBonusPointDAO(MemberBonusPointDAO bonusPointDAO) {
        this.bonusPointDAO = bonusPointDAO;
    }

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
    public Member addMember(Member member) {
        Member newMember = memberDAO.addMember(member);
        LoginUser loginUser = new LoginUser();
        loginUser.setCreationTime(new Date(System.currentTimeMillis()));
        loginUser.setLoginCount(0);
        loginUser.setLoginPassword(SysContent.DEFAULT_MEMB_PWD);
        loginUser.setLoginUser(member.getMemberName());
        loginUser.setMemberId(member.getMemberId());
        permissionDAO.addLoginUser(loginUser);
        return newMember;
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

    private MemberBonusPoint _createBonusPoint(Member member, int point){
        MemberBonusPoint bonusPoint = new MemberBonusPoint();
        bonusPoint.setMember(member);
        bonusPoint.setBonusPoint(point);

        bonusPointDAO.addBonusPointRecord(bonusPoint);

        return bonusPoint;
    }

    @Override
    public void increaseBonusPoint(Member member, int point) {
        MemberBonusPoint bonusPoint = bonusPointDAO.findBonusPointOfMemeber(member);
        if(bonusPoint == null){
            _createBonusPoint(member, point);
            return ;
        }
        bonusPoint.setBonusPoint(bonusPoint.getBonusPoint() + point);
        bonusPointDAO.updateBonusPoint(bonusPoint);
    }

    @Override
    public int getBonusPoint(Member member) {
        MemberBonusPoint bonusPoint = bonusPointDAO.findBonusPointOfMemeber(member);
        if(bonusPoint == null){
            _createBonusPoint(member, 0);
            return 0;
        }
        return bonusPoint.getBonusPoint();
    }

    @Override
    public void updateBonusPoint(Member member, int newPoint) {
        MemberBonusPoint bonusPoint = bonusPointDAO.findBonusPointOfMemeber(member);
        if(bonusPoint == null){
            _createBonusPoint(member, newPoint);
            return;
        }
        bonusPoint.setBonusPoint(newPoint);
        bonusPointDAO.updateBonusPoint(bonusPoint);
    }


    //
    // Member Type Service
    //
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
    public MemberType getDefaultMemberType() {
        return null;
    }

    @Override
    public List<MemberType> listAllMemberType() {
        return memberTypeDAO.listMemberType();
    }

    @Override
    public MemberType findMemberTypeById(String memberTypeId) {
        return memberTypeDAO.findMemberTypeById(memberTypeId);
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


    //
    // Member Asset Service
    //
    private MemberAssetDAO memberAssetDAO;

    public MemberAssetDAO getMemberAssetDAO() {
        return memberAssetDAO;
    }

    public void setMemberAssetDAO(MemberAssetDAO memberAssetDAO) {
        this.memberAssetDAO = memberAssetDAO;
    }

    private MemberAsset _getAsset(Member member, int assetType , Product product, ProductPackage pkg, int deposit, int count){
        MemberAsset asset = new MemberAsset();

        asset.setProduct(product);
        asset.setProductPackage(pkg);
        asset.setDeposit(deposit);

        asset.setAssetType(assetType);
        asset.setMember(member);
        asset.setCount(count);

        return asset;
    }

    @Override
    public MemberAsset findAssetById(String assetId) {
        return memberAssetDAO.findAssetById(assetId);
    }

    @Override
    public List<MemberAsset> findAssetOfMember(Member member) {
        return memberAssetDAO.findAssetOfMember(member);
    }

    @Override
    public List<MemberAsset> findProductAssetOfMember(Member member, Product product) {
        return memberAssetDAO.findProductAssetOfMember(member, product);
    }

    @Override
    public MemberAsset getDepositAssetOfMember(Member member) {
        return memberAssetDAO.findDepositAssetOfMember(member);
    }

    @Override
    public MemberAsset addProductAsset(Member member, Product product, int count) {
        List<MemberAsset> existAssets = memberAssetDAO.findProductAssetOfMember(member, product);
        MemberAsset foundAsset = null;
        for (MemberAsset existAsset : existAssets){
            if(existAsset.getProductPackage() == null){
                // There should be only one product asset
                foundAsset = existAsset;
                break;
            }
        }
        if(foundAsset != null){
            int original = foundAsset.getCount();
            foundAsset.setCount(original + count);
            foundAsset.setLastUpdateDate(DateUtils.getDateNow());
            return memberAssetDAO.updateAsset(foundAsset);
        }else{
            MemberAsset asset =
                    _getAsset(member, AssetType.ASSET_PRODUCT_TYPE.ordinal(), product, null, 0, count);
            return memberAssetDAO.addAsset(asset);
        }
    }

    @Override
    public List<MemberAsset> addPackageAsset(Member member, ProductPackage productPackage, int count) {
        List<MemberAsset> listProducts = new ArrayList<MemberAsset>();

        List<MemberAsset> existPkgAssets = memberAssetDAO.findPackageAssetOfMember(member, productPackage);

        Iterator iter = productPackage.getProductMappingSet().iterator();
        while (iter.hasNext()){
            PackageProductMapping mapping = (PackageProductMapping)iter.next();

            // Find current existing asset
            MemberAsset foundAsset = null;
            for (MemberAsset existAsset : existPkgAssets){
                if(existAsset.getProduct().getProductId().equalsIgnoreCase(
                        mapping.getProduct().getProductId()
                )){
                    // There should be only one product asset
                    foundAsset = existAsset;
                    break;
                }
            }
            if(foundAsset != null){
                int original = foundAsset.getCount();
                foundAsset.setCount(original + count);
                foundAsset.setLastUpdateDate(DateUtils.getDateNow());
                memberAssetDAO.updateAsset(foundAsset);
            }else {
                MemberAsset asset =  _getAsset(member, AssetType.ASSET_PACKAGE_TYPE.ordinal(), mapping.getProduct(), productPackage, 0, mapping.getCount() * count);
                MemberAsset retAsset = memberAssetDAO.addAsset(asset);
                listProducts.add(retAsset);
            }
        }
        return listProducts;
    }

    @Override
    public MemberAsset addDepositAsset(Member member, int deposit) {
        MemberAsset depositAsset = memberAssetDAO.findDepositAssetOfMember(member);
        if(depositAsset != null){
            int original = depositAsset.getDeposit();
            depositAsset.setDeposit(original + deposit);
            depositAsset.setLastUpdateDate(DateUtils.getDateNow());
            return memberAssetDAO.updateAsset(depositAsset);
        }else{
            MemberAsset asset =  _getAsset(member, AssetType.ASSET_DEPOSIT_TYPE.ordinal(), null, null, deposit, 0);
            return memberAssetDAO.addAsset(asset);
        }
    }

    @Override
    public MemberAsset increaseOrAddDepositAsset(Member member, int deposit){
        MemberAsset depositAsset = memberAssetDAO.findDepositAssetOfMember(member);
        if(depositAsset != null){
            int newDeposit = depositAsset.getDeposit() + deposit;
            depositAsset.setDeposit(newDeposit);
            return memberAssetDAO.updateAsset(depositAsset);
        }else{
            return addDepositAsset(member, deposit);
        }
    }

    @Override
    public boolean removeDepositAsset(MemberAsset asset) {
        if(asset.getAssetType() == AssetType.ASSET_PACKAGE_TYPE.ordinal()){
            return false;
        }else{
            return  memberAssetDAO.removeAsset(asset);
        }
    }

    @Override
    public boolean removeProductAsset(MemberAsset asset) {
        return removeDepositAsset(asset);
    }

    @Override
    public boolean removePackageAsset(String packageId) {
        return memberAssetDAO.removePackageAsset(packageId);
    }

    @Override
    public boolean removeAssetList(List<String> assetIdList) {
        return  memberAssetDAO.removeAssetList(assetIdList);
    }

    @Override
    public boolean removeAssetOfMember(Member member) {
        return memberAssetDAO.removeAssetOfMember(member);
    }

    @Override
    public MemberAsset updateProductAsset(MemberAsset asset) {
        if(asset.getAssetType() == AssetType.ASSET_PACKAGE_TYPE.ordinal()){
            return null;
        }else{
            return  memberAssetDAO.updateAsset(asset);
        }
    }

    @Override
    public MemberAsset updateDepositAsset(MemberAsset asset) {
        return updateProductAsset(asset);
    }

    @Override
    public List<MemberAsset> updatePackageAsset(String packageId, int count) {
        return memberAssetDAO.updatePackageAsset(packageId, count);
    }

    @Override
    public boolean zapProductAsset(MemberAsset asset) {
        if(asset.getAssetType() != AssetType.ASSET_PACKAGE_TYPE.ordinal()){
            memberAssetDAO.zapAsset(asset);
            return true;
        }
        return false;
    }

    @Override
    public boolean zapDepositAsset(MemberAsset asset) {
        return zapProductAsset(asset);
    }

    @Override
    public boolean zapPackageAsset(String packageId){
        return memberAssetDAO.zapPackageAsset(packageId);
    }

    @Override
    public boolean zapAssetOfMember(Member member) {
        return memberAssetDAO.removeAssetOfMember(member);
    }


    // Member Config service

    SystemConfigService configService;

    public SystemConfigService getConfigService() {
        return configService;
    }

    public void setConfigService(SystemConfigService configService) {
        this.configService = configService;
    }

    private List<DepositMemberTypeBean> depositMemberTypeBeanList = null;

    class DMTBeanComparator implements Comparator {
        public int compare(Object arg0, Object arg1) {
            DepositMemberTypeBean bean0 = (DepositMemberTypeBean)arg0;
            DepositMemberTypeBean bean1 = (DepositMemberTypeBean)arg1;

            return bean0.getDeposit() >= bean1.getDeposit() ? 1 : -1;
        }
    }

    @Override
    public MemberType getDepositMemberTypeRank(int deposit) {
        if(depositMemberTypeBeanList == null ||
                depositMemberTypeBeanList.size() == 0){
            depositMemberTypeBeanList = new ArrayList<DepositMemberTypeBean>();

            List<SystemConfig> configs = configService.findModuleConfig(
                    Configuration.MEMBER_MODULE_NAME,
                    Configuration.CONFIG_DEPOST_MEMBERTYPE);

            for (SystemConfig config : configs){
                DepositMemberTypeBean bean = DepositMemberTypeBeanUtil.toBean(config, this);
                if(bean != null){
                    depositMemberTypeBeanList.add(bean);
                }
            }

            Collections.sort(depositMemberTypeBeanList, new DMTBeanComparator());
        }

        int idx = 0;
        for(; idx < depositMemberTypeBeanList.size(); idx++){
            DepositMemberTypeBean bean = depositMemberTypeBeanList.get(idx);
            if(deposit < bean.getDeposit()){
                break;
            }
        }
        idx--;
        if(idx < 0) return null;
        return depositMemberTypeBeanList.get(idx).getMemberType();
    }

    @Override
    @Transactional
    public boolean createDepositMemberTypeRank(List<DepositMemberTypeBean> beanList){
        Collections.sort(beanList, new DMTBeanComparator());

        List<SystemConfig> configList = new ArrayList<>();
        for (DepositMemberTypeBean bean: beanList){
            SystemConfig config = DepositMemberTypeBeanUtil.toEntity(bean);
            configList.add(config);
        }

        // delete the original first
        configService.zapModuleConfig(
                Configuration.MEMBER_MODULE_NAME,
                Configuration.CONFIG_DEPOST_MEMBERTYPE
        );

        depositMemberTypeBeanList = null;

        for(SystemConfig config: configList){
            configService.addConfig(config);
        }
        depositMemberTypeBeanList = beanList;

        return true;
    }
}
