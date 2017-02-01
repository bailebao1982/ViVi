/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.sales.service;

import com.spstudio.common.search.Page;
import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.Member;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductPackage;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.sales.entity.SaleDiscount;
import com.spstudio.modules.sales.entity.Sales;

import java.util.List;

/**
 *
 * @author wewezhu
 */
public interface SaleService {

    /*
     *  Sales operations
     */
    public Sales findSaleRecordById(String saleId);

    public List<Sales> findSaleRecordOfMember(Member member);

    //public Sales _addProductSaleRecord(Member member, Product product, int count, int deposit, float price, String saler, PaymentMethodType type);

    //public Sales addProductPackageSaleRecord(Member member, ProductPackage pkg, int count, String saler, PaymentMethodType type);

    //public Sales addDepositSaleRecord(Member member, int deposit, String saler, PaymentMethodType type);

    public boolean removeSaleRecord(Sales salerec);

    public boolean removeSaleRecordList(List<String> assetIdList);

    public boolean removeSaleRecordsOfMember(Member member);

    public Sales updateSaleRecord(Sales salerec);
    
    public void zapSalesRecord(Sales sales);

    public boolean zapSaleRecordOfMember(Member member);

    public Page<Sales> querySalesForPage(int offset, int length, SearchCriteria criteria);

    /*
     *  Discount operations
     */
    public SaleDiscount createDiscountForProduct(Product product, MemberType type, float discount);

    public SaleDiscount createDiscountForProductType(ProductType productType, MemberType type, float discount);

    public SaleDiscount findDiscountById(String discountId);

    public SaleDiscount findDiscount(Product product, MemberType type);

    public SaleDiscount findDiscount(ProductType productType, MemberType type);

    public void updateDiscount(SaleDiscount discount);

    public boolean deleteDiscount(SaleDiscount discount);

    public boolean deleteDiscountByProduct(Product product);

    public boolean deleteDiscountByProductType(ProductType productType);

    public boolean deleteDiscountByMemberType(MemberType type);

    public Page<SaleDiscount> queryDiscountForPage(int offset, int length, SearchCriteria criteria);

    /*
     * Bonus rate Service
     */
    public Float getGlobalDepositRate();

    public void createOrUpdateGlobalDepositRate(Float depositRate);

    public Float getDepositRate(MemberType memberType);

    public void createOrUpdateDepositRate(MemberType memberType, Float depositRate);

    /*
     * Sale operations
     */
    public boolean BuyDeposit(Member member, int deposit, String saler);

    public boolean BuyProduct(PaymentMethodType type, Member member, Product product, int count, String saler);
    public boolean BuyProduct(PaymentMethodType type, Member member, Product product, int count, float price, String saler);

    //public boolean BuyProductPayWithCash(Member member, Product product, int count, String saler);
    //public boolean BuyProductPayWithDeposit(Member member, Product product, int count, String saler);
    //public boolean BuyProductPayWithBonusPoint(Member member, Product product, int count, String saler);

    public boolean BuyPackage(PaymentMethodType type, Member member, ProductPackage pkg, int count, String saler);
    public boolean BuyPackage(PaymentMethodType type, Member member, ProductPackage pkg, int count, float price, String saler);

    //public boolean BuyPackagePayWithCash(Member member, ProductPackage pkg, int count, String saler);
    //public boolean BuyPackagePayWithDeposit(Member member, ProductPackage pkg, int count, String saler);

}
