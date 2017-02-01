package com.spstudio.modules.member.bean.request;

/**
 * Created by Soul on 2016/12/1.
 */
public class MemberTypeJsonBean{
    public MemberTypeJsonBean(){
    }

    private String member_type_id;

    private String member_type_name;

    private String member_type_description;

    public String getMember_type_name() {
        return member_type_name;
    }

    public void setMember_type_name(String memberTypeName) {
        member_type_name = memberTypeName;
    }

    public String getMember_type_description() {
        return member_type_description;
    }

    public void setMember_type_description(String memberTypeDescription) {
        member_type_description = memberTypeDescription;
    }

    public String getMember_type_id() {
        return member_type_id;
    }

    public void setMember_type_id(String member_type_id) {
        this.member_type_id = member_type_id;
    }
}
