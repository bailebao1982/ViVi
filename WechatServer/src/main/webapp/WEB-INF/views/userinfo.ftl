<#import "layout.ftl" as mainLayout>

<@mainLayout.layout pageTitile="会员信息">
    <div class="weui-panel">
        <div class="weui-panel__bd">
            <div class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box weui-media-box_appmsg">
                    <div class="weui-media-box__hd">
                        <img class="weui-media-box__thumb" src="${userinfo.image}" alt="">
                    </div>
                    <div class="weui-media-box__bd">
                        <h4 class="weui-media-box__title">${userinfo.name}</h4>
                        <p class="weui-media-box__desc">OpenId: ${userinfo.openid}</p>
                    </div>
                </div>
            </div>
            <div class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box weui-media-box_small-appmsg">
                    <div class="weui-media-box__bd">
                        <h4 class="weui-media-box__title">性别: ${userinfo.sex}</h4>
                    </div>
                </div>
            </div>
            <div class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box weui-media-box_small-appmsg">
                    <div class="weui-media-box__bd">
                        <h4 class="weui-media-box__title">城市: ${userinfo.city}</h4>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#include "footer.ftl">
</@mainLayout.layout>