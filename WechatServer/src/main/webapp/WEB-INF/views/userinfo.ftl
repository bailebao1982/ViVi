<!DOCTYPE html>
<html lang="en">
<head>
    <title>Auth Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link rel="stylesheet" href="http://cdn.bootcss.com/weui/1.1.0/style/weui.min.css"/>
</head>
<body>
<div class="userinfo_header">
    <img src="${userinfo.image}" width="60" height="60"/>
    ${userinfo.name}
</div>
<table class="table table-striped">
    <tr>
        <td>OpenId:</td>
        <td>${userinfo.openid}</td>
    </tr>
    <tr>
        <td>性别:</td>
        <td>${userinfo.sex}</td>
    </tr>
    <tr>
        <td>城市:</td>
        <td>${userinfo.city}</td>
    </tr>
</table>

<div class="weui-msg__extra-area">
    <div class="weui-footer">
        <p class="weui-footer__links">
            <a href="javascript:void(0);" class="weui-footer__link">底部链接文本</a>
        </p>
        <p class="weui-footer__text">Copyright &copy; 2009-2016 Plume.Studio</p>
    </div>
</div>

</body>
</html>