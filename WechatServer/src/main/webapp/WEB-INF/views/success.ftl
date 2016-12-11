<#import "layout.ftl" as mainLayout>

<@mainLayout.layout pageTitile="成功">
<div class="weui-msg">
    <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
    <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">操作成功</h2>
        <p class="weui-msg__desc">${message}</p>
    </div>

    <#include "footer.ftl">
</div>
</@mainLayout.layout>