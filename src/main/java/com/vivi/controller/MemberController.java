/*
 *
 * Class: MemberController
 * Description:
 *  This is the class act as the route for restful api
 *
 */
package com.vivi.controller;

import com.vivi.common.response.ResponseBean;
import com.vivi.common.response.ResponseMsgBeanFactory;
import com.vivi.common.response.SuccessMessageBean;
import com.vivi.modules.member.entity.Member;
import com.vivi.modules.member.entity.MemberType;
import com.vivi.modules.member.service.MemberTypeService;
import com.vivi.modules.member.bean.request.*;
import com.vivi.common.search.Page;
import com.vivi.common.search.SearchConditionEnum;
import com.vivi.common.search.SearchCriteria;
import com.vivi.common.search.SearchCriteriaItem;
import com.vivi.modules.member.service.MemberService;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

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
    
    @Resource(name="memberTypeService")
    private MemberTypeService memberTypeService;

    @Resource(name="memberService")
    private MemberService memberService;

    public MemberService getMemberService() {
        return memberService;
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    
    
//    @RequestMapping(value = "/customers", method = RequestMethod.GET,headers="Accept=application/json")
//    public @ResponseBody List<MemberJsonBean> getCustomers(){
//        List<MemberJsonBean> listOfCustomers = new ArrayList<MemberJsonBean>();
//        listOfCustomers = createCountryList();
//
//        return listOfCustomers;
//    }

    @RequestMapping(value = "/list_member_type",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    public @ResponseBody ResponseBean listMemberType(){
        List<MemberType> memberTypes = memberTypeService.listAllMemberType();

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                memberTypes
        );
    }

    @RequestMapping(value = "/add_member_type",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    public @ResponseBody ResponseBean addMemberType(@RequestBody MemberTypeJsonBean memberTypeJson){

        MemberType newMemberType = MemberTypeJsonBeanUtil.toEntityBean(memberTypeJson);
        memberTypeService.addMemberType(newMemberType);
        return ResponseMsgBeanFactory.getSuccessResponseBean("会员类型创建成功");
    }

    @RequestMapping(value = "/del_member_type/{member_type_id}",
            method = RequestMethod.DELETE,
            headers="Accept=application/json")
    public @ResponseBody ResponseBean delMemberType(@PathVariable String member_type_id){

        MemberType newMemberType = memberTypeService.findMemberTypeById(member_type_id);
        if(newMemberType != null){
            memberTypeService.removeMemberType(newMemberType);
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
    public @ResponseBody ResponseBean addCustomer(@RequestBody MemberJsonBean member){
        if(member.getMember_name().isEmpty() ||
           member.getMember_telphone().isEmpty()){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "1009",
                    "错误：会员必要的信息丢失。请检查 名称，手机号码"
            );
        }else{
            Member newMember = MemberJsonBeanUtil.toEntityBean(member, memberTypeService);
            newMember.setCreationDate(new java.sql.Date(new Date().getTime()));
            try{
                memberService.addMember(newMember);
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
    public @ResponseBody ResponseBean updateCustomer(@RequestBody MemberJsonBean memberJson,
                                                     @PathVariable String member_id){
        // Something might be wrong here
        Member updateMember = MemberJsonBeanUtil.toEntityBean(memberJson, memberTypeService);
        updateMember.setMemberId(member_id);
        if(memberService.updateMember(updateMember)){
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
    public @ResponseBody ResponseBean removeCustomer(@PathVariable String member_id){

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
    
    @RequestMapping(value = "/member_detail/{member_id}",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    public @ResponseBody ResponseBean getMemberByMemberId(@PathVariable String member_id){
       
        Member member = memberService.findMemberByMemberId(member_id);
        if(member != null){
            MemberJsonBean responseMemberJsonBean = MemberJsonBeanUtil.toJsonBean(member);
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
      public @ResponseBody ResponseBean searchMemberByCondition(
              @RequestParam(value="page", required=true) int page,
              @RequestParam(value="page_size", required=true) int page_size,
              @RequestParam(value="name", required=false) String name,
              @RequestParam(value="telphone", required=false) String telphone,
              @RequestParam(value="email", required=false) String email,
              @RequestParam(value="reg_start_date", required=false) String reg_start_date,
              @RequestParam(value="reg_end_date", required=false) String reg_end_date){
        MemberQueryBean queryBean = new MemberQueryBean();
        queryBean.setPage(page);
        queryBean.setPage_size(page_size);
        queryBean.setName(name);
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
        if(queryBean.getReg_start_date() != null &&
           !queryBean.getReg_start_date().isEmpty())
            sc.addSearchCriterialItem("start_date",
                    new SearchCriteriaItem("creationDate", queryBean.getReg_start_date(), SearchConditionEnum.LargeOrEqual)
            );
        if(queryBean.getReg_end_date()!=null &&
           !queryBean.getReg_end_date().isEmpty())
            sc.addSearchCriterialItem("end_date",
                    new SearchCriteriaItem("creationDate", queryBean.getReg_end_date(), SearchConditionEnum.Small)
            );
        
        Page<Member> resultPageBean = memberService.queryForPage(queryBean.getPage(), queryBean.getPage_size(), sc);
        Page<MemberJsonBean> returnPage = new Page<MemberJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        ArrayList<MemberJsonBean> returnArray = new ArrayList<MemberJsonBean>();
        for (Member memb : resultPageBean.getList()){
            returnArray.add(MemberJsonBeanUtil.toJsonBean(memb));
        }
        returnPage.setList(returnArray);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }

    @RequestMapping(value = "/test",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    public @ResponseBody ResponseBean<SuccessMessageBean> test(){
        return ResponseMsgBeanFactory.getSuccessResponseBean("测试成功");
    }

}
