<#macro layout>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${pageTitile}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link rel="stylesheet" href="../style/style.css"/>
    <link rel="stylesheet" href="http://cdn.bootcss.com/weui/1.1.0/style/weui.min.css"/>
</head>
<body>
	<div class="page__bd page__bd_spacing">
	    <div class="page__hd"></div>
	    <#nested>
	</div>
</body>
</html>
</#macro>