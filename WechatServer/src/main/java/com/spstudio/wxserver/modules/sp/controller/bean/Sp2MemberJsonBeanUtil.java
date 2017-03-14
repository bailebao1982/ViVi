package com.spstudio.wxserver.modules.sp.controller.bean;

import com.spstudio.modules.member.dto.MemberJsonBean;
import com.spstudio.modules.sp.entity.ServiceProvider;

/**
 * Created by Soul on 2017/3/14.
 */
public class Sp2MemberJsonBeanUtil {
    public static MemberJsonBean toMemberJsonBean(ServiceProvider serviceProvider){
        MemberJsonBean userinfo = new MemberJsonBean();
        userinfo.setMember_wechat(serviceProvider.getSpWechatId());
        userinfo.setMember_sex(serviceProvider.getSpSex());
        userinfo.setMember_name(serviceProvider.getSpName());
        userinfo.setMember_id(serviceProvider.getServiceProviderId());
        userinfo.setMember_no(serviceProvider.getSpWorkNo());

        return userinfo;
    }
}
