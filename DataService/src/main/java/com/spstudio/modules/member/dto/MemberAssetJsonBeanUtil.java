package com.spstudio.modules.member.dto;

import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.product.dto.PackageJsonBeanUtil;
import com.spstudio.modules.product.dto.ProductJsonBeanUtil;

/**
 * Created by Soul on 2017/1/23.
 */
public class MemberAssetJsonBeanUtil {
    public static MemberAssetJsonBean toJsonBean(MemberAsset asset){
        MemberAssetJsonBean assetJsonBean = new MemberAssetJsonBean();

        assetJsonBean.setAsset_id(asset.getMemberAssetId());
        assetJsonBean.setAsset_type(asset.getAssetType());
        assetJsonBean.setCount(asset.getCount());
        assetJsonBean.setDeposit(asset.getDeposit());
        assetJsonBean.setCreate_date(asset.getCreationDate().toString());
        assetJsonBean.setAsset_product(ProductJsonBeanUtil.toJsonBean(asset.getProduct()));
        assetJsonBean.setAsset_package(PackageJsonBeanUtil.toJsonBean(asset.getProductPackage()));
        return assetJsonBean;
    }
}
