package com.spstudio.modules.common.bean;

import com.spstudio.modules.common.bean.Select2OptionJsonBean;
import com.spstudio.modules.member.entity.Member;

/**
 * Created by Soul on 2017/1/24.
 */
public class Select2OptionJsonBeanUtil {
    public static Select2OptionJsonBean toJsonBean(String id, String text) {
        Select2OptionJsonBean jsonBean = new Select2OptionJsonBean();
        jsonBean.setId(id);
        jsonBean.setText(text);
        return jsonBean;
    }
}
