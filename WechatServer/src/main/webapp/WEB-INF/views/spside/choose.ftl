<#import "layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout>
<#assign pageTitile = "会员注册" in mainLayout>
<#assign userinfo = userinfo in mainLayout>

<@mainLayout.layout>

<div class="index-content">
    <div class="weui-btn-area">
        <input id="nickname" name="nickname" type="hidden" value="${userinfo.member_name}">
        <input id="gender" name="gender" type="hidden" value="${userinfo.member_sex}">
        <input id="headerImgUrl" name="headerImgUrl" type="hidden" value="${userinfo.member_profilepic}">
        <input id="openId" name="openId" type="hidden" value="${userinfo.member_wechat}">
        <input id="address" name="address" type="hidden" value="${userinfo.member_address}">
        <button id="showReg" class="weui-btn weui-btn_primary my_weui-btn_primary">注册新员工</button>
        <button id="showBinding"  class="weui-btn weui-btn_primary my_weui-btn_primary">绑定已有账户</button>
    </div>
</div>

    <#include "components/footer.ftl">

<script type="text/html" id="tpl_home">
    <script type="text/javascript">
        $('#showReg').on('click', function (event) {
            console.log("response show_reg");
            var nickname = $('#nickname').val();
            var gender = $('#gender').val();
            var headerImgUrl = $('#headerImgUrl').val();
            var openId = $('#openId').val();
            var address = $('#address').val();

            var loc = "/wxserver/serviceprovider/register?nickname=" + nickname +
                        "&gender=" + gender +
                        "&headImgUrl=" + headerImgUrl +
                        "&openId=" + openId +
                        "&address=" + address;
            window.location.href = loc;
        });
        $('#showBinding').on('click', function (event) {
            console.log("response show_binding");
            var nickname = $('#nickname').val();
            var gender = $('#gender').val();
            var headerImgUrl = $('#headerImgUrl').val();
            var openId = $('#openId').val();
            var address = $('#address').val();

            var loc = "/wxserver/serviceprovider/binding?nickname=" + nickname +
                "&gender=" + gender +
                "&headImgUrl=" + headerImgUrl +
                "&openId=" + openId +
                "&address=" + address;
            window.location.href = loc;
        });
</script>
</script>

</@mainLayout.layout>