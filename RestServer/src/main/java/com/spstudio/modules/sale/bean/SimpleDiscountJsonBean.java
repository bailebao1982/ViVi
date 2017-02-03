package com.spstudio.modules.sale.bean;

/**
 * Created by Soul on 2017/1/24.
 */
public class SimpleDiscountJsonBean {
    private String  discount_id;
    private int     discount_type;
    private String discount_member_type;
    private String discount_product_type;
    private String discount_product;

    private float  discount_value;

    public int getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(int discount_type) {
        this.discount_type = discount_type;
    }

    public String getDiscount_member_type() {
        return discount_member_type;
    }

    public void setDiscount_member_type(String discount_member_type) {
        this.discount_member_type = discount_member_type;
    }

    public String getDiscount_product_type() {
        return discount_product_type;
    }

    public void setDiscount_product_type(String discount_product_type) {
        this.discount_product_type = discount_product_type;
    }

    public String getDiscount_product() {
        return discount_product;
    }

    public void setDiscount_product(String discount_product) {
        this.discount_product = discount_product;
    }

    public String getDiscount_id() {

        return discount_id;
    }

    public void setDiscount_id(String discount_id) {
        this.discount_id = discount_id;
    }

    public float getDiscount_value() {
        return discount_value;
    }

    public void setDiscount_value(float discount_value) {
        this.discount_value = discount_value;
    }
}
