package com.spstudio.modules.member.bean.request;

import java.util.ArrayList;

/**
 * Created by Soul on 2016/12/4.
 */
public class BatDelMemberBean {

    private ArrayList<String> member_id_list;

    public ArrayList<String> getMember_id_list() {
        return member_id_list;
    }

    public void setMember_id_list(ArrayList<String> member_id_list) {
        this.member_id_list = member_id_list;
    }
}
