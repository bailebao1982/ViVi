package com.spstudio.modules.sale.bean;

import com.spstudio.common.utils.DateUtils;
import com.spstudio.modules.member.bean.request.MemberJsonBeanUtil;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.service.AssetType;
import com.spstudio.modules.member.service.MemberService;
import com.spstudio.modules.product.bean.request.PackageJsonBean;
import com.spstudio.modules.product.bean.request.PackageJsonBeanUtil;
import com.spstudio.modules.product.bean.request.ProductJsonBeanUtil;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.service.ProductService;
import com.spstudio.modules.sales.entity.Sales;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Soul on 2017/1/19.
 */
public class SaleRecordJsonBeanUtil {
    public static SaleRecordJsonBean toJsonBean(Sales saleBean) {
        SaleRecordJsonBean jsonBean = new SaleRecordJsonBean();
        jsonBean.setSale_record_id(saleBean.getSaleId());

        Member member = saleBean.getMember();
        jsonBean.setSale_member(MemberJsonBeanUtil.toJsonBean(member));

        jsonBean.setSaler(saleBean.getSaler());
        jsonBean.setSale_date(saleBean.getSalesDate().toString());
        jsonBean.setSale_payment_method(saleBean.getPaymentMethodType());
        jsonBean.setSale_price(saleBean.getPrice());

        int intAssetType = saleBean.getSaleType();
        AssetType assetType = AssetType.fromInteger(intAssetType);

        jsonBean.setSale_record_type(intAssetType);
        switch (assetType){
            case ASSET_DEPOSIT_TYPE: {
                break;
            }
            case ASSET_PACKAGE_TYPE: {
                ProductPackage pkg = saleBean.getProductPackage();
                jsonBean.setSale_package(PackageJsonBeanUtil.toJsonBean(pkg));
                break;
            }
            case ASSET_PRODUCT_TYPE: {
                Product product = saleBean.getProduct();
                jsonBean.setSale_product(ProductJsonBeanUtil.toJsonBean(product));
                break;
            }
        }

        return jsonBean;
    }

    public static Sales toEntityBean(SaleRecordJsonBean jsonBean, MemberService memberService, ProductService productService){
        Sales saleBean = new Sales();

        saleBean.setSaler(jsonBean.getSaler());
        saleBean.setPaymentMethodType(jsonBean.getSale_payment_method());
        saleBean.setPrice(jsonBean.getSale_price());

        String memberId = jsonBean.getSale_member().getMember_id();
        Member member = memberService.findMemberByMemberId(memberId);
        saleBean.setMember(member);

        int intAssetType = jsonBean.getSale_record_type();
        AssetType assetType = AssetType.fromInteger(intAssetType);
        saleBean.setSaleType(intAssetType);

        switch (assetType){
            case ASSET_DEPOSIT_TYPE: {
                break;
            }
            case ASSET_PACKAGE_TYPE: {
                String packageId = jsonBean.getSale_package().getPackage_id();
                ProductPackage pkg = productService.findProductPackageByPackageId(packageId);
                saleBean.setProductPackage(pkg);
                break;
            }
            case ASSET_PRODUCT_TYPE: {
                String productId = jsonBean.getSale_product().getProduct_id();
                Product product = productService.findProductByProductId(productId);
                saleBean.setProduct(product);
                break;
            }
        }

        return saleBean;
    }
}
