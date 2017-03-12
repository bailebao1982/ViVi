package com.spstudio.modules.member.dto;

import com.spstudio.modules.product.dto.PackageJsonBean;
import com.spstudio.modules.product.dto.ProductJsonBean;

/**
 * Created by Soul on 2017/1/23.
 */
public class MemberAssetJsonBean {
    private String          asset_id;
    private int             asset_type;
    private ProductJsonBean asset_product;
    private PackageJsonBean asset_package;
    private int             deposit;
    private int             count;
    private String          create_date;

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

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
