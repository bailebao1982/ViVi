package com.spstudio.modules.workorder.bean;

import com.spstudio.modules.member.bean.request.MemberAssetJsonBean;

/**
 * Created by Soul on 2017/2/26.
 */
public class AssetCountJsonBean {
    private int count;
    private MemberAssetJsonBean asset;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public MemberAssetJsonBean getAsset() {
        return asset;
    }

    public void setAsset(MemberAssetJsonBean asset) {
        this.asset = asset;
    }
}
