package com.spstudio.modules.product.bean.request;

import java.util.List;

/**
 * Created by Soul on 2017/1/6.
 */
public class PackageProductListBean {
    private List<PackageProductJsonBean> products;

    public List<PackageProductJsonBean> getProducts() {
        return products;
    }

    public void setProducts(List<PackageProductJsonBean> products) {
        this.products = products;
    }
}
