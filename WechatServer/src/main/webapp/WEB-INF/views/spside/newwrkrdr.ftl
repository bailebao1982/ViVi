<#import "../layout/layout.ftl" as mainLayout>

<#assign headerStyle = "simple" in mainLayout.layout>
<#assign pageTitile = "创建工单" in mainLayout.layout>
<#assign userinfo = userinfo in mainLayout.layout>

<@mainLayout.layout>

<div class="index-content">
    <form method="POST" action="/member/register">
        <div class="myOrder-card weui-cells">
            <div id="container">
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input id="service_date" name="service_date" class="weui-input" type="text" placeholder="请选择您的服务日期">
                    </div>
                    <div class="weui-cell__ft" style="display: none;">
                        <i class="weui-icon-warn"></i>
                    </div>
                </div>

                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <input id="telphone" name="phone" class="weui-input" type="text" placeholder="请输入会员名称">
                    </div>
                    <div class="weui-cell__ft" style="display: none;">
                        <i class="weui-icon-warn"></i>
                    </div>
                </div>

                <div class="weui-cell">
                    <div class="weui-cell__hd" style="border-right: 1px solid #9b9b9b">
                        <input type="hidden" value="" name="product_id_0">
                        <input type="hidden" value="" name="package_id_0">
                        <input class="weui-input service_product_select" type="text" placeholder="请服务项目">
                    </div>
                    <div class="weui-cell__bd" style="margin-left: 10px">
                        <input class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入数量">
                    </div>
                </div>
            </div>
        </div>
        <div class="weui-btn-area">
            <div class="weui-flex">
                <div class="weui-flex__item" style="padding: 0px 10px 0px 10px;">
                    <a class="weui-btn weui-btn_plain-default">添加项目</a>
                </div>
                <div class="weui-flex__item" style="padding: 0px 10px 0px 10px;">
                    <button class="weui-btn weui-btn_primary my_weui-btn_primary">确定</button>
                </div>
            </div>
        </div>
    </form>
</div>

<#include "../components/footer.ftl">

<script type="text/html" id="tpl_home">
    <script type="text/javascript">
        $('#service_date').on('click', function () {
            weui.datePicker({
                start: 2017,
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
        $('#container').on('click', '.service_product_select', function () {
            weui.picker([{
                label: '飞机票',
                value: 0
            }, {
                label: '火车票',
                value: 1
            }, {
                label: '的士票',
                value: 2
            },{
                label: '公交票 (disabled)',
                disabled: true,
                value: 3
            }, {
                label: '其他',
                value: 4
            }], {
                onChange: function (result) {
                    console.log(result);
                },
                onConfirm: function (result) {
                    console.log(result);
                }
            });
        });
</script>
</script>

</@mainLayout.layout>