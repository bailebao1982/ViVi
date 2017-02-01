package com.spstudio.modules.common.bean;

/**
 * Created by Soul on 2017/1/24.
 */
public class Select2RequestBean {
    private String query;
    private int page;
    private int page_size;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

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
}
