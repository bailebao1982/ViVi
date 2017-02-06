/*
 *
 * Class: MemberController
 * Description:
 *  This is the class act as the route for restful api
 *
 */
package com.spstudio.modules.member.controller;

import com.spstudio.common.response.ResponseMsgBeanFactory;
import com.spstudio.common.response.SuccessMessageBean;
import com.spstudio.common.response.ResponseBean;
import com.spstudio.modules.common.bean.Select2OptionJsonBean;
import com.spstudio.modules.common.bean.Select2RequestBean;
import com.spstudio.modules.common.bean.Select2ResponseJsonBean;
import com.spstudio.modules.common.bean.Select2ResponseJsonBeanUtil;
import com.spstudio.modules.member.bean.request.MemberJsonBean;
import com.spstudio.modules.member.bean.request.*;
import com.spstudio.modules.member.bean.response.MemberDepositBean;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberAsset;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchConditionEnum;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.search.SearchCriteriaItem;
import com.spstudio.modules.member.service.MemberService;

import javax.annotation.Resource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wewezhu
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource(name="memberService")
    private MemberService memberService;

    public MemberService getMemberService() {
        return memberService;
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(value = "/list_member_type",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean listMemberType(){
        List<MemberType> memberTypes = memberService.listAllMemberType();

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                memberTypes
        );
    }

    @RequestMapping(value = "/add_member_type",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addMemberType(@RequestBody MemberTypeJsonBean memberTypeJson){

        MemberType newMemberType = MemberTypeJsonBeanUtil.toEntityBean(memberTypeJson);
        memberService.addMemberType(newMemberType);
        return ResponseMsgBeanFactory.getSuccessResponseBean("会员类型创建成功");
    }

    @RequestMapping(value = "/del_member_type/{member_type_id}",
            method = RequestMethod.DELETE,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean delMemberType(@PathVariable String member_type_id){

        MemberType newMemberType = memberService.findMemberTypeById(member_type_id);
        if(newMemberType != null){
            memberService.removeMemberType(newMemberType);
            return ResponseMsgBeanFactory.getSuccessResponseBean("会员类型删除成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "1004",
                    "未能发现该会员分类，会员分类ID：" + member_type_id
            );
        }
    }
    
    @RequestMapping(value = "/add_member",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addMember(@RequestBody MemberJsonBean member){
        if(member.getMember_name().isEmpty() ||
           member.getMember_telphone().isEmpty()){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "1009",
                    "错误：会员必要的信息丢失。请检查 名称，手机号码"
            );
        }else{
            Member newMember = MemberJsonBeanUtil.toEntityBean(member, memberService);
            newMember.setCreationDate(new java.sql.Date(new Date().getTime()));
            try{
                memberService.addMember(newMember);
                int bonusPoint = member.getMember_bonusPoint();
                memberService.increaseBonusPoint(newMember, bonusPoint);

                return ResponseMsgBeanFactory.getSuccessResponseBean("会员创建成功");
            }catch (DataIntegrityViolationException ex){
                return ResponseMsgBeanFactory.getErrorResponseBean(
                        "1010",
                        "错误：该会员手机号码或者微信号码已经被注册"
                );
            }
        }
    }
    
    @RequestMapping(value = "/update_member/{member_id}",
            method = RequestMethod.POST,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean updateMember(@RequestBody MemberJsonBean memberJson,
                                                     @PathVariable String member_id){
        // Something might be wrong here
        Member updateMember = MemberJsonBeanUtil.toEntityBean(memberJson, memberService);
        updateMember.setMemberId(member_id);
        if(memberService.updateMember(updateMember)){
            int bonusPoint = memberJson.getMember_bonusPoint();
            memberService.updateBonusPoint(updateMember, bonusPoint);
            return ResponseMsgBeanFactory.getSuccessResponseBean("会员信息更新成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "1005",
                    "会员信息更新失败，会员ID：" + member_id
            );
        }
    }
    
    @RequestMapping(value = "/del_member/{member_id}",
            method = RequestMethod.DELETE,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean removeMember(@PathVariable String member_id){

        Member removeMember = memberService.findMemberByMemberId(member_id);
        if (removeMember == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "1006",
                    "会员信息删除失败，无法找到会员ID：" + member_id
            );
        }else{
            memberService.removeMember(removeMember);
            return ResponseMsgBeanFactory.getSuccessResponseBean("会员信息删除成功");
        }
    }

    @RequestMapping(value = "/batdel_member",
            method = RequestMethod.POST,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean batRemoveMembers(@RequestBody BatDelMemberBean batDelMemberJson){
        boolean result = memberService.removeMemberList(batDelMemberJson.getMember_id_list());
        if (result){
            return ResponseMsgBeanFactory.getSuccessResponseBean("会员删除成功");
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "1011",
                    "会员信息删除失败，会员列表中会员无一存在"
            );
        }
    }
    
    @RequestMapping(value = "/member_detail/{member_id}",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean getMemberByMemberId(@PathVariable String member_id){
       
        Member member = memberService.findMemberByMemberId(member_id);
        if(member != null){
            MemberJsonBean responseMemberJsonBean = MemberJsonBeanUtil.toJsonBean(member);
            int bonusPoint = memberService.getBonusPoint(member);
            responseMemberJsonBean.setMember_bonusPoint(bonusPoint);

            return ResponseMsgBeanFactory.getResponseBean(
                    true,
                    responseMemberJsonBean
            );
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "1008",
                    "会员信息查找失败，无法找到会员ID：" + member_id
            );
        }
    }
    
    @RequestMapping(value = "/list_memebers",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
      public @ResponseBody ResponseBean searchMemberByCondition(
              @RequestParam(value="page", required=true) int page,
              @RequestParam(value="page_size", required=true) int page_size,
              @RequestParam(value="name", required=false) String name,
              @RequestParam(value="type", required=false) String type,
              @RequestParam(value="telphone", required=false) String telphone,
              @RequestParam(value="email", required=false) String email,
              @RequestParam(value="reg_start_date", required=false) String reg_start_date,
              @RequestParam(value="reg_end_date", required=false) String reg_end_date){
        MemberQueryBean queryBean = new MemberQueryBean();
        queryBean.setPage(page);
        queryBean.setPage_size(page_size);
        queryBean.setName(name);
        queryBean.setType(type);
        queryBean.setTelphone(telphone);
        queryBean.setEmail(email);
        queryBean.setReg_start_date(reg_start_date);
        queryBean.setReg_end_date(reg_end_date);

        SearchCriteria sc = new SearchCriteria();
        if(queryBean.getName() != null &&
           !queryBean.getName().isEmpty())
            sc.addSearchCriterialItem("name",
                    new SearchCriteriaItem("memberName", queryBean.getName(), SearchConditionEnum.Like)
            );
        if(queryBean.getTelphone() != null &&
           !queryBean.getTelphone().isEmpty())
             sc.addSearchCriterialItem("telepone",
                     new SearchCriteriaItem("mobile",queryBean.getTelphone(), SearchConditionEnum.Equal)
             );

        if(queryBean.getEmail() != null &&
           !queryBean.getEmail().isEmpty())
            sc.addSearchCriterialItem("email",
                    new SearchCriteriaItem("email",queryBean.getEmail(), SearchConditionEnum.Like)
            );

        if(queryBean.getType() != null &&
           !queryBean.getType().isEmpty()) {
            MemberType membType = memberService.findMemberTypeByType(queryBean.getType());
            if(membType != null) {
                sc.addSearchCriterialItem("type",
                        new SearchCriteriaItem("memberTypeId", membType.getMemberTypeId(), SearchConditionEnum.Equal)
                );
            }
        }

        if(queryBean.getReg_start_date() != null &&
           !queryBean.getReg_start_date().isEmpty())
            sc.addSearchCriterialItem("start_date",
                    new SearchCriteriaItem("creationDate", queryBean.getReg_start_date(), SearchConditionEnum.LargeOrEqual)
            );

        if(queryBean.getReg_end_date()!=null &&
           !queryBean.getReg_end_date().isEmpty())
            sc.addSearchCriterialItem("end_date",
                    new SearchCriteriaItem("creationDate", queryBean.getReg_end_date(), SearchConditionEnum.SmallOrEqual)
            );
        
        Page<Member> resultPageBean = memberService.queryForPage(queryBean.getPage(), queryBean.getPage_size(), sc);
        Page<MemberJsonBean> returnPage = new Page<MemberJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        ArrayList<MemberJsonBean> returnArray = new ArrayList<MemberJsonBean>();
        for (Member memb : resultPageBean.getList()){
            int bonusPoint = memberService.getBonusPoint(memb);

            MemberJsonBean responseMemberJsonBean = MemberJsonBeanUtil.toJsonBean(memb);
            responseMemberJsonBean.setMember_bonusPoint(bonusPoint);

            returnArray.add(responseMemberJsonBean);
        }
        returnPage.setList(returnArray);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }

    @RequestMapping(value = "/list_memebers_for_select2",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody Select2ResponseJsonBean listMemebersForSelect2(
            @RequestParam(value="query", required = false) String query,
            @RequestParam(value="page", required=true) int page,
            @RequestParam(value="page_size", required=true) int page_size){
        SearchCriteria sc = new SearchCriteria();
        if(query != null && !query.isEmpty())
            sc.addSearchCriterialItem("name",
                    new SearchCriteriaItem("memberName", query, SearchConditionEnum.Like)
            );

        Page<Member> resultPageBean = memberService.queryForPage(page, page_size, sc);

        List<Select2OptionJsonBean> productJsonBeanList =
                SimpleMemberJsonBeanUtil.toJsonBeanList(resultPageBean.getList());

        Select2ResponseJsonBean returnBean = Select2ResponseJsonBeanUtil.toJsonBean(
                resultPageBean.getTotalRecords(),
                productJsonBeanList
        );

        return returnBean;
    }


    @RequestMapping(value = "/test",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    public @ResponseBody ResponseBean<SuccessMessageBean> test(){
        return ResponseMsgBeanFactory.getSuccessResponseBean("测试成功");
    }

    // Member Asset related
    @RequestMapping(value = "/list_member_assets",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean listMemberAssets(
            @RequestParam(value="page", required=true) int page,
            @RequestParam(value="page_size", required=true) int page_size,
            @RequestParam(value="member_name", required=false) String member_name){
        SearchCriteria sc = new SearchCriteria();
        if(member_name!= null &&
          !member_name.isEmpty())
            sc.addSearchCriterialItem("name",
                    new SearchCriteriaItem("memberName", member_name, SearchConditionEnum.Like)
            );

        Page<Member> resultPageBean = memberService.queryForPage(page, page_size, sc);

        List<MemberAssetListJsonBean> memberAssetListJsonBean = new ArrayList<MemberAssetListJsonBean>();
        for (Member memb : resultPageBean.getList()){
            MemberAssetListJsonBean assetListJsonBean = new MemberAssetListJsonBean();
            MemberJsonBean responseMemberJsonBean = MemberJsonBeanUtil.toJsonBean(memb);

            int bonusPoint = memberService.getBonusPoint(memb);
            responseMemberJsonBean.setMember_bonusPoint(bonusPoint);

            assetListJsonBean.setAsset_member(responseMemberJsonBean);

            List<MemberAssetJsonBean> assetList = new ArrayList<MemberAssetJsonBean>();
            List<MemberAsset> memberAssets = memberService.findAssetOfMember(memb);
            for (MemberAsset asset : memberAssets){
                MemberAssetJsonBean memberAssetJsonBean = MemberAssetJsonBeanUtil.toJsonBean(asset);
                assetList.add(memberAssetJsonBean);
            }

            assetListJsonBean.setAssets(assetList);

            memberAssetListJsonBean.add(assetListJsonBean);
        }



        Page<MemberAssetListJsonBean> returnPage = new Page<MemberAssetListJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        returnPage.setList(memberAssetListJsonBean);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }

    @RequestMapping(value = "/load_member_deposit/{member_id}",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    public @ResponseBody ResponseBean loadMemberDeposit(@PathVariable String member_id){
        Member member = memberService.findMemberByMemberId(member_id);
        if(member != null){
            MemberAsset asset = memberService.getDepositAssetOfMember(member);
            if(asset != null){
                int deposit = asset.getDeposit();

                MemberDepositBean depositBean = new MemberDepositBean();
                depositBean.setDeposit(deposit);
                depositBean.setMember(MemberJsonBeanUtil.toJsonBean(member));

                return ResponseMsgBeanFactory.getResponseBean(
                        true,
                        depositBean
                );

            }else{
                return ResponseMsgBeanFactory.getErrorResponseBean(
                        "1106",
                        "该会员无充值信息， 会员ID：" + member_id
                );
            }
        }else {
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "1006",
                    "会员信息删除失败，无法找到会员ID：" + member_id
            );
        }
    }

    @RequestMapping(value = "/load_member_bonusponit/{member_id}",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    public @ResponseBody ResponseBean loadMemberBonusPoint(@PathVariable String member_id){
        Member member = memberService.findMemberByMemberId(member_id);
        if(member != null){
            int bonuspoint = memberService.getBonusPoint(member);


            MemberBonusPointJsonBean bonusPointJsonBean = new MemberBonusPointJsonBean();
            bonusPointJsonBean.setMember_bonus_point(bonuspoint);
            bonusPointJsonBean.setMember(MemberJsonBeanUtil.toJsonBean(member));

            return ResponseMsgBeanFactory.getResponseBean(
                    true,
                    bonusPointJsonBean
            );
        }else {
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "1006",
                    "会员信息删除失败，无法找到会员ID：" + member_id
            );
        }
    }
}
