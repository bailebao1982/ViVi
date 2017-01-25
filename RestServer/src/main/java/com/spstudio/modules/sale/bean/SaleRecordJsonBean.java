package com.spstudio.modules.sale.bean;

import com.spstudio.modules.member.bean.request.MemberJsonBean;
import com.spstudio.modules.product.bean.request.PackageJsonBean;
import com.spstudio.modules.product.bean.request.ProductJsonBean;

/**
 * Created by Soul on 2017/1/19.
 */
public class SaleRecordJsonBean {
    private String sale_record_id;

    private MemberJsonBean sale_member;

    private int  sale_record_type; //  * 0 -- product, 1 -- package, 2 -- deposit

    private ProductJsonBean sale_product;
    private PackageJsonBean sale_package;

    private int    sale_count;

    private String saler;

    private int sale_payment_method; //

    private float sale_price;   // deposit

    private String sale_date;

    //private String product_update_date;

    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler;
    }

    public String getSale_date() {
        return sale_date;
    }

    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }

//    public String getProduct_update_date() {
//        return product_update_date;
//    }
//
//    public void setProduct_update_date(String product_update_date) {
//        this.product_update_date = product_update_date;
//    }

    public String getSale_record_id() {
        return sale_record_id;
    }

    public void setSale_record_id(String sale_record_id) {
        this.sale_record_id = sale_record_id;
    }

    public int getSale_record_type() {
        return sale_record_type;
    }

    public void setSale_record_type(int sale_record_type) {
        this.sale_record_type = sale_record_type;
    }

    public ProductJsonBean getSale_product() {
        return sale_product;
    }

    public void setSale_product(ProductJsonBean sale_product) {
        this.sale_product = sale_product;
    }

    public PackageJsonBean getSale_package() {
        return sale_package;
    }

    public void setSale_package(PackageJsonBean sale_package) {
        this.sale_package = sale_package;
    }

    public int getSale_count() {
        return sale_count;
    }

    public void setSale_count(int sale_count) {
        this.sale_count = sale_count;
    }

    public MemberJsonBean getSale_member() {
        return sale_member;
    }

    public void setSale_member(MemberJsonBean sale_member) {
        this.sale_member = sale_member;
    }

    public int getSale_payment_method() {
        return sale_payment_method;
    }

    public void setSale_payment_method(int sale_payment_method) {
        this.sale_payment_method = sale_payment_method;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }
}
