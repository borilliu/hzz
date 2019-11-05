<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/10/15
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>巡河问题处理完成情况</title>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        html,
        body {
            height: 100%;
            overflow: hidden;
        }

        .left {
            float: left;
            margin: 0 70px;
        }

        .indexbox {
            width: 140px;
            height: 80px;
            background-color: #6777ef;
            float: left;
            margin-top: 10px;
            margin-left: 40px;
            border-radius: 10px;
        }

        .indexcout {
            font-size: 24px;
            font-weight: 100;
            text-align: center;
            color: #efefef;
            margin-top: 10px;
        }

        .indextext {
            color: #FFF;
            text-align: center;
        }
    </style>
    <link rel="stylesheet" href="/static/plug-in/bootstrap-3.3.7-dist/css/bootstrap.min.css">

    <script type="text/javascript"
            src="/static/js/modules/common/hzz.js"></script>
    <script type="text/javascript"
            src="/plug-in/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript"
            src="/static/plug-in/echart4.3.0/dist/echarts.min.js"></script>
    <script type="text/javascript"
            src="/static/plug-in/echart4.3.0/dist/extension/dataTool.min.js"></script>
    <script type="text/javascript"
            src="/static/plug-in/echart4.3.0/dist/extension/bmap.min.js"></script>
    <script type="text/javascript"
            src="/static/js/modules/statistics/problemstats/problem_stats_home.js"></script>
</head>

<body>
<div style="padding-left:50px;height: 140px; border: 2px solid #efefef; font-family: 'Microsoft YaHei'; line-height: 30px; font-size: 14px; margin: 10px; overflow: hidden;">
    <form:form id="homeForm" class="form-inline" modelAttribute="problemHandleStatsForm">
        <form:input type="hidden" id="areacode" path="area.code"/>
        <form:input type="hidden" path="statsType"/>
        <div class="row">
            <div class="col-lg-2">
                <label for="startDay">开始时间：</label>
                <input id="startDay" name="startDay" type="text" readonly="readonly" maxlength="20"
                       class="input-medium form-control Wdate"
                       value="<fmt:formatDate value="${problemHandleStatsForm.startDay}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            </div>
            <div class="col-lg-2">
                <label for="endDay">结束时间：</label>
                <input id="endDay" name="endDay" type="text" readonly="readonly" maxlength="20"
                       class="input-medium form-control Wdate"
                       value="<fmt:formatDate value="${problemHandleStatsForm.endDay}" pattern="yyyy-MM-dd"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            </div>
            <div class="col-lg-8">
                <button id="searchbtn" type="button" class="btn btn-primary" onclick="search()">查询</button>
                <button id="searBycurYearbtn" type="button" class="btn btn-primary" onclick="searBycurYear()">本年
                </button>
                <button id="searBycurMonthbtn" type="button" class="btn btn-primary" onclick="searBycurMonth()">本月
                </button>
            </div>
        </div>
        <div class="row">
            <div id="divArea"></div>
            <div id="tblContent" style="float:left; margin-top:10px; margin-left:5px; height:auto;line-height:32px;font-size:14px; "></div>
        </div>
    </form:form>
</div>
<div style="padding-left:50px;height: 100px; border: 2px solid #efefef; font-family: 'Microsoft YaHei'; line-height: 30px; font-size: 14px; margin: 10px; overflow: hidden;">
    <div class="left">
        <p>
            <label>地区：</label>
            <span id="areaname"></span>
        </p>
        <p>
            <label>时间：</label>
            <span id="searchdate"></span>
        </p>
    </div>
    <div class="left">
        <div class="indexbox">
            <div id="statsTypeTotal" class="indexcout">12</div>
            <div class="indextext">类型总数</div>
        </div>
    </div>

    <div class="left">
        <div class="indexbox" style="background-color:#51e3c0">
            <div id="eventTotal" class="indexcout">12</div>
            <div class="indextext">事件总数</div>
        </div>
    </div>

    <div class="left">
        <div class="indexbox" style="background-color:#ffa426">
            <div id="processedTotal" class="indexcout">12</div>
            <div class="indextext">已处理总数</div>
        </div>
    </div>

    <div class="left">
        <div class="indexbox" style="background-color:#fc544b">
            <div id="pendingTotal" class="indexcout">12</div>
            <div class="indextext">待处理总数</div>
        </div>
    </div>
</div>

<div id="div1" style="height: 100px; margin: 10px;">
    <div id="div1_1" style="float: left; height: 100%; width: 200px; background-color: #efefef; overflow: hidden;">
        <p style=" text-align:center">2019年1-9月巡河问题占比图</p>
        <div id="pratolRatioEchart" style="width:100%;height: 90%;"></div>
    </div>
    <div id="div1_2" style="float: right; height: 100%; width: 200px; background-color: #efefef; overflow: hidden;">
        <p style="text-align:center">2019年1-9月巡河问题处理完成情况</p>
        <div id="proceesedEchart" style="width:100%;height: 90%;"></div>
    </div>
    <div style="clear: both;"></div>
</div>
<div id="div2" style="height: 100px; background-color: #efefef; overflow: hidden;">
    <p style="text-align:center">近一年巡河问题处理完成次数变化趋势图</p>
    <div id="yearEchart" style="width:100%;height: 90%;"></div>
</div>
</body>

</html>
