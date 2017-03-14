package com.spstudio.wxserver.modules.member.controller;

import com.spstudio.common.utils.DateUtils;
import com.spstudio.common.utils.StringUtils;
import com.spstudio.modules.member.dto.MemberJsonBean;
import com.spstudio.modules.member.dto.MemberJsonBeanUtil;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.permission.entity.LoginUser;
import com.spstudio.modules.permission.service.PermissionService;
import com.spstudio.modules.sp.entity.SPInviteCode;
import com.spstudio.modules.sp.entity.ServiceProvider;
import com.spstudio.modules.sp.service.ServiceProviderService;
import com.spstudio.modules.workorder.entity.WorkOrder;
import com.spstudio.modules.workorder.service.WorkOrderService;
import com.spstudio.wxserver.common.controller.WxBaseController;
import com.spstudio.wxserver.modules.member.bean.UserRegisterBean;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Soul on 2016/12/7.
 */
@Controller
@RequestMapping("/member")
public class MemberController extends WxBaseController {

    @Autowired
    private MemberService memberService;

//    @Autowired
//    private PermissionService permissionService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    @Autowired
    protected WxMpService wxMpService;

    private ModelAndView __getMembershipPage(Member member){
        ModelAndView mav = new ModelAndView();
        MemberJsonBean userinfo = MemberJsonBeanUtil.toJsonBean(member);
        mav.addObject("userinfo", userinfo);
        mav.setViewName("main");
        return mav;
    }

    /**
     *  response to view event, return my profile webpage
     */
    @RequestMapping(value = "/entry",
            method = RequestMethod.GET)
    public ModelAndView getOAuth2UserInfo(@RequestParam(value = "code") String code) {
        WxMpOAuth2AccessToken accessToken;
        WxMpUser wxMpUser;

        try {
            accessToken = this.wxMpService.oauth2getAccessToken(code);
            wxMpUser = this.wxMpService.getUserService()
                    .userInfo(accessToken.getOpenId(), "zh_CN");

            Member member = memberService.findMemberByWechatId(wxMpUser.getOpenId());
            if(member != null){
                // should show my info page
                return __getMembershipPage(member);
            } else{
                // should lead him to register page
                MemberJsonBean userinfo = new MemberJsonBean();

                userinfo.setMember_name(wxMpUser.getNickname());
                userinfo.setMember_wechat(wxMpUser.getOpenId());
                userinfo.setMember_sex(wxMpUser.getSex());

                String location = String.format(
                        "%s %s %s",
                        wxMpUser.getCity(),
                        wxMpUser.getProvince(),
                        wxMpUser.getCountry()
                );
                userinfo.setMember_address(location);
                userinfo.setMember_profilepic(wxMpUser.getHeadImgUrl());

                return _getChoosePage(userinfo);
            }
        } catch (WxErrorException e) {
            ModelAndView mav = new ModelAndView();
            String msg = "错误：" + e.getError().toString();
            mav.addObject("message", msg);
            mav.setViewName("common/error");

            this.logger.error(e.getError().toString());
            return mav;
        }
    }

    @RequestMapping(value = "/home/{member_id}",
            method = RequestMethod.GET)
    public ModelAndView home(@PathVariable String member_id){
        Member member = memberService.findMemberByMemberId(member_id);
        if(member != null){
            MemberJsonBean userinfo =
                    MemberJsonBeanUtil.toJsonBean(member);
            ModelAndView mav = new ModelAndView();
            mav.addObject("userinfo", userinfo);
            mav.setViewName("main");
            return mav;
        }else{
            String msg = "错误：无法找到该会员，member_id: " + member_id;
            return _getErrorView(msg);
        }
    }

    private ModelAndView _getChoosePage(MemberJsonBean userinfo){
        ModelAndView mav = new ModelAndView();
        mav.addObject("userinfo", userinfo);
        mav.setViewName("choose");
        return mav;
    }

    // According to the map
    private ModelAndView _getRegisterPage(MemberJsonBean userinfo){
        ModelAndView mav = new ModelAndView();
        mav.addObject("userinfo", userinfo);
        mav.setViewName("register");
        return mav;
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.GET)
    public ModelAndView register(ModelAndView mav,
                                 @RequestParam String nickname,
                                 @RequestParam String gender,
                                 @RequestParam String headImgUrl,
                                 @RequestParam String openId,
                                 @RequestParam String address){
        MemberJsonBean userinfo = new MemberJsonBean();
        userinfo.setMember_name(nickname);
        userinfo.setMember_sex(gender);
        userinfo.setMember_wechat(openId);
        userinfo.setMember_profilepic(headImgUrl);
        userinfo.setMember_address(address);

        mav.addObject("userinfo", userinfo);

        mav.setViewName("register");
        return mav;
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ModelAndView doRegister(UserRegisterBean registerBean){

        SPInviteCode spInviteCode = serviceProviderService.getEffectiveInviteCode(registerBean.getInvite_code());
        if(spInviteCode == null){
            return _getErrorView("对不起， 您的邀请码不存在！");
        }

        Member newMember = new Member();

        newMember.setWeixinId(registerBean.getOpenId());
        newMember.setSex(registerBean.getGender());
        newMember.setMemberName(registerBean.getNickname());
        newMember.setProfilePicture(registerBean.getHeaderImgUrl());

        try {
            newMember.setBirthDay(StringUtils.str2Date(registerBean.getBirthday()));
        } catch (ParseException e) {

        }

        newMember.setAddress(registerBean.getAddress());
        newMember.setMobile(registerBean.getTelphone());

        MemberType defaultMembType = memberService.getDefaultMemberType();
        newMember.setMemberType(defaultMembType);
        newMember.setCreationDate(DateUtils.getDateNow());

        Member addedMember = memberService.addMember(newMember);
//
//        LoginUser loginUser = permissionService.getLoginUserByLoginName(addedMember.getMemberId());
//        if(loginUser != null) {
//            loginUser.setLoginPassword(registerBean.getPassword());
//            permissionService.updateLoginUser(loginUser);
//        }

        return __getMembershipPage(addedMember);
    }

    @RequestMapping(value = "/binding",
            method = RequestMethod.GET)
    public ModelAndView binding(ModelAndView mav,
                                  @RequestParam String nickname,
                                  @RequestParam String gender,
                                  @RequestParam String headImgUrl,
                                  @RequestParam String openId,
                                  @RequestParam String address){
        MemberJsonBean userinfo = new MemberJsonBean();
        userinfo.setMember_name(nickname);
        userinfo.setMember_sex(gender);
        userinfo.setMember_wechat(openId);
        userinfo.setMember_profilepic(headImgUrl);
        userinfo.setMember_address(address);

        mav.addObject("userinfo", userinfo);

        mav.setViewName("binding");
        return mav;
    }

    @RequestMapping(value = "/binding",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ModelAndView doBinding(UserRegisterBean registerBean){
        Member newMember = memberService.findMemberByMemberNo(registerBean.getMemberno());
        if(newMember == null){
            newMember = memberService.findMemberByMemberTelphone(registerBean.getMemberno());
            if(newMember == null){
                String msg = "错误：无法找到要绑定的会员, 会员编号【或者手机号】 " + registerBean.getMemberno();
                return _getErrorView(msg);
            }
        }

        newMember.setWeixinId(registerBean.getOpenId());
        newMember.setProfilePicture(registerBean.getHeaderImgUrl());

        memberService.updateMember(newMember);

//        LoginUser loginUser = permissionService.getLoginUserByMemberId(newMember.getMemberId());
//        if(loginUser != null) {
//            loginUser.setLoginPassword(registerBean.getPassword());
//            permissionService.updateLoginUser(loginUser);
//        }

        return __getMembershipPage(newMember);
    }

    @RequestMapping(value = "/myinfo/{member_id}",
            method = RequestMethod.GET)
    public ModelAndView getMyInfo(@PathVariable String member_id){
        Member member = memberService.findMemberByMemberId(member_id);
        if(member != null){
            MemberJsonBean userinfo =
                    MemberJsonBeanUtil.toJsonBean(member);
            ModelAndView mav = new ModelAndView();
            mav.addObject("userinfo", userinfo);
            mav.setViewName("/info/myinfo");
            return mav;
        }else{
            String msg = "错误：无法找到该会员，member_id: " + member_id;
            return _getErrorView(msg);
        }
    }

    private ModelAndView _getErrorView(String msg){
        //
        // TODO:: MISSING USERINFO HERE
        //
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", msg);
        mav.setViewName("common/error");

        this.logger.error(msg);
        return mav;
    }

    @RequestMapping(value = "/update/{member_id}",
            method = RequestMethod.POST)
    public ModelAndView updateProfile(@PathVariable String member_id, @RequestBody UserRegisterBean updateBean){
        Member member = memberService.findMemberByMemberId(member_id);
        if(member != null){
            try {
                member.setBirthDay(StringUtils.str2Date(updateBean.getBirthday()));
            } catch (ParseException e) {
                logger.error("Birthday format error!");
                logger.error(e.getMessage());
            }
            member.setMobile(updateBean.getTelphone());
            member.setAddress(updateBean.getAddress());
            member.setMemberName(updateBean.getNickname());

            MemberJsonBean userinfo =
                    MemberJsonBeanUtil.toJsonBean(member);
            ModelAndView mav = new ModelAndView();
            mav.addObject("userinfo", userinfo);
            mav.setViewName("/info/myinfo");
            return mav;
        }else{
            String msg = "错误：无法找到该会员，member_id: " + member_id;
            return _getErrorView(msg);
        }
    }
}
