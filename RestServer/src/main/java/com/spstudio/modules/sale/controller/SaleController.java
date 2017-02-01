package com.spstudio.modules.sale.controller;

import com.spstudio.common.response.ResponseBean;
import com.spstudio.common.response.ResponseMsgBeanFactory;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchConditionEnum;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.search.SearchCriteriaItem;
import com.spstudio.modules.member.bean.request.MemberJsonBean;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.service.AssetType;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.sale.bean.*;
import com.spstudio.modules.sales.entity.SaleDiscount;
import com.spstudio.modules.sales.service.DiscountType;
import com.spstudio.modules.sales.service.PaymentMethodType;
import com.spstudio.modules.sales.entity.Sales;
import com.spstudio.modules.sales.service.SaleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Soul on 2017/1/17.
 */
@RestController
@RequestMapping("/sale")
public class SaleController {

    @Resource(name="memberService")
    private MemberService memberService;

    // For generate sales record
    @Resource(name="saleService")
    private SaleService saleService;

    @Resource(name="productService")
    private ProductService productService;

    private ResponseBean _result2Response(boolean result){
        if(result){
            return ResponseMsgBeanFactory.getSuccessResponseBean(
                    "销售记录创建成功！"
            );
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4101",
                    "错误：创建销售记录失败"
            );
        }
    }

    @RequestMapping(value = "/add_sale_record",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addSaleRecord(@RequestBody SimpleSaleRecordJsonBean saleRecordJsonBean){
        String saler = saleRecordJsonBean.getSaler();

        Member member = memberService.findMemberByMemberId(saleRecordJsonBean.getSale_member_id());

        if(member != null){
            AssetType assetType = AssetType.fromInteger(
                    saleRecordJsonBean.getSale_record_type()
            );
            PaymentMethodType paymentMethodType = PaymentMethodType.fromInteger(
                    saleRecordJsonBean.getSale_payment_method()
            );

            switch (assetType){
                case ASSET_DEPOSIT_TYPE: {
                    if(!paymentMethodType.equals(PaymentMethodType.PAYMENT_METHOD_CASH)){
                        return ResponseMsgBeanFactory.getErrorResponseBean(
                                "4200",
                                "错误：不支持该付款方式"
                        );
                    }else{
                        boolean result = saleService.BuyDeposit(
                                member,
                                (int)saleRecordJsonBean.getSale_price(),
                                saler);
                        return _result2Response(result);
                    }
                    //break;
                }
                case ASSET_PACKAGE_TYPE: {
                    String pacakgeId = saleRecordJsonBean.getSale_package_id();
                    ProductPackage prdtPackage = productService.findProductPackageByPackageId(pacakgeId);

                    if(prdtPackage == null) {
                        return ResponseMsgBeanFactory.getErrorResponseBean(
                                "4105",
                                "错误：未能识别套餐，package id:" + pacakgeId
                        );
                    }

                    if(!paymentMethodType.equals(PaymentMethodType.PAYMENT_METHOD_CASH) &&
                            !paymentMethodType.equals(PaymentMethodType.PAYMENT_METHOD_DEPOSIT)){
                        return ResponseMsgBeanFactory.getErrorResponseBean(
                                "4200",
                                "错误：不支持该付款方式"
                        );
                    }

                    boolean result = saleService.BuyPackage(
                            paymentMethodType,
                            member,
                            prdtPackage,
                            saleRecordJsonBean.getSale_count(),
                            saler);
                    return _result2Response(result);
                    //break;
                }
                case ASSET_PRODUCT_TYPE: {
                    String productId = saleRecordJsonBean.getSale_product_id();
                    Product product = productService.findProductByProductId(productId);
                    if(product == null){
                        return ResponseMsgBeanFactory.getErrorResponseBean(
                                "4106",
                                "错误：未能识别产品，product id:" + productId
                        );
                    }

                    if(!paymentMethodType.equals(PaymentMethodType.PAYMENT_METHOD_CASH) &&
                       !paymentMethodType.equals(PaymentMethodType.PAYMENT_METHOD_DEPOSIT) &&
                       !paymentMethodType.equals(PaymentMethodType.PAYMENT_METHOD_BONUSPOINT)){
                        return ResponseMsgBeanFactory.getErrorResponseBean(
                                "4200",
                                "错误：不支持该付款方式"
                        );
                    }

                    boolean result = saleService.BuyProduct(
                            paymentMethodType,
                            member,
                            product,
                            saleRecordJsonBean.getSale_count(),
                            saler);
                    return _result2Response(result);

                   // break;
                }
                default:{
                    return ResponseMsgBeanFactory.getErrorResponseBean(
                            "4201",
                            "错误：没有这样的商品类型"
                    );
                    //break;
                }
            }
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4001",
                    "错误：未能识别用户，member id:" + saleRecordJsonBean.getSale_member_id()
            );
        }
    }

    // delete
    @RequestMapping(value = "/del_sale/{sale_id}",
            method = RequestMethod.DELETE,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean removeSale(@PathVariable String sale_id){
        Sales sale = saleService.findSaleRecordById(sale_id);
        if (sale == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4106",
                    "销售信息删除失败，无法找到该记录，记录ID：" + sale_id
            );
        }else{
            saleService.removeSaleRecord(sale);
            return ResponseMsgBeanFactory.getSuccessResponseBean("销售记录删除成功");
        }
    }

    // bat delete

    // list
    @RequestMapping(value = "/list_sale_records",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean searchSalesByCondition(
            @RequestParam(value="page", required=true) int page,
            @RequestParam(value="page_size", required=true) int page_size,
            @RequestParam(value="member_id", required=false) String member_id,
            @RequestParam(value="member_name", required=false) String member_name,
            @RequestParam(value="asset_type", required=false, defaultValue = "-1") int asset_type,
            @RequestParam(value="product_id", required=false) String product_id,
            @RequestParam(value="package_id", required=false) String package_id,
            @RequestParam(value="pay_method_type", required=false, defaultValue = "-1") int pay_method_type,
            @RequestParam(value="sale_start_date", required=false) String sale_start_date,
            @RequestParam(value="sale_end_date", required=false) String sale_end_date
    ){
        SearchCriteria sc = new SearchCriteria();
        if(member_id != null &&
                !member_id.isEmpty())
            sc.addSearchCriterialItem("memberId",
                    new SearchCriteriaItem("memberId", member_id, SearchConditionEnum.Equal)
            );

        if(member_name != null &&
                !member_name.isEmpty())
            sc.addSearchCriterialItem("memberName",
                    new SearchCriteriaItem("memberId", member_id, SearchConditionEnum.Equal)
            );

        AssetType assetType = AssetType.fromInteger(asset_type);
        switch (assetType){
            case ASSET_DEPOSIT_TYPE: {
                // 查询充值
                sc.addSearchCriterialItem("saleType",
                        new SearchCriteriaItem("saleType", String.valueOf(assetType), SearchConditionEnum.Equal)
                );
                break;
            }
            case ASSET_PRODUCT_TYPE: {
                // 查询产品购买记录
                sc.addSearchCriterialItem("saleType",
                        new SearchCriteriaItem("saleType", String.valueOf(assetType), SearchConditionEnum.Equal)
                );
                if(product_id != null &&
                        !product_id.isEmpty())
                    sc.addSearchCriterialItem("productId",
                            new SearchCriteriaItem("productId", product_id, SearchConditionEnum.Equal)
                    );
                break;
            }
            case ASSET_PACKAGE_TYPE: {
                // 查询套餐购买记录
                sc.addSearchCriterialItem("saleType",
                        new SearchCriteriaItem("saleType", String.valueOf(assetType), SearchConditionEnum.Equal)
                );
                if(package_id != null &&
                        !package_id.isEmpty())
                    sc.addSearchCriterialItem("productPackageId",
                            new SearchCriteriaItem("productPackageId", package_id, SearchConditionEnum.Equal)
                    );
                break;
            }
            default: {
                break;
            }
        }


        PaymentMethodType payMethodType = PaymentMethodType.fromInteger(pay_method_type);
        switch (payMethodType){
            case PAYMENT_METHOD_BONUSPOINT:
            case PAYMENT_METHOD_CASH:
            case PAYMENT_METHOD_DEPOSIT: {
                sc.addSearchCriterialItem("paymentMethodType",
                        new SearchCriteriaItem("paymentMethodType", String.valueOf(pay_method_type), SearchConditionEnum.Equal)
                );
                break;
            }
            default: {
                break;
            }
        }

        if(sale_start_date !=null &&
                !sale_start_date.isEmpty())
            sc.addSearchCriterialItem("start_date",
                    new SearchCriteriaItem("salesDate", sale_start_date, SearchConditionEnum.SmallOrEqual)
            );

        if(sale_end_date != null &&
                !sale_end_date.isEmpty())
            sc.addSearchCriterialItem("end_date",
                    new SearchCriteriaItem("salesDate", sale_end_date, SearchConditionEnum.LargeOrEqual)
            );


        Page<Sales> resultPageBean = saleService.querySalesForPage(page, page_size, sc);
        Page<SaleRecordJsonBean> returnPage = new Page<SaleRecordJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        ArrayList<SaleRecordJsonBean> returnArray = new ArrayList<SaleRecordJsonBean>();
        for (Sales sale : resultPageBean.getList()){
            returnArray.add(SaleRecordJsonBeanUtil.toJsonBean(sale));
        }
        returnPage.setList(returnArray);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }

    @RequestMapping(value = "/add_discount",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean addDiscount(@RequestBody SimpleDiscountJsonBean discountJsonBean){
        SaleDiscount discountBean = SimpleDiscountJsonBeanUtil.toEntityBean(discountJsonBean, memberService, productService);
        if(discountBean == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4201",
                    "折扣创建失败"
            );
        }

        int intDiscountType = discountJsonBean.getDiscount_type();
        DiscountType discountType = DiscountType.fromInteger(intDiscountType);
        switch (discountType){
            case PRODUCT_DISCOUNT: {
                SaleDiscount createdDiscount = saleService.createDiscountForProduct(
                        discountBean.getProduct(),
                        discountBean.getMemberType(),
                        discountBean.getDiscountValue()
                );
                if(createdDiscount != null){
                    return ResponseMsgBeanFactory.getSuccessResponseBean("折扣创建成功！");
                }else{
                    return ResponseMsgBeanFactory.getErrorResponseBean(
                            "4201",
                            "折扣创建失败"
                    );
                }
            }
            case PRODUCT_TYPE_DISCOUNT: {
                SaleDiscount createdDiscount = saleService.createDiscountForProductType(
                        discountBean.getProductType(),
                        discountBean.getMemberType(),
                        discountBean.getDiscountValue()
                );
                if(createdDiscount != null){
                    return ResponseMsgBeanFactory.getSuccessResponseBean("折扣创建成功！");
                }else{
                    return ResponseMsgBeanFactory.getErrorResponseBean(
                            "4201",
                            "折扣创建失败"
                    );
                }
            }
        }
        return ResponseMsgBeanFactory.getErrorResponseBean(
                "4201",
                "折扣创建失败"
        );
    }

    @RequestMapping(value = "/update_discount/{discount_id}",
            method = RequestMethod.POST,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean updateDiscount(@PathVariable String discount_id,
                                                     @RequestBody SimpleDiscountJsonBean discountJsonBean){
        SaleDiscount discount = saleService.findDiscountById(discount_id);

        if(discount == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4206",
                    "删除折扣失败，无此折扣，Id：" + discount_id
            );
        }else{
            float discountVal = discountJsonBean.getDiscount_type();
            discount.setDiscountValue(discountVal);
            saleService.updateDiscount(discount);
            return ResponseMsgBeanFactory.getSuccessResponseBean(
                    "折扣更新成功!"
            );
        }
    }

    @RequestMapping(value = "/del_discount/{discount_id}",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean removeDiscount(@PathVariable String discount_id){
        SaleDiscount discountBean = saleService.findDiscountById(discount_id);

        if (discountBean == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4206",
                    "删除折扣失败，无此折扣，Id：" + discount_id
            );
        }else{
            boolean result = saleService.deleteDiscount(discountBean);
            if(result){
                return ResponseMsgBeanFactory.getSuccessResponseBean("销售记录删除成功");
            }else{
                return ResponseMsgBeanFactory.getErrorResponseBean(
                        "4207",
                        "折扣删除发生错误，Id：" + discount_id
                );
            }

        }
    }

    @RequestMapping(value = "/list_discount",
            method = RequestMethod.PUT,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean listDiscounts(
            @RequestParam(value="page", required=true) int page,
            @RequestParam(value="page_size", required=true) int page_size,
            @RequestParam(value="member_type_id", required=false) String member_type_id,
            @RequestParam(value="discount_type", required=false, defaultValue = "-1") int discount_type,
            @RequestParam(value="product_id", required=false) String product_id,
            @RequestParam(value="product_type_id", required=false) String product_type_id){
        SearchCriteria sc = new SearchCriteria();
        if(member_type_id != null && !member_type_id.isEmpty()) {
            sc.addSearchCriterialItem("memberTypeId",
                    new SearchCriteriaItem("memberTypeId", member_type_id, SearchConditionEnum.Equal)
            );
        }

        DiscountType discountType = DiscountType.fromInteger(discount_type);
        switch (discountType){
            case PRODUCT_DISCOUNT: {
                if(product_id != null && !product_id.isEmpty()) {
                    sc.addSearchCriterialItem("productId",
                            new SearchCriteriaItem("productId", product_id, SearchConditionEnum.Equal)
                    );
                }
                break;
            }
            case PRODUCT_TYPE_DISCOUNT: {
                if(product_type_id != null && !product_type_id.isEmpty()) {
                    sc.addSearchCriterialItem("productTypeId",
                            new SearchCriteriaItem("productTypeId", product_type_id, SearchConditionEnum.Equal)
                    );
                }
                break;
            }
        }

        Page<SaleDiscount> resultPageBean = saleService.queryDiscountForPage(page, page_size, sc);
        Page<DiscountJsonBean> returnPage = new Page<DiscountJsonBean>();

        returnPage.setTotalRecords(resultPageBean.getTotalRecords());
        returnPage.setPageNo(resultPageBean.getPageNo());
        returnPage.setPageSize(resultPageBean.getPageSize());

        ArrayList<DiscountJsonBean> returnArray = new ArrayList<DiscountJsonBean>();
        for (SaleDiscount discount : resultPageBean.getList()){
            returnArray.add(DiscountJsonBeanUtil.toJsonBean(discount));
        }
        returnPage.setList(returnArray);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                returnPage
        );
    }
}
