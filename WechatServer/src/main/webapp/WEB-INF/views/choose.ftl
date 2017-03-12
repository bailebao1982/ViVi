<#import "layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout.layout>
<#assign pageTitile = "会员注册" in mainLayout.layout>
<#assign userinfo = userinfo in mainLayout.layout>

<@mainLayout.layout>

<div class="index-content">
    <div class="weui-btn-area">
        <input id="nickname" name="nickname" type="hidden" value="${userinfo.member_name}">
        <input id="gender" name="gender" type="hidden" value="${userinfo.member_sex}">
        <input id="headerImgUrl" name="headerImgUrl" type="hidden" value="${userinfo.member_profilepic}">
        <input id="openId" name="openId" type="hidden" value="${userinfo.member_wechat}">
        <input id="address" name="address" type="hidden" value="${userinfo.member_address}">
        <button id="show_reg" class="weui-btn weui-btn_primary my_weui-btn_primary">注册新会员</button>
        <button id="show_binding"  class="weui-btn weui-btn_primary my_weui-btn_primary">绑定已有账户</button>
    </div>
</div>

    <#include "components/footer.ftl">

<script type="text/html" id="tpl_home">
    <script type="text/javascript">
        $('#show_reg').on('click', function () {
            var nickname = $('#nickname').val();
            var gender = $('#gender').val();
            var headerImgUrl = $('#headerImgUrl').val();
            var openId = $('#openId').val();
            var address = $('#address').val();

            window.location.href = "/member/register?nickname=" + nickname +
                            "&gender=" + gender +
                            "&headerImgUrl=" + headerImgUrl +
                            "&openId=" + openId +
                            "&address" + address;
        });
        $('#show_binding').on('click', function () {
            var nickname = $('#nickname').val();
            var gender = $('#gender').val();
            var headerImgUrl = $('#headerImgUrl').val();
            var openId = $('#openId').val();
            var address = $('#address').val();

            window.location.href = "/member/binding?nickname=" + nickname +
                            "&gender=" + gender +
                            "&headerImgUrl=" + headerImgUrl +
                            "&openId=" + openId +
                            "&address" + address;
        });
</script>
</script>

</@mainLayout.layout>