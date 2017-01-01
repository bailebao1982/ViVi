package com.spstudio.modules.product.bean.request;

import java.util.ArrayList;

/**
 * Created by Soul on 2017/1/1.
 */
public class BatDelProductBean {
    private ArrayList<String> product_id_list;

    public ArrayList<String> getProduct_id_list() {
        return product_id_list;
    }

    public void setProduct_id_list(ArrayList<String> product_id_list) {
        this.product_id_list = product_id_list;
    }
}
