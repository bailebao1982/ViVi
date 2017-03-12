package com.spstudio.modules.workorder.dto;

/**
 * Created by Soul on 2017/2/13.
 */
public class WorkOrderConfirmJsonBean {
    private String workorder_id;
    private int rate;
    private String note;

    public String getWorkorder_id() {
        return workorder_id;
    }

    public void setWorkorder_id(String workorder_id) {
        this.workorder_id = workorder_id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
