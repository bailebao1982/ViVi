<#import "../layout/layout.ftl" as mainLayout>

<#assign headerStyle = "normal" in mainLayout.layout>
<#assign pageTitile = "我的工单" in mainLayout.layout>
<#assign userinfo = userinfo in mainLayout.layout>

<@mainLayout.layout>
<div class="index-content" id="container">

    <#list workorders as workorder>
        <div class="myOrder-card">
            <p class="myOrder-number">工单编号<span>${workorder.workorder_id}</span></p>

            <div class="myOrder-details weui-flex">
                <div class="weui-flex__item"><span>服务日期: ${workorder.service_date}</span></div>
                <div class="weui-flex__item"><span>服务技师: ${workorder.employee.employee_name}</span></div>
            </div>
            <div class="myOrder-details weui-flex">
                <div class="weui-flex__item"><span>服务内容: ${workorder.assets_summary}</span></div>
            </div>
            <#if workorder.confirmed == 1>
                <div class="myOrder-details weui-flex">
                    <div class="weui-flex__item"><span>确认日期: ${workorder.confirm_date}</span></div>
                    <div class="weui-flex__item"><span>服务评分: ${workorder.rate}</span></div>
                </div>
                <div class="weui-flex myOrder-operate">
                    <div class="weui-flex__item workorder_view_btn" id="view_${workorder.workorder_id}">查看详情</div>
                </div>
                <i class="myOrder-tips tip-readyIndentify">已确认</i>
            <#else>
                <div class="weui-flex myOrder-operate">
                    <div class="weui-flex__item" id="confirm_${workorder.workorder_id}"
                         onclick="showModalDlg('${workorder.workorder_id}', '${workorder.service_date}', '${workorder.employee.employee_name}', '${workorder.assets_summary}')">
                        确认工单
                    </div>
                    <div class="weui-flex__item workorder_view_btn" id="view_${workorder.workorder_id}">查看详情</div>
                </div>
                <i class="myOrder-tips tip-doneIndentify">待确定</i>
            </#if>

        </div>
    </#list>
</div>

    <#assign hidePage = "WORKORDER_PAGE">
    <#include "../components/navtab.ftl">

<div id="dialogs">
    <!--BEGIN dialog1-->
    <div class="js_dialog" id="confirmDialog" style="display: none;">
        <form method="POST" action="/workorder/confirm">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div><strong class="weui-dialog__title">确认工单</strong></div>
                <div class="weui-dialog__bd">
                    <div class="weui-media-box weui-media-box_text">
                        <p id="confirm_dlg_date"></p>
                        <p id="confirm_dlg_sp"></p>
                        <p id="confirm_dlg_summary"></p>
                    </div>
                </div>
                <div class="weui-dialog__ft">
                    <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_default cancel_modal_btn">取消</a>
                    <input id="workorder_id_input" name="workorder_id" type="hidden" value="">
                    <button class="weui-dialog__btn weui-dialog__btn_primary">确认</button>
                </div>
            </div>
        </form>
    </div>
    <!--END dialog1-->
</div>

<script type="text/html" id="tpl_home">
    <script type="text/javascript">
        
        function showModalDlg(workorder_id, sdate, sp, summary) {
            $("#confirm_dlg_date").text("服务日期: " + sdate);
            $("#confirm_dlg_sp").text("服务技师: " + sp);
            $("#confirm_dlg_summary").text("服务项目: " + summary);
            $("#workorder_id_input").val(workorder_id);

            $('#confirmDialog').fadeIn(200);
        }
        
        $(function(){
            $('#dialogs').on('click', '.cancel_modal_btn', function(){
                $(this).parents('.js_dialog').fadeOut(200);
            });

//            $('#container').on('click', '.workorder_confirm_btn', function(event){
//                var target_id = event.target.id;
//                var workorder_id = target_id.split("_")[1];
//
//                $("#workorder_id_input").val(workorder_id);
//                $("#workorder_id_input").val(workorder_id);
//
//                $('#confirmDialog').fadeIn(200);
//            });

            $('#container').on('click', '.workorder_view_btn', function(){
                var target_id = event.target.id;
                var workorder_id = target_id.split("_")[1];
                window.location.href = "";
            });
        });
    </script>
</script>

</@mainLayout.layout>