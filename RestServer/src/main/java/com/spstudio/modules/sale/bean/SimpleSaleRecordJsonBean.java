package com.spstudio.modules.sale.bean;

/**
 * Created by Soul on 2017/1/24.
 */
public class SimpleSaleRecordJsonBean {
    private String sale_member_id;

    private int  sale_record_type; //  * 0 -- product, 1 -- package, 2 -- deposit

    private String sale_product_id;

    private String sale_package_id;

    private int    sale_count; // can be used as deposit

    private String saler;

    private int sale_payment_method; //

    private float sale_price;   // deposit

    public String getSale_member_id() {
        return sale_member_id;
    }

    public void setSale_member_id(String sale_member_id) {
        this.sale_member_id = sale_member_id;
    }

    public int getSale_record_type() {
        return sale_record_type;
    }

    public void setSale_record_type(int sale_record_type) {
        this.sale_record_type = sale_record_type;
    }

    public String getSale_product_id() {
        return sale_product_id;
    }

    public void setSale_product_id(String sale_product_id) {
        this.sale_product_id = sale_product_id;
    }

    public String getSale_package_id() {
        return sale_package_id;
    }

    public void setSale_package_id(String sale_package_id) {
        this.sale_package_id = sale_package_id;
    }

    public int getSale_count() {
        return sale_count;
    }

    public void setSale_count(int sale_count) {
        this.sale_count = sale_count;
    }

    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler;
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
