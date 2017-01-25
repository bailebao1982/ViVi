package com.spstudio.modules.sale.bean;

import com.spstudio.modules.member.bean.request.MemberTypeJsonBean;
import com.spstudio.modules.product.bean.request.ProductJsonBean;
import com.spstudio.modules.product.bean.request.ProductTypeJsonBean;
import com.spstudio.modules.product.entity.ProductType;

/**
 * Created by Soul on 2017/1/22.
 */
public class DiscountJsonBean {
    private String discount_id;

    private MemberTypeJsonBean discount_member_type;

    private int  discount_type;

    private ProductJsonBean discount_product;

    private ProductTypeJsonBean discount_product_type;

    private float discount_value;

    private String create_date;

    public String getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(String discount_id) {
        this.discount_id = discount_id;
    }

    public MemberTypeJsonBean getDiscount_member_type() {
        return discount_member_type;
    }

    public void setDiscount_member_type(MemberTypeJsonBean discount_member_type) {
        this.discount_member_type = discount_member_type;
    }

    public ProductJsonBean getDiscount_product() {
        return discount_product;
    }

    public void setDiscount_product(ProductJsonBean discount_product) {
        this.discount_product = discount_product;
    }

    public ProductTypeJsonBean getDiscount_product_type() {
        return discount_product_type;
    }

    public void setDiscount_product_type(ProductTypeJsonBean discount_product_type) {
        this.discount_product_type = discount_product_type;
    }

    public float getDiscount_value() {
        return discount_value;
    }

    public void setDiscount_value(float discount_value) {
        this.discount_value = discount_value;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(int discount_type) {
        this.discount_type = discount_type;
    }
}
