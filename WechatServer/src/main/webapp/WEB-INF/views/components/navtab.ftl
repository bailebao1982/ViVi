<#macro navTab>
<div class="index-footer">
    <div class="index-tab weui-tabbar">
        <#if hidePage != 'PERSONALINFO_PAGE'>
            <div class="weui-tabbar__item"><a href="/member/myinfo/${userinfo.member_id}"><span class="icon-icon_11"></span>个人信息</a></div>
        <#elseif hidePage != 'ASSERT_PAGE'>
            <div class="weui-tabbar__item"><a href="/member/myasset/${userinfo.member_id}"><span class="icon-icon_12"></span>资产列表</div>
        <#elseif hidePage != 'WORKORDER_PAGE'>
            <div class="weui-tabbar__item"><a href="/member/myworkorder/${userinfo.member_id}"><span class="icon-icon_11"></span>我的工单</div>
        </#if>
    </div>
</div>
</#macro>