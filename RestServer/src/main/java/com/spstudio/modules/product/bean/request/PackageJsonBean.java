package com.spstudio.modules.product.bean.request;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/5.
 */
public class PackageJsonBean {

    private String package_id;

    private String package_serialno;

    private String package_name;

    private String package_price;

    private String package_start_date;

    private String package_end_date;

    private String package_note;

    private String package_content;

    private List<PackageProductJsonBean> package_products;

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getPackage_serialno() {
        return package_serialno;
    }

    public void setPackage_serialno(String package_serialno) {
        this.package_serialno = package_serialno;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getPackage_start_date() {
        return package_start_date;
    }

    public void setPackage_start_date(String package_start_date) {
        this.package_start_date = package_start_date;
    }

    public String getPackage_end_date() {
        return package_end_date;
    }

    public void setPackage_end_date(String package_end_date) {
        this.package_end_date = package_end_date;
    }

    public String getPackage_note() {
        return package_note;
    }

    public void setPackage_note(String package_note) {
        this.package_note = package_note;
    }

    public List<PackageProductJsonBean> getPackage_products() {
        return package_products;
    }

    public void setPackage_products(List<PackageProductJsonBean> package_products) {
        this.package_products = package_products;
    }

    public String getPackage_content() {
        final int maxCntLen = 3;
        if(this.package_products.isEmpty()){
            return "";
        }else{
            List<String> contentList = new ArrayList<String>();
            for (int i = 0; i < maxCntLen && i < this.package_products.size(); i++){
                PackageProductJsonBean product = this.package_products.get(i);
                String cnt = String.format("%s: %d%s",
                        product.getProduct().getProduct_name(),
                        product.getProduct_count(),
                        product.getProduct().getProduct_uom());
                contentList.add(cnt);
            }

            String content = StringUtils.join(contentList, ",");
            if(maxCntLen < this.package_products.size()){
                content = content + "...";
            }
            return content;
        }
    }
}
