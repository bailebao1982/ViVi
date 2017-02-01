package com.spstudio.modules.common.bean;

import java.util.List;

/**
 * Created by Soul on 2017/1/24.
 */
public class Select2ResponseJsonBean {
    private int total_count;
    private List<Select2OptionJsonBean> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<Select2OptionJsonBean> getItems() {
        return items;
    }

    public void setItems(List<Select2OptionJsonBean> items) {
        this.items = items;
    }
}
