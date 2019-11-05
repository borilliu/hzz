<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>河长统计报表</title>
    <t:base type="jquery,easyui,tools"></t:base>
    <link rel="stylesheet" type="text/css" href="/plug-in/ztree/css/zTreeStyle.css">

    <script type="text/javascript" src="/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="/plug-in/ztree/js/ztreeCreator.js"></script>
    <script type="text/javascript" src="/static/js/modules/statistics/hz_report_forms.js"></script>
</head>
<body>
<div class="easyui-layout" fit="true" scroll="no">
    <div data-options="region:'west',title:'行政地区',split:true" style="width: 254px; overflow: auto;">
        <div id="areaTree" class="ztree"></div>
    </div>
    <div id="iframeDiv" data-options="region:'center',border:false" style="text-align: center; overflow: hidden;">
        <iframe id="listFrame" src="/hz/report/page/main.do" frameborder="no" width="100%" height="100%"></iframe>
    </div>
</div>
</body>
</html>
<script>

</script>
