package com.spstudio.modules.member.bean.request;

import com.spstudio.modules.common.bean.Select2OptionJsonBean;
import com.spstudio.modules.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/1/24.
 */
public class SimpleMemberJsonBeanUtil {
    public static Select2OptionJsonBean toJsonBean(Member member) {
        Select2OptionJsonBean jsonBean = new Select2OptionJsonBean();
        jsonBean.setId(member.getMemberId());
        jsonBean.setText(member.getMemberName());
        return jsonBean;
    }

    public static List<Select2OptionJsonBean> toJsonBeanList(List<Member> memberList) {
        List<Select2OptionJsonBean> jsonBeanList = new ArrayList<Select2OptionJsonBean>();
        for (Member member : memberList){
            jsonBeanList.add(toJsonBean(member));
        }
        return jsonBeanList;
    }
}
