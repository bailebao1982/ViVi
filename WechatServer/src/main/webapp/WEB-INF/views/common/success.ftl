<#import "layout/layout.ftl" as mainLayout>
<#assign headerStyle = "simple" in mainLayout>
<#assign pageTitile = "操作成功" in mainLayout>
<#assign userinfo = userinfo in mainLayout>

<@mainLayout.layout>
<div class="container" id="container">
    <div class="page">
        <div class="bd">
            <div class="weui-panel weui-panel_access">
                <div class="weui-msg">
                    <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
                    <div class="weui-msg__text-area">
                        <h2 class="weui-msg__title">操作成功！</h2>
                        <p class="weui-msg__desc">${message}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#assign hidePage = "">
<#include "components/navtab.ftl">
</@mainLayout.layout>