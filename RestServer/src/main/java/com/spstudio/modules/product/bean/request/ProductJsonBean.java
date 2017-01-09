package com.spstudio.modules.product.bean.request;

/**
 * Created by Soul on 2017/1/1.
 */
public class ProductJsonBean {

    private String product_id;

    private String product_name;

    private String product_serialno;

    private String product_type;

    private String product_uom;

    private String product_price;

    private String product_description;

    private boolean product_available;

    public boolean isProduct_available() {
        return product_available;
    }

    public void setProduct_available(boolean product_available) {
        this.product_available = product_available;
    }

    public String getProduct_create_date() {
        return product_create_date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setProduct_create_date(String product_create_date) {
        this.product_create_date = product_create_date;
    }

    private String product_create_date;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_serialno() {
        return product_serialno;
    }

    public void setProduct_serialno(String product_serialno) {
        this.product_serialno = product_serialno;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_uom() {
        return product_uom;
    }

    public void setProduct_uom(String product_uom) {
        this.product_uom = product_uom;
    }


}
