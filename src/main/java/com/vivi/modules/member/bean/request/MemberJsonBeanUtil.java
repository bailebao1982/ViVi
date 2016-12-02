/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivi.modules.member.bean.request;

import com.vivi.modules.member.entity.Member;
import com.vivi.modules.member.service.MemberTypeService;

/**
 *
 * @author wewezhu
 */
public class MemberJsonBeanUtil {
    
    public static MemberJsonBean toJsonBean(Member member){
        MemberJsonBean memberJsonBean = new MemberJsonBean();
        memberJsonBean.setMember_address(member.getAddress());
        if(member.getBirthDay() != null)
            memberJsonBean.setMember_birthday(member.getBirthDay().toString());
        memberJsonBean.setMember_hobby(member.getHobby());
        if(member.getCreationDate()!=null)
            memberJsonBean.setMember_inputdate(member.getCreationDate().toString());
        memberJsonBean.setMember_job(member.getJob());
        memberJsonBean.setMember_name(member.getMemberName());
        memberJsonBean.setMember_note(member.getRemark());
        memberJsonBean.setMember_sex(member.getSex());
        memberJsonBean.setMember_telphone(member.getMobile());
        memberJsonBean.setMember_wechat(member.getWeixinId());
        memberJsonBean.setMember_type(member.getMemberType().getTypeName());
        
        return memberJsonBean;
    }
    
    public static Member toEntityBean(MemberJsonBean memberJsonBean, MemberTypeService service){
        Member member = new Member();
      
        member.setAddress(memberJsonBean.getMember_address());

        if(memberJsonBean.getMember_birthday() != null &&
           memberJsonBean.getMember_birthday() != "")
            member.setBirthDay(java.sql.Date.valueOf(memberJsonBean.getMember_birthday()));

        if(memberJsonBean.getMember_inputdate() != null &&
           memberJsonBean.getMember_inputdate() != "")
            member.setCreationDate(java.sql.Date.valueOf(memberJsonBean.getMember_inputdate()));

        member.setMemberName(memberJsonBean.getMember_name());
        member.setHobby(memberJsonBean.getMember_hobby());



        member.setJob(memberJsonBean.getMember_job());
        member.setMobile(memberJsonBean.getMember_telphone());
        member.setRemark(memberJsonBean.getMember_note());
        member.setSex(memberJsonBean.getMember_sex());

        String membType = memberJsonBean.getMember_type();
        if(!membType.isEmpty()){
            member.setMemberType(service.findMemberTypeByType(membType));
        }else{
            // 'normal' type should be setup before
            member.setMemberType(service.findMemberTypeByType("normal"));
        }

        member.setWeixinId(memberJsonBean.getMember_wechat());
        return member;
    }
}
