<#macro layout>
<!DOCTYPE html>
<html lang="en">
<head>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0"/>
    <title>${pageTitile}</title>
    <link rel="stylesheet" href="../css/my.css">
    <link rel="stylesheet" href="../css/weui.css">
    <link rel="stylesheet" href="../css/index.css">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/model.css">
    <link rel="stylesheet" href="../css/myOrder.css">
    <link rel="stylesheet" href="../css/example.css"/>
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.css">
</head>
<script>
    window.onload = function () {
        var wid = window.screen.width;
        document.documentElement.style.fontSize = wid / 10 + 'px';
    };
    window.onresize = function(){
        var width = window.screen.width;
        document.documentElement.style.fontSize = width / 10 + 'px';
    };
</script>
<body>
<#if headerStyle == "simple">
    <#include "components/simpleheader.ftl">
<#else>
    <#include "components/header.ftl">
</#if>
<#nested>
</body>
<script src="../js/zepto.min.js"></script>
<script src="../js/weui.min.js"></script>
<script src="../js/example.js"></script>
</html>
</#macro>