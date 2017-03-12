package com.spstudio.wxserver.modules.asset.controller;

import com.spstudio.modules.member.dto.*;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.service.AssetType;
import com.spstudio.modules.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soul on 2017/3/8.
 */
@Controller
@RequestMapping("/asset")
public class AssetController {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberService memberService;

    private ModelAndView _getErrorView(String msg){
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", msg);
        mav.setViewName("common/error");

        this.logger.error(msg);
        return mav;
    }

    @RequestMapping(value = "/myasset/{member_id}",
            method = RequestMethod.GET)
    public ModelAndView getMyAssets(@PathVariable String member_id) {
        Member member = memberService.findMemberByMemberId(member_id);
        if(member != null){
            MemberJsonBean memberJsonBean = MemberJsonBeanUtil.toJsonBean(member);
            int bonusPoint = memberService.getBonusPoint(member);
            memberJsonBean.setMember_bonusPoint(bonusPoint);

            List<MemberAsset> memberAssets = memberService.findAssetOfMember(member);

            MemberAssetJsonBean depositAsset = null;
            List<MemberAssetJsonBean> productAssets = new ArrayList<MemberAssetJsonBean>();
            List<MemberAssetJsonBean> pkgPrdtAssets = new ArrayList<MemberAssetJsonBean>();

            for (MemberAsset asset : memberAssets){
                int intAssetType = asset.getAssetType();
                AssetType assetType = AssetType.fromInteger(intAssetType);

                switch (assetType){
                    case ASSET_PRODUCT_TYPE: {
                        productAssets.add(
                                MemberAssetJsonBeanUtil.toJsonBean(asset)
                        );
                        break;
                    }
                    case ASSET_PACKAGE_TYPE: {
                        pkgPrdtAssets.add(
                                MemberAssetJsonBeanUtil.toJsonBean(asset)
                        );
                        break;
                    }
                    case ASSET_DEPOSIT_TYPE: {
                        depositAsset = MemberAssetJsonBeanUtil.toJsonBean(asset);
                        break;
                    }
                }
            }

            ModelAndView mav = new ModelAndView();
            mav.setViewName("/workorder/workorders");

            mav.addObject("userinfo", memberJsonBean);
            mav.addObject("depositAsset", depositAsset);
            mav.addObject("productAssets", productAssets);
            mav.addObject("pkgPrdtAssets", pkgPrdtAssets);

            return mav;
        }else{
            String msg = "错误：无法找到该会员，member_id: " + member_id;
            return _getErrorView(msg);
        }
    }

}
