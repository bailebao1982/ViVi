<#import "layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout>
<#assign pageTitile = "我的资产" in mainLayout>
<#assign userinfo = userinfo in mainLayout>

<@mainLayout.layout>

<div class="index-content" id="container">
    <div class="myOrder-card">
        <p class="myOrder-number">我的积分 & 余额</p>
        <div class="myOrder-details weui-flex">
            <div class="weui-flex__item" style="font-size: 20px"><span>积分: ${userinfo.member_bonusPoint}</span></div>
            <div class="weui-flex__item" style="font-size: 20px"><span>
                <#if depositAsset == null>
                    余额: ${depositAsset.deposit}
                <#else>
                    余额: 0
                </#if>
            </span></div>
        </div>
    </div>

    <div class="myOrder-card">
        <p class="myOrder-number">我的产品</p>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>产品编号</th>
                <th>产品名称</th>
                <th>剩余数量</th>
            </tr>
            </thead>
            <tbody>
            <#list productAssets as productAsset>
                <tr>
                    <th scope="row">${productAsset.asset_product.product_serialno}</th>
                    <td>${productAsset.asset_product.product_name}</td>
                    <td>${productAsset.asset_product.count}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="myOrder-card">
        <p class="myOrder-number">我的套餐</p>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>产品编号</th>
                <th>产品名称</th>
                <th>套餐名称</th>
                <th>剩余数量</th>
            </tr>
            </thead>
            <tbody>
                <#list pkgPrdtAssets as pkgPrdtAsset>
                <tr>
                    <th scope="row">${pkgPrdtAsset.asset_product.product_serialno}</th>
                    <td>${pkgPrdtAsset.asset_product.product_name}</td>
                    <td>${pkgPrdtAsset.asset_package.package_name}</td>
                    <td>${pkgPrdtAsset.asset_product.count}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>

<#assign hidePage = "ASSERT_PAGE">
<#include "components/navtab.ftl">
</@mainLayout.layout>