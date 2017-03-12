package com.spstudio.modules.workorder.dto;

import com.spstudio.modules.member.dto.MemberJsonBean;
import com.spstudio.modules.sp.dto.EmployeeJsonBean;

import java.util.List;

/**
 * Created by Soul on 2017/2/13.
 */
public class WorkOrderJsonBean {
    private String workorder_id;
    private String note;
    private MemberJsonBean member;
    private EmployeeJsonBean employee;
    private List<AssetCountJsonBean> assets;
    private String assets_summary;
    private int confirmed;
    private int rate;
    private String confirm_comment;
    private String create_date;
    private String effective_end_date;
    private String service_date;
    private String service_location;
    private String confirm_date;
    private String base64Img1;
    private String base64Img2;
    private String base64Img3;
    private String base64Img4;
    private String base64Img5;

    public String getWorkorder_id() {
        return workorder_id;
    }

    public void setWorkorder_id(String workorder_id) {
        this.workorder_id = workorder_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public MemberJsonBean getMember() {
        return member;
    }

    public void setMember(MemberJsonBean member) {
        this.member = member;
    }

    public EmployeeJsonBean getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeJsonBean employee) {
        this.employee = employee;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getConfirm_date() {
        return confirm_date;
    }

    public void setConfirm_date(String confirm_date) {
        this.confirm_date = confirm_date;
    }

    public String getBase64Img1() {
        return base64Img1;
    }

    public void setBase64Img1(String base64Img1) {
        this.base64Img1 = base64Img1;
    }

    public String getBase64Img2() {
        return base64Img2;
    }

    public void setBase64Img2(String base64Img2) {
        this.base64Img2 = base64Img2;
    }

    public String getBase64Img3() {
        return base64Img3;
    }

    public void setBase64Img3(String base64Img3) {
        this.base64Img3 = base64Img3;
    }

    public String getBase64Img4() {
        return base64Img4;
    }

    public void setBase64Img4(String base64Img4) {
        this.base64Img4 = base64Img4;
    }

    public String getBase64Img5() {
        return base64Img5;
    }

    public void setBase64Img5(String base64Img5) {
        this.base64Img5 = base64Img5;
    }

    public String getService_location() {
        return service_location;
    }

    public void setService_location(String service_location) {
        this.service_location = service_location;
    }

    public String getConfirm_comment() {
        return confirm_comment;
    }

    public void setConfirm_comment(String confirm_comment) {
        this.confirm_comment = confirm_comment;
    }

    public String getEffective_end_date() {
        return effective_end_date;
    }

    public void setEffective_end_date(String effective_end_date) {
        this.effective_end_date = effective_end_date;
    }

    public List<AssetCountJsonBean> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetCountJsonBean> assets) {
        this.assets = assets;
    }

    public String getAssets_summary() {
        return assets_summary;
    }

    public void setAssets_summary(String assets_summary) {
        this.assets_summary = assets_summary;
    }
}
