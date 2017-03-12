<#import "layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout.layout>
<#assign pageTitile = "会员绑定" in mainLayout.layout>
<#assign userinfo = userinfo in mainLayout.layout>

<@mainLayout.layout>

<div class="index-content">
    <form method="POST" action="/member/binding">
        <div class="myOrder-card weui-cells">
            <div id="container">
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input id="memberno" name="memberno" class="weui-input" type="text" placeholder="请输入会员编号或者手机号" value="">
                    </div>
                    <div class="weui-cell__ft" style="display: none;">
                        <i class="weui-icon-warn"></i>
                    </div>
                </div>

                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input id="password" name="password" class="weui-input" type="password" placeholder="请设置您的密码" value="">
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