package com.spstudio.modules.product.dto;

/**
 * Created by Soul on 2017/1/5.
 */
public class PackageProductJsonBean {
    private int product_count;
    private ProductJsonBean product;

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }

    public ProductJsonBean getProduct() {
        return product;
    }

    public void setProduct(ProductJsonBean product) {
        this.product = product;
    }
}
