<#import "layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout>
<#assign pageTitile = "我的工单" in mainLayout>
<#assign userinfo = userinfo in mainLayout>

<@mainLayout.layout>
<div class="index-content" id="container">

    <#list workorders as workorder>
        <div class="myOrder-card">
            <p class="myOrder-number">工单编号<span>${workorder.workorder_id}</span></p>

            <div class="myOrder-details weui-flex">
                <div class="weui-flex__item"><span>服务日期: ${workorder.service_date}</span></div>
                <div class="weui-flex__item"><span>服务客户: ${workorder.member.member_name}</span></div>
            </div>
            <div class="myOrder-details weui-flex">
                <div class="weui-flex__item"><span>服务内容: ${workorder.assets_summary}</span></div>
            </div>
            <#if workorder.confirmed == 1>
                <div class="myOrder-details weui-flex">
                    <div class="weui-flex__item"><span>确认日期: ${workorder.confirm_date}</span></div>
                    <div class="weui-flex__item"><span>客户评分: ${workorder.rate}</span></div>
                </div>

                <i class="myOrder-tips tip-readyIndentify">已确认</i>
            <#else>
                <i class="myOrder-tips tip-doneIndentify">待确定</i>
            </#if>
        </div>
    </#list>
</div>

<#include "components/footer.ftl">

</script>

</@mainLayout.layout>