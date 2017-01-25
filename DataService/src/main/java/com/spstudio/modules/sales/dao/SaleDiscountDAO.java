package com.spstudio.modules.sales.dao;

import com.spstudio.common.search.SearchCriteria;
import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductType;
import com.spstudio.modules.sales.entity.SaleDiscount;

import java.util.List;

/**
 * Created by Soul on 2017/1/19.
 */
public interface SaleDiscountDAO {

    public SaleDiscount getDiscountByProduct(String discountId);

    public SaleDiscount getDiscountByProduct(Product product, MemberType type);

    public SaleDiscount getDiscountByProductType(ProductType productType, MemberType type);

    public SaleDiscount createDiscount(Product product, MemberType type, float discount);

    public SaleDiscount createDiscount(ProductType productType, MemberType type, float discount);

    public void updateDiscountByProduct(SaleDiscount discount);

    public boolean zapDiscount(SaleDiscount discount);

    public boolean zapDiscountsByProduct(Product product);

    public boolean zapDiscountsByProductType(ProductType product);

    public boolean zapDiscountsByMemberType(MemberType type);

    public int getQueryCount(SearchCriteria criteria);

    public List<SaleDiscount> queryForPage(int offset, int length, SearchCriteria criteria);
}
