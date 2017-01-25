package com.spstudio.modules.member.bean.request;

import com.spstudio.modules.product.bean.request.PackageJsonBean;
import com.spstudio.modules.product.bean.request.ProductJsonBean;

/**
 * Created by Soul on 2017/1/23.
 */
public class MemberAssetJsonBean {
    private int             asset_type;
    private ProductJsonBean asset_product;
    private PackageJsonBean asset_package;
    private int             deposit;
    private int             count;
    private String          create_date;

    public int getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(int asset_type) {
        this.asset_type = asset_type;
    }

    public ProductJsonBean getAsset_product() {
        return asset_product;
    }

    public void setAsset_product(ProductJsonBean asset_product) {
        this.asset_product = asset_product;
    }

    public PackageJsonBean getAsset_package() {
        return asset_package;
    }

    public void setAsset_package(PackageJsonBean asset_package) {
        this.asset_package = asset_package;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
