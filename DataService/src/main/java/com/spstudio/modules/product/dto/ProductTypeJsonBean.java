package com.spstudio.modules.product.dto;

/**
 * Created by Soul on 2017/1/1.
 */
public class ProductTypeJsonBean {
    private String product_type_id;

    private String product_type_name;

    private String product_type_description;

    public String getProduct_type_name() {
        return product_type_name;
    }

    public void setProduct_type_name(String product_type_name) {
        this.product_type_name = product_type_name;
    }

    public String getProduct_type_description() {
        return product_type_description;
    }

    public void setProduct_type_description(String product_type_description) {
        this.product_type_description = product_type_description;
    }

    public String getProduct_type_id() {
        return product_type_id;
    }

    public void setProduct_type_id(String product_type_id) {
        this.product_type_id = product_type_id;
    }
}
