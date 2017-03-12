package com.spstudio.modules.member.dto;

import java.util.List;

/**
 * Created by Soul on 2017/1/23.
 */
public class MemberAssetListJsonBean {
    private MemberJsonBean asset_member;

    private List<MemberAssetJsonBean>  assets;

    public MemberJsonBean getAsset_member() {
        return asset_member;
    }

    public void setAsset_member(MemberJsonBean asset_member) {
        this.asset_member = asset_member;
    }

    public List<MemberAssetJsonBean> getAssets() {
        return assets;
    }

    public void setAssets(List<MemberAssetJsonBean> assets) {
        this.assets = assets;
    }
}
