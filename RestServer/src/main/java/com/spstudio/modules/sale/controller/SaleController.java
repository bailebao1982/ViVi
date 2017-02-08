package com.spstudio.modules.sale.controller;

import com.spstudio.common.response.ResponseBean;
import com.spstudio.common.response.ResponseMsgBeanFactory;
import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchConditionEnum;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.common.search.SearchCriteriaItem;
import com.spstudio.common.utils.StringUtils;
import com.spstudio.modules.member.bean.request.MemberJsonBean;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.member.service.AssetType;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.sale.bean.*;
import com.spstudio.modules.sales.entity.SaleDiscount;
import com.spstudio.modules.sales.exception.InsufficientBonusPointException;
import com.spstudio.modules.sales.exception.InsufficientDepositException;
import com.spstudio.modules.sales.service.DiscountType;
import com.spstudio.modules.sales.service.PaymentMethodType;
import com.spstudio.modules.sales.entity.Sales;
import com.spstudio.modules.sales.service.SaleService;
import com.spstudio.modules.stock.exceptions.StockNotEnoughException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.spstudio.modules.sales.service.PaymentMethodType.PAYMENT_METHOD_DEPOSIT;

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

    private ResponseBean _exception2Response(Exception ex){
        if(ex instanceof InsufficientBonusPointException){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4112",
                    "会员积分不足抵扣"
            );
        }else if(ex instanceof InsufficientDepositException){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4113",
                    "会员余额不足"
            );
        }else if (ex instanceof StockNotEnoughException){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4114",
                    "库存数量不足"
            );
        }else{
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4115",
                    ex.getMessage()
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
                            !paymentMethodType.equals(PAYMENT_METHOD_DEPOSIT)){
                        return ResponseMsgBeanFactory.getErrorResponseBean(
                                "4200",
                                "错误：不支持该付款方式"
                        );
                    }

                    try {
                        saleService.BuyPackage(
                                paymentMethodType,
                                member,
                                prdtPackage,
                                saleRecordJsonBean.getSale_count(),
                                saleRecordJsonBean.getSale_price(),
                                saler);
                        return _result2Response(true);
                    }catch (Exception ex){
                        return _exception2Response(ex);
                    }

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
                       !paymentMethodType.equals(PAYMENT_METHOD_DEPOSIT) &&
                       !paymentMethodType.equals(PaymentMethodType.PAYMENT_METHOD_BONUSPOINT)){
                        return ResponseMsgBeanFactory.getErrorResponseBean(
                                "4200",
                                "错误：不支持该付款方式"
                        );
                    }

                    try{
                        saleService.BuyProduct(
                                paymentMethodType,
                                member,
                                product,
                                saleRecordJsonBean.getSale_count(),
                                saleRecordJsonBean.getSale_price(),
                                saler);
                        return _result2Response(true);
                    }catch (Exception ex){
                        return _exception2Response(ex);
                    }
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
            @RequestParam(value="member_name", required=false) String member_name,
            @RequestParam(value="asset_type", required=false, defaultValue = "-1") int asset_type,
            @RequestParam(value="product_id", required=false) String product_id,
            @RequestParam(value="package_id", required=false) String package_id,
            @RequestParam(value="pay_method_type", required=false, defaultValue = "-1") int pay_method_type,
            @RequestParam(value="sale_start_date", required=false) String sale_start_date,
            @RequestParam(value="sale_end_date", required=false) String sale_end_date
    ){
        SearchCriteria sc = new SearchCriteria();
        if(member_name != null &&
                !member_name.isEmpty()) {
            SearchCriteria member_sc = new SearchCriteria();
            member_sc.addSearchCriterialItem("memberName",
                    new SearchCriteriaItem("memberName", member_name, SearchConditionEnum.Like)
            );
            Page<Member> members = memberService.queryForPage(page, page_size, member_sc);
            List<String> member_id_list = new ArrayList<String>();
            member_id_list.add("''");
            for (Member member: members.getList()){
                member_id_list.add("'" + member.getMemberId() + "'");
            }

            String content = org.apache.commons.lang.StringUtils.join(member_id_list, ",");
            sc.addSearchCriterialItem("memberId",
                    new SearchCriteriaItem("memberId", content, SearchConditionEnum.In)
            );
        }

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
                        new SearchCriteriaItem("saleType", String.valueOf(assetType.ordinal()), SearchConditionEnum.Equal)
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
                        new SearchCriteriaItem("saleType", String.valueOf(assetType.ordinal()), SearchConditionEnum.Equal)
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
                    new SearchCriteriaItem("salesDate", sale_start_date, SearchConditionEnum.LargeOrEqual)
            );

        if(sale_end_date != null &&
                !sale_end_date.isEmpty())
            sc.addSearchCriterialItem("end_date",
                    new SearchCriteriaItem("salesDate", sale_end_date, SearchConditionEnum.SmallOrEqual)
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
            float discountVal = discountJsonBean.getDiscount_value();
            discount.setDiscountValue(discountVal);
            saleService.updateDiscount(discount);
            return ResponseMsgBeanFactory.getSuccessResponseBean(
                    "折扣更新成功!"
            );
        }
    }

    @RequestMapping(value = "/del_discount/{discount_id}",
            method = RequestMethod.DELETE,
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
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean listDiscounts(
            @RequestParam(value="page", required=true) int page,
            @RequestParam(value="page_size", required=true) int page_size,
            @RequestParam(value="member_type_id", required=false) String member_type_id,
            @RequestParam(value="discount_type", required=false, defaultValue = "-1") int discount_type,
            @RequestParam(value="product_id", required=false) String product_id,
            @RequestParam(value="product_type_serialno", required=false) String product_type_serialno){
        SearchCriteria sc = new SearchCriteria();
        if(member_type_id != null && !member_type_id.isEmpty()) {
            MemberType memberType = memberService.findMemberTypeByType(member_type_id);
            if(memberType != null) {
                sc.addSearchCriterialItem("memberTypeId",
                        new SearchCriteriaItem("memberTypeId", memberType.getMemberTypeId(), SearchConditionEnum.Equal)
                );
            }
        }

        DiscountType discountType = DiscountType.fromInteger(discount_type);
        switch (discountType){
            case PRODUCT_DISCOUNT: {
                if(product_id != null && !product_id.isEmpty()) {
                    Product product = productService.findProductByProductId(product_id);
                    if(product != null){
                        sc.addSearchCriterialItem("productId",
                                new SearchCriteriaItem("productId", product.getProductId(), SearchConditionEnum.Equal)
                        );
                    }
                }
                break;
            }
            case PRODUCT_TYPE_DISCOUNT: {
                if(product_type_serialno != null && !product_type_serialno.isEmpty()) {
                    ProductType productType = productService.findProductTypeByProductTypeName(product_type_serialno);
                    if(productType != null) {
                        sc.addSearchCriterialItem("productTypeId",
                                new SearchCriteriaItem("productTypeId", productType.getProductTypeId(), SearchConditionEnum.Equal)
                        );
                    }
                }
                break;
            }
            case ALL_DISCOUNTS:{
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

    @RequestMapping(value = "/get_product_price/{product_id}",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean getProductPrice(
            @PathVariable String product_id,
            @RequestParam(value="count", required=true) int count,
            @RequestParam(value="member_id", required=true) String member_id,
            @RequestParam(value="sale_payment_method", required=true) int sale_payment_method){

        Product product = productService.findProductByProductId(product_id);
        if(product == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4106",
                    "错误：未能识别产品，product id:" + product_id
            );
        }

        Member member = null;
        PaymentMethodType paymentMethodType = PaymentMethodType.fromInteger(sale_payment_method);
        if(paymentMethodType.equals(PAYMENT_METHOD_DEPOSIT)){
            member = memberService.findMemberByMemberId(member_id);
            if(member == null){
                return ResponseMsgBeanFactory.getErrorResponseBean(
                        "4216",
                        "未能找到该用户，member id:" + member_id
                );
            }
        }

        float price = saleService.GetProductPrice(paymentMethodType, member, product, count);
        float discount = saleService.GetProductDiscount(paymentMethodType, member, product);

        SalePriceJsonBean salePriceJsonBean = new SalePriceJsonBean();
        salePriceJsonBean.setPrice(price);
        salePriceJsonBean.setDiscount(discount);
        salePriceJsonBean.setUnit_price(product.getUnitPrice());
        salePriceJsonBean.setCount(count);


        return ResponseMsgBeanFactory.getResponseBean(
                true,
                salePriceJsonBean
        );
    }

    @RequestMapping(value = "/get_product_bonus_point/{product_id}",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean getProductBonusPoint(
            @PathVariable String product_id,
            @RequestParam(value="count", required=true) int count){
        // Get bonus point directly
        Product product = productService.findProductByProductId(product_id);
        if(product == null){
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4106",
                    "错误：未能识别产品，product id:" + product_id
            );
        }

        int bonusPoint = saleService.GetProductBonusPoint(product, count);

        SalePriceJsonBean salePriceJsonBean = new SalePriceJsonBean();
        salePriceJsonBean.setPrice(bonusPoint);
        salePriceJsonBean.setDiscount(1.0f);
        salePriceJsonBean.setUnit_price(product.getBonusePoint());
        salePriceJsonBean.setCount(count);


        return ResponseMsgBeanFactory.getResponseBean(
                true,
                salePriceJsonBean
        );
    }

    @RequestMapping(value = "/get_package_price/{package_id}",
            method = RequestMethod.GET,
            headers="Accept=application/json")
    @CrossOrigin
    public @ResponseBody ResponseBean getPackagePrice(
            @PathVariable String package_id,
            @RequestParam(value="count", required=true) int count){
            // calculate package price directly
        ProductPackage prdtPackage = productService.findProductPackageByPackageId(package_id);

        if(prdtPackage == null) {
            return ResponseMsgBeanFactory.getErrorResponseBean(
                    "4105",
                    "错误：未能识别套餐，package id:" + package_id
            );
        }

        int price = saleService.GetPackagePrice(prdtPackage, count);

        SalePriceJsonBean salePriceJsonBean = new SalePriceJsonBean();
        salePriceJsonBean.setPrice(price);
        salePriceJsonBean.setDiscount(1.0f);
        salePriceJsonBean.setUnit_price(prdtPackage.getUnitPrice());
        salePriceJsonBean.setCount(count);

        return ResponseMsgBeanFactory.getResponseBean(
                true,
                salePriceJsonBean
        );
    }
}
