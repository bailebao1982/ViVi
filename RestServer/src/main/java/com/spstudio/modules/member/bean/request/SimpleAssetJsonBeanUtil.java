package com.spstudio.modules.member.bean.request;

import com.spstudio.modules.common.bean.Select2OptionJsonBean;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.service.AssetType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/2/26.
 */
public class SimpleAssetJsonBeanUtil {
    public static Select2OptionJsonBean toJsonBean(MemberAsset asset) {
        Select2OptionJsonBean jsonBean = new Select2OptionJsonBean();
        AssetType assetType = AssetType.fromInteger(asset.getAssetType());

        String text = "未知资产";

        switch (assetType){
            case ASSET_PACKAGE_TYPE:
                text = String.format("%s:%s",
                        asset.getProductPackage().getPackageName(),
                        asset.getProduct().getProductName());
                break;
            case ASSET_PRODUCT_TYPE:
                text = String.format("%s", asset.getProduct().getProductName());
                break;
            case ASSET_DEPOSIT_TYPE:
                text = String.format("充值余额");
                break;
        }


        jsonBean.setId(asset.getMemberAssetId());
        jsonBean.setText(text);
        return jsonBean;
    }

    public static List<Select2OptionJsonBean> toJsonBeanList(List<MemberAsset> assets) {
        List<Select2OptionJsonBean> jsonBeanList = new ArrayList<Select2OptionJsonBean>();
        for (MemberAsset asset : assets){
            jsonBeanList.add(toJsonBean(asset));
        }
        return jsonBeanList;
    }
}
