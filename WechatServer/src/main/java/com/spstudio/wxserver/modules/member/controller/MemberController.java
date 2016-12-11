package com.spstudio.wxserver.modules.member.controller;

import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.member.service.MemberTypeService;
import com.spstudio.wxserver.common.controller.WxBaseController;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    private MemberService memberService;

    @Autowired
    private MemberTypeService memberTypeService;

    @Autowired
    protected WxMpService wxMpService;

    /**
     *  response to view event, return my profile webpage
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

            Member memeber = memberService.findMemberByWechatId(wxMpUser.getOpenId());
            if(memeber != null){
                String memberType = memeber.getMemberType().getTypeName();
                map.put("member_type",  memberType);
            } else{
                map.put("member_type",  "非注册用户");
            }

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
