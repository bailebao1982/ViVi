package com.spstudio.modules.product.bean.request;

/**
 * Created by Soul on 2017/1/5.
 */
public class PackageProductJsonBean {
    private int count;
    private ProductJsonBean product;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ProductJsonBean getProduct() {
        return product;
    }

    public void setProduct(ProductJsonBean product) {
        this.product = product;
    }
}
