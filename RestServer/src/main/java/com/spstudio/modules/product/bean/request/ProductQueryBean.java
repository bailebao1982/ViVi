package com.spstudio.modules.product.bean.request;

/**
 * Created by Soul on 2017/1/1.
 */
public class ProductQueryBean {
    private int page;

    private int page_size;

    private String name;

    private String type;

    private float price_start;

    private float price_end;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice_start() {
        return price_start;
    }

    public void setPrice_start(float price_start) {
        this.price_start = price_start;
    }

    public float getPrice_end() {
        return price_end;
    }

    public void setPrice_end(float price_end) {
        this.price_end = price_end;
    }
}
