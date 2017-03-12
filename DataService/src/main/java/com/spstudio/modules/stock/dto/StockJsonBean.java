package com.spstudio.modules.stock.dto;

/**
 * Created by Soul on 2017/1/12.
 */
public class StockJsonBean {
    private String product_id;

    private String product_serialno;

    private String product_type;

    private String product_type_dscrp;

    private String product_name;

    private int product_stock;

    private boolean product_stock_isinfinite;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(int product_stock) {
        this.product_stock = product_stock;
    }

    public boolean isProduct_stock_isinfinite() {
        return product_stock_isinfinite;
    }

    public void setProduct_stock_isinfinite(boolean product_stock_isinfinite) {
        this.product_stock_isinfinite = product_stock_isinfinite;
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

    public String getProduct_type_dscrp() {
        return product_type_dscrp;
    }

    public void setProduct_type_dscrp(String product_type_dscrp) {
        this.product_type_dscrp = product_type_dscrp;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
