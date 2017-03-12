package com.spstudio.modules.member.dto;

/**
 * Created by Soul on 2016/12/1.
 */
public class MemberTypeJsonBean{
    public MemberTypeJsonBean(){
    }

    private String member_type_id;

    private String member_type_name;

    private String member_type_description;

    private int member_type_priority;

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

    public int getMember_type_priority() {
        return member_type_priority;
    }

    public void setMember_type_priority(int member_type_priority) {
        this.member_type_priority = member_type_priority;
    }
}
