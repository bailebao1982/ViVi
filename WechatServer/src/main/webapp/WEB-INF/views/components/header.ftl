<div class="my-header">
    <div class="my-info">
        <#if userinfo != null>
            <div class="my-face">
                <img src="${userinfo.member_profilepic}">
            </div>
            <div class="my-name">
            ${userinfo.member_name}
            <#if userinfo.member_sex == 'M'>
                <span class="icon-icon_3"></span>
            <#else>
                <span class="icon-icon_3"></span>
            </#if>
            </div>
        </#if>
    </div>
</div>