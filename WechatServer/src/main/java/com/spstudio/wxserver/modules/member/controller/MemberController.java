package com.spstudio.wxserver.modules.member.controller;

import com.spstudio.wxserver.common.controller.WxBaseController;
import com.spstudio.wxserver.common.util.ReturnModel;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soul on 2016/12/7.
 */
@Controller
@RequestMapping("/member")
public class MemberController extends WxBaseController {

    @Autowired
    protected WxMpService wxMpService;

    /**
     * 通过code获得基本用户信息
     * 详情请见: http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     *
     * @param response
     * @param code     code
     */
    @RequestMapping(value = "/myinfo")
    public ModelAndView getOAuth2UserInfo(HttpServletResponse response, @RequestParam(value = "code") String code) {
        ModelAndView mav = new ModelAndView();
        WxMpOAuth2AccessToken accessToken;
        WxMpUser wxMpUser;
        try {
            accessToken = this.wxMpService.oauth2getAccessToken(code);
            wxMpUser = this.wxMpService.getUserService()
                    .userInfo(accessToken.getOpenId(), "zh_CN");

            Map<String,String> map = new HashMap<String, String>();
            map.put("name", wxMpUser.getNickname());
            map.put("openid", wxMpUser.getOpenId());
            map.put("sex", wxMpUser.getSex());
            map.put("city", wxMpUser.getCity());
            map.put("image",  wxMpUser.getHeadImgUrl());
            mav.addObject("userinfo", map);
            mav.setViewName("userinfo");

        } catch (WxErrorException e) {
            String msg = "错误：" + e.getError().toString();
            mav.addObject("message", msg);
            mav.setViewName("fail");

            this.logger.error(e.getError().toString());
        }
        return mav;
    }
}
