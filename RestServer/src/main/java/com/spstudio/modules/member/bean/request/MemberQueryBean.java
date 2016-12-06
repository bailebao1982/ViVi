/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.member.bean.request;

/**
 *
 * @author wewezhu
 */
public class MemberQueryBean {
    private int page;
    
    private int page_size;
    
    private String name;
    
    private String type;

    private String telphone;
    
    private String email;
    
    private String reg_start_date;
    
    private String reg_end_date;
    
    public MemberQueryBean(){
        
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

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReg_start_date() {
        return reg_start_date;
    }

    public void setReg_start_date(String start_date) {
        this.reg_start_date = start_date;
    }

    public String getReg_end_date() {
        return reg_end_date;
    }

    public void setReg_end_date(String end_date) {
        this.reg_end_date = end_date;
    }
    
}
