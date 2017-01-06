package com.spstudio.modules.product.bean.request;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/5.
 */
public class PackageJsonBean {

    private String id;

    private String serialno;

    private String name;

    private String price;

    private String start_date;

    private String end_date;

    private String note;

    private String content;

    private List<PackageProductJsonBean> products;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<PackageProductJsonBean> getProducts() {
        return products;
    }

    public void setProducts(List<PackageProductJsonBean> products) {
        this.products = products;
    }

    public String getContent() {
        final int maxCntLen = 3;
        if(this.products.isEmpty()){
            return "";
        }else{
            List<String> contentList = new ArrayList<String>();
            for (int i = 0; i < maxCntLen && i < this.products.size(); i++){
                PackageProductJsonBean product = this.products.get(i);
                String cnt = String.format("%s: %d%s",
                        product.getProduct().getProduct_name(),
                        product.getCount(),
                        product.getProduct().getProduct_uom());
                contentList.add(cnt);
            }

            String content = StringUtils.join(contentList, ",");
            if(maxCntLen < this.products.size()){
                content = content + "...";
            }
            return content;
        }
    }
}
