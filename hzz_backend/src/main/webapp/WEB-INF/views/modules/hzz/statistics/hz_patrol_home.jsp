<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>

<!DOCTYPE html>
<html>

<head>
    <title>巡河统计-首页</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/static/plug-in/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/plug-in/Guriddo_jqGrid_JS_5.4.0-Trial/css/ui.jqgrid-bootstrap.css">

    <script type="text/javascript" src="/static/plug-in/Guriddo_jqGrid_JS_5.4.0-Trial/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="/static/plug-in/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/plug-in/Guriddo_jqGrid_JS_5.4.0-Trial/js/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="/static/plug-in/Guriddo_jqGrid_JS_5.4.0-Trial/js/jquery.jqGrid.min.js"></script>

    <script type="text/javascript" src="/plug-in/echart/echarts.js"></script>
    <script type="text/javascript" src="/plug-in/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/static/js/modules/common/echartOption.js"></script>
    <script type="text/javascript" src="/static/js/modules/common/jqGridOption.js"></script>
    <script type="text/javascript" src="/static/js/modules/statistics/riverpatrol-home.js"></script>


    <style type="text/css">
        .area_color {
            background-color: #FFFFFF;
        }

        .middle {

            float: none;

            display: inline-block;

            vertical-align: middle;
        }
    </style>
    <script>
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $(document).ready(function () {
            $(window).resize(function () {
                $("#area_table").setGridWidth($('#area_row').width() * 0.99);
            });
            let riverPatrol = new RiverPatrol();
            riverPatrol.init();
            riverPatrol.search();
            $('#searchButton').on('click', function () {
                riverPatrol.search();
            })
        });
    </script>
</head>

<body style="background-color: #EFF1F6">
<div class="container" style="width:100%; height:100%">
    <div class="row" style="height: 90px; padding: 12px 5px 0 5px;">
        <div style="height: 100%; background-color: #FFFFFF; padding: 20px 0">
            <form:form id="homeForm" class="form-inline" modelAttribute="homeForm">
                <form:input type="hidden" id="moduleCode" path="moduleCode"></form:input>
                <div class="col-lg-4 col-lg-offset-1">
                    <c:if test="${homeForm.moduleCode eq 'county'}">
                        <label for="city_division">城市：</label>
                        <form:select id="city_division" path="city.code" class="form-control" maxlength="20">
                            <form:option value="-1">请选择城市</form:option>
                            <form:options items="${fns:listByCity()}" itemLabel="name" itemValue="code"
                                          htmlEscape="false"/>
                        </form:select>
                    </c:if>
                    <c:if test="${homeForm.moduleCode eq 'city'}">
                        <label for="province_division">城市：</label>
                        <form:select id="province_division" path="province.code" class="form-control" maxlength="20" disabled="true">
                            <form:option value="320000000000">江苏省</form:option>
                        </form:select>
                    </c:if>
                </div>
                <div class="col-lg-4">
                    <label for="month">月份：</label>
                    <input id="month" name="month" type="text" readonly="readonly" maxlength="20"
                           class="input-medium form-control Wdate"
                           value="<fmt:formatDate value="${homeForm.month}" pattern="yyyy-MM"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
                </div>
                <div class="col-lg-3">
                    <button id="searchButton" type="button" class="btn btn-primary">查询</button>
                </div>
            </form:form>
        </div>
    </div>
    <div id="area_row" class="row" style="height: 360px; padding: 12px 5px 0 5px;">
        <div class="col-lg-4" style="height: 100%; background-color: #FFFFFF;">
            <div align="right">
                <a href="javascript:void(0);" onclick="toEchartHtml('/riverPatrolStats/page/county/echart.do', 'area')">详细信息</a>
            </div>
            <div id="area_main" style="height:90%;" class="text-center">

            </div>
        </div>
        <div class="col-lg-8" style="height: 100%; padding: 0px 0px 0px 5px;">
            <div style="height: 100%; background-color: #FFFFFF;">
                <div align="right">
                    <a href="javascript:void(0);" onclick="toGridHtml('/riverPatrolStats/page/county/grid.do', 'area')">详细信息</a>
                </div>
                <table id="area_table"></table>
            </div>

        </div>
    </div>
    <div class="row" style="height: 360px; padding: 12px 5px 5px 5px;">
        <div class="col-lg-12" style="height: 100%;background-color: #FFFFFF;">
            <div style="height: 100%; background-color: #FFFFFF;">
                <div align="right">
                    <a href="javascript:void(0);" onclick="toGridHtml('/riverPatrolStats/page/county/grid.do', 'river')">详细信息</a>
                </div>
                <table id="river_table"></table>
            </div>
        </div>
    </div>
</div>
</body>

</html>