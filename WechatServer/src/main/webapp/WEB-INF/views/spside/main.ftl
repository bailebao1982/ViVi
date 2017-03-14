<#import "layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout>
<#assign pageTitile = "ViVA天玺瑗皮肤管理" in mainLayout>
<#assign userinfo = userinfo in mainLayout>

<@mainLayout.layout>

<div class="my-content">
    <ul>
        <li onclick="redirect2Page('myInfo')">
            <span class="icon-icon_13"></span>
            <span>个人信息</span>
            <span></span>
            <span class="icon-icon_15"></span>
        </li>
        <li onclick="redirect2Page('myWorkOrder')">
            <span class="icon-icon_12"></span>
            <span>服务清单</span>
            <span></span>
            <span class="icon-icon_15"></span>
        </li>
        <li onclick="redirect2Page('createWorkOrder')">
            <span class="icon-icon_12"></span>
            <span>创建工单</span>
            <span></span>
            <span class="icon-icon_15"></span>
        </li>
    </ul>
</div>

<#include "components/footer.ftl">

<script type="application/javascript">
    function redirect2Page(page) {
        if(page == 'myInfo'){
            window.location.href = "/serviceprovider/myinfo/${userinfo.member_id}";
        }else if(page == 'myWorkOrder'){
            window.location.href = "/serviceprovider/createworkorder/${userinfo.member_id}";
        }else if(page == 'createWorkOrder'){
            window.location.href = "/serviceprovider/myworkorder/${userinfo.member_id}";
        }
    }
</script>
</@mainLayout.layout>