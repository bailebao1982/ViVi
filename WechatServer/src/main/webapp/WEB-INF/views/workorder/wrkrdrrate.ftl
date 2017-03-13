<#import "layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout>
<#assign pageTitile = "我的工单" in mainLayout>
<#assign userinfo = userinfo in mainLayout>

<@mainLayout.layout>

<div class="container" id="container">
    <div class="page">
        <div id="result_panel" class="bd">
            <div class="weui-panel weui-panel_access">
                <div class="weui-msg">
                    <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
                    <div class="weui-msg__text-area">
                        <h2 class="weui-msg__title">订单确认成功！</h2>
                    </div>
                </div>
            </div>
        </div>
        <div id="order_card" class="myOrder-card" style="display: none;">
            <form method="post" action="/wxserver/workorder/rate/${workorder.workorder_id}">
                <p class="myOrder-number">工单编号<span>${workorder.workorder_id}</span></p>

                <div class="weui-cells__title">您的评分:</div>
                <#include "components/ratepanel.ftl">

                <div class="weui-cells__title">您的评价:</div>
                <div class="weui-cells weui-cells_form">
                    <div class="weui-cell">
                        <div class="weui-cell__bd" style="padding: 5px;border: 1px solid #ccc; border-radius: 2px">
                            <textarea name="rate_comment" class="weui-textarea" placeholder="输入您的宝贵意见" rows="3"></textarea>
                            <div class="weui-textarea-counter">200</div>
                        </div>
                    </div>
                </div>

                <div class="weui-btn-area">
                    <button class="weui-btn weui-btn_primary my_weui-btn_primary">提交</button>
                </div>
            </form>
            <br/>
        </div>
    </div>
</div>
<#include "components/footer.ftl">

<script type="application/javascript">
    setTimeout(function () {
        $('#result_panel').fadeOut(500);
    }, 500);
    setTimeout(function () {
        $('#order_card').fadeIn(1000);
    }, 1000)

</script>

</@mainLayout.layout>