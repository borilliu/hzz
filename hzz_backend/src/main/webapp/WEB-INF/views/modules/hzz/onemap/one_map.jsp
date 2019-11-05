<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>一张图</title>
    <link rel="stylesheet" href="${ctxStatic}/plug-in/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link rel="stylesheet" href="${ctxStatic}/plug-in/select2/css/select2.min.css"/>
    <style type="text/css">
        html {
            height: 100%
        }

        body {
            height: 100%;
            margin: 0px;
            padding: 0px
        }

        #container {
            height: 100%
        }

        .baidu-maps label {
            max-width: none;
        }
    </style>
</head>

<body>
    <div id="container" class="baidu-maps"></div>
</body>

</html>
<script type="text/javascript" src="${webRoot}/plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/select2/js/select2.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=3.0&ak=7ZiXhEu9qpQAnEIRmO6mDkW2uP1IVYCT"></script>
<!--限定显示区域api库-->
<script type="text/javascript"
        src="http://api.map.baidu.com/library/AreaRestriction/1.2/src/AreaRestriction_min.js"></script>
<%--<script src="${ctxStatic}/plug-in/bootstrap-select/1.12.4/js/bootstrap-select.min.js" type="text/javascript"></script>--%>
<%--<script src="${ctxStatic}/plug-in/bootstrap-select/1.12.4/js/i18n/defaults-zh_CN.min.js" type="text/javascript"></script>--%>
<script type="text/javascript" src="${ctxStatic}/js/modules/onemap/division_control.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/modules/onemap/one_map.js"></script>

<script>

</script>