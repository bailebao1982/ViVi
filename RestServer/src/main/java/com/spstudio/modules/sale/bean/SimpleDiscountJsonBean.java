package com.spstudio.modules.sale.bean;

/**
 * Created by Soul on 2017/1/24.
 */
public class SimpleDiscountJsonBean {
    private String  discount_id;
    private int     discount_type;
    private String  discount_member_type_id;
    private String  discount_product_type_id;
    private String  discount_product_id;

    private float  discount_value;

    public int getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(int discount_type) {
        this.discount_type = discount_type;
    }

    public String getDiscount_member_type_id() {
        return discount_member_type_id;
    }

    public void setDiscount_member_type_id(String discount_member_type_id) {
        this.discount_member_type_id = discount_member_type_id;
    }

    public String getDiscount_product_type_id() {
        return discount_product_type_id;
    }

    public void setDiscount_product_type_id(String discount_product_type_id) {
        this.discount_product_type_id = discount_product_type_id;
    }

    public String getDiscount_product_id() {
        return discount_product_id;
    }

    public void setDiscount_product_id(String discount_product_id) {
        this.discount_product_id = discount_product_id;
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
