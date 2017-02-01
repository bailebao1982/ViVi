package com.spstudio.modules.sales.entity;

import com.spstudio.modules.member.entity.MemberType;
import com.spstudio.modules.product.entity.Product;
import com.spstudio.modules.product.entity.ProductType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Soul on 2017/1/18.
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Table(
        name="T_ProductDiscount"
)
public class SaleDiscount {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid",strategy="uuid")
    @Column(length=32)
    String discountId;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="memberTypeId" ,referencedColumnName="memberTypeId")
    MemberType memberType;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="productId" ,referencedColumnName="productId")
    Product product;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="productTypeId" ,referencedColumnName="productTypeId")
    ProductType productType;

    @Column
    float discountValue;

    @Column(updatable = false)
    Date creationDate;

    @Column
    Date lastUpdateDate;

    @Column(length=32)
    String creator;

    @Column(length=32)
    String lastUpdateBy;

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(float discountValue) {
        this.discountValue = discountValue;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
