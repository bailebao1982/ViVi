package com.spstudio.modules.sale.bean;

import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.sales.entity.SaleDiscount;
import com.spstudio.modules.sales.service.DiscountType;

/**
 * Created by Soul on 2017/1/24.
 */
public class SimpleDiscountJsonBeanUtil {

    public static SaleDiscount toEntityBean(SimpleDiscountJsonBean jsonBean, MemberService memberService, ProductService productService){
        SaleDiscount discountBean = new SaleDiscount();
        try {
            discountBean.setDiscountValue(Float.valueOf(jsonBean.getDiscount_value()));
        }catch (NumberFormatException ex){
            return null;
        }

        MemberType memberType = memberService.findMemberTypeByType(jsonBean.getDiscount_member_type());
        if(memberType == null){
            return null;
        }
        discountBean.setMemberType(memberType);

        int intDiscountType = jsonBean.getDiscount_type();
        DiscountType discountType = DiscountType.fromInteger(intDiscountType);

        switch (discountType){
            case PRODUCT_DISCOUNT: {
                Product product = productService.findProductByProductId(jsonBean.getDiscount_product());
                if(product != null) {
                    discountBean.setProduct(product);
                }else {
                    return null;
                }
                break;
            }
            case PRODUCT_TYPE_DISCOUNT: {
                ProductType productType = productService.findProductTypeByProductTypeName(jsonBean.getDiscount_product_type());
                if(productType != null) {
                    discountBean.setProductType(productType);
                }else{
                    return null;
                }
                break;
            }
            case NON_DISCOUNT: {
                return null;
            }
        }

        return discountBean;
    }
}
