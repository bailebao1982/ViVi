<#import "../layout/layout.ftl" as mainLayout>
<#assign headerStyle = "simple" in mainLayout.layout>
<#assign pageTitile = "发生了错误" in mainLayout.layout>
<#assign userinfo = userinfo in mainLayout.layout>

<@mainLayout.layout>
<div class="container" id="container">
    <div class="page">
        <div class="bd">
            <div class="weui-panel weui-panel_access">
                <div class="weui-msg">
                    <div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
                    <div class="weui-msg__text-area">
                        <h2 class="weui-msg__title">发生了错误</h2>
                        <p class="weui-msg__desc">${message}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../components/footer.ftl">
</@mainLayout.layout>