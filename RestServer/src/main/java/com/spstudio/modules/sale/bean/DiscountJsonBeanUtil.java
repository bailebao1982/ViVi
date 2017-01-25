package com.spstudio.modules.sale.bean;

import com.spstudio.modules.member.bean.request.MemberTypeJsonBeanUtil;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.product.bean.request.ProductJsonBean;
import com.spstudio.modules.product.bean.request.ProductJsonBeanUtil;
import com.spstudio.modules.product.bean.request.ProductTypeJsonBean;
import com.spstudio.modules.product.bean.request.ProductTypeJsonBeanUtil;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.sales.entity.SaleDiscount;
import com.spstudio.modules.sales.service.DiscountType;

/**
 * Created by Soul on 2017/1/22.
 */
public class DiscountJsonBeanUtil {
    public static DiscountJsonBean toJsonBean(SaleDiscount discountBean){
        DiscountJsonBean discountJsonBean = new DiscountJsonBean();
        discountJsonBean.setDiscount_id(discountBean.getDiscountId());
        discountJsonBean.setCreate_date(discountBean.getCreationDate().toString());
        discountJsonBean.setDiscount_member_type(MemberTypeJsonBeanUtil.toJsonBean(discountBean.getMemberType()));
        discountJsonBean.setDiscount_value(discountBean.getDiscountValue());

        Product product = discountBean.getProduct();
        ProductType productType = discountBean.getProductType();
        if(product != null){
            discountJsonBean.setDiscount_type(DiscountType.PRODUCT_DISCOUNT.ordinal());
            discountJsonBean.setDiscount_product(ProductJsonBeanUtil.toJsonBean(product));
        }else if(productType != null){
            discountJsonBean.setDiscount_type(DiscountType.PRODUCT_TYPE_DISCOUNT.ordinal());
            discountJsonBean.setDiscount_product_type(ProductTypeJsonBeanUtil.toJsonBean(productType));
        }else{
            discountJsonBean.setDiscount_type(DiscountType.NON_DISCOUNT.ordinal());
        }
        return discountJsonBean;
    }


    public static SaleDiscount toEntityBean(DiscountJsonBean jsonBean, MemberService memberService, ProductService productService){
        SaleDiscount discountBean = new SaleDiscount();
        try {
            discountBean.setDiscountValue(Float.valueOf(jsonBean.getDiscount_value()));
        }catch (NumberFormatException ex){
            return null;
        }

        discountBean.setMemberType(memberService.findMemberTypeById(jsonBean.getDiscount_member_type().getMember_type_id()));

        int intDiscountType = jsonBean.getDiscount_type();
        DiscountType discountType = DiscountType.fromInteger(intDiscountType);

        switch (discountType){
            case PRODUCT_DISCOUNT: {
                ProductJsonBean productJsonBean = jsonBean.getDiscount_product();
                if(productJsonBean != null) {
                    discountBean.setProduct(productService.findProductByProductId(productJsonBean.getProduct_id()));
                }
                break;
            }
            case PRODUCT_TYPE_DISCOUNT: {
                ProductTypeJsonBean productTypeJsonBean = jsonBean.getDiscount_product_type();
                if(productTypeJsonBean != null) {
                    discountBean.setProductType(
                            productService.findProductTypeByProductTypeId(productTypeJsonBean.getProduct_type_id())
                    );
                }
                break;
            }
            case NON_DISCOUNT: {
                break;
            }
        }

        return discountBean;
    }
}
