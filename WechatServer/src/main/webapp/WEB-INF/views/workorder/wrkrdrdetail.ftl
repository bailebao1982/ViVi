<#import "../layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout.layout>
<#assign pageTitile = "我的工单" in mainLayout.layout>
<#assign userinfo = userinfo in mainLayout.layout>

<@mainLayout.layout>

<div class="index-content" id="container">
    <div class="myOrder-card">
        <p class="myOrder-number">工单编号<span>${workorder.workorder_id}</span></p>

        <div class="myOrder-details weui-flex">
            <div class="weui-flex__item"><span>服务日期: ${workorder.service_date}</span></div>
            <div class="weui-flex__item"><span>服务技师: ${workorder.employee.employee_name}</span></div>
        </div>
        <#if workorder.confirmed == 1>
            <div class="myOrder-details weui-flex">
                <div class="weui-flex__item"><span>确认日期: ${workorder.confirm_date}</span></div>
                <div class="weui-flex__item"><span>服务评分: ${workorder.rate}</span></div>
            </div>
            <i class="myOrder-tips tip-readyIndentify">已确认</i>
        <#else>
            <i class="myOrder-tips tip-doneIndentify">待确定</i>
        </#if>
    </div>

    <div class="myOrder-card">
        <p class="myOrder-number">工单项目</p>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>产品编号</th>
                <th>产品名称</th>
                <th>套餐名称</th>
                <th>使用数量</th>
            </tr>
            </thead>
            <tbody>
            <#list workorder.assets as asset >
            <tr>
                <th scope="row">${asset.asset.asset_product.product_serialno}</th>
                <td>${asset.asset.asset_product.product_name}</td>
                <#if asset.asset.asset_type == 1> <!-- 1 => package -->
                    <td>${asset.asset.asset_package.package_name}</td>
                <#else>
                    <td></td>
                </#if>

                <td>${asset.count}</td>
            </tr>
            </#list>
            </tbody>
        </table>

    </div>

    <#if workorder.confirmed == 0>
    <form method="post" action="/workorder/confirm">
        <div class="weui-btn-area">
            <input name="workorder_id" value="${workorder.workorder_id}" type="hidden">
            <button class="weui-btn weui-btn_primary my_weui-btn_primary">确认</button>
        </div>
    </form>
    </#if>
</div>

<#include "../components/footer.ftl">
</@mainLayout.layout>