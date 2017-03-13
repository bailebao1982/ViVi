<#import "layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout>
<#assign pageTitile = "员工注册" in mainLayout>
<#assign userinfo = userinfo in mainLayout>

<@mainLayout.layout>

<div class="index-content">
    <form method="POST" action="/employee/register">
        <div class="myOrder-card weui-cells">
            <div id="container">
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input id="telphone" name="telphone" class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入您的联系电话">
                    </div>
                    <div class="weui-cell__ft" style="display: none;">
                        <i class="weui-icon-warn"></i>
                    </div>
                </div>

                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input id="birthday" name="birthday" class="weui-input" type="text" placeholder="请选择您的输入生日">
                    </div>
                    <div class="weui-cell__ft" style="display: none;">
                        <i class="weui-icon-warn"></i>
                    </div>
                </div>

                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input id="address" name="address" class="weui-input" type="text" placeholder="请输入您的地址">
                    </div>
                    <div class="weui-cell__ft" style="display: none;">
                        <i class="weui-icon-warn"></i>
                    </div>
                </div>

                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input id="employee_type" name="employee_type" class="weui-input" type="text" placeholder="请选择您的类别">
                    </div>
                    <div class="weui-cell__ft" style="display: none;">
                        <i class="weui-icon-warn"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="weui-btn-area">
            <input name="nickname" type="hidden" value="${userinfo.nickname}">
            <input name="gender" type="hidden" value="${userinfo.gender}">
            <input name="headerImgUrl" type="hidden" value="${userinfo.headerImgUrl}">
            <input name="openId" type="hidden" value="${userinfo.openId}">
            <button class="weui-btn weui-btn_primary my_weui-btn_primary">确定</button>
        </div>
    </form>
</div>

<#include "components/footer.ftl">

<script type="text/html" id="tpl_home">
    <script type="text/javascript">
        $('#birthday').on('click', function () {
            weui.datePicker({
                start: 1970,
                end: new Date().getFullYear(),
                onChange: function (result) {
                    console.log(result);
                },
                onConfirm: function (result) {
                    var year = result[0];
                    var month = result[1] + 1;
                    var day = result[2];
                    var date = year + '年' + month + '月' + day + '日';
                    console.log(date);
                    $('#birthday').val(date);
                }
            });
        });
</script>
</script>

</@mainLayout.layout>