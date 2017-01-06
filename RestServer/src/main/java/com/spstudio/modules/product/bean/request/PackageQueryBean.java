package com.spstudio.modules.product.bean.request;

/**
 * Created by Soul on 2017/1/5.
 */
public class PackageQueryBean {
    private int page;

    private int page_size;

    private String name;

    private String serialno;

    private float price_start;

    private float price_end;

    private String start_date;

    private String end_date;

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

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
