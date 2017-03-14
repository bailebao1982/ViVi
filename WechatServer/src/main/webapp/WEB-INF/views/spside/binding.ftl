<#import "layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout>
<#assign pageTitile = "员工绑定" in mainLayout>
<#assign userinfo = userinfo in mainLayout>

<@mainLayout.layout>

<div class="index-content">
    <form method="POST" action="/wxserver/serviceprovider/binding">
        <div class="myOrder-card weui-cells">
            <div id="container">
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input id="employeeno" name="employeeno" class="weui-input" type="text" placeholder="请输入员工编号或者手机号" value="">
                    </div>
                    <div class="weui-cell__ft" style="display: none;">
                        <i class="weui-icon-warn"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="weui-btn-area">
            <input name="headerImgUrl" type="hidden" value="${userinfo.member_profilepic}">
            <input name="openId" type="hidden" value="${userinfo.member_wechat}">
            <button class="weui-btn weui-btn_primary my_weui-btn_primary">确定</button>
        </div>
    </form>
</div>

<#include "components/footer.ftl">

</@mainLayout.layout>