<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="/plug-in/ace/css/bootstrap.css">
    <link rel="stylesheet" href="/plug-in/jquery-plugs/jqGrid-4.6.0/css/ui.jqgrid.css">

    <title>巡河统计</title>

    <script type="text/javascript" src="/plug-in/echart/echarts.js"></script>
    <script type="text/javascript" src="/plug-in/jquery-plugs/jqGrid-4.6.0/jquery.js"></script>
    <script type="text/javascript" src="/plug-in/jquery-plugs/jqGrid-4.6.0/js/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="/plug-in/jquery-plugs/jqGrid-4.6.0/js/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="/static/js/modules/common/echartOption.js"></script>
    <script type="text/javascript" src="/static/js/modules/common/jqGridOption.js"></script>
    <script type="text/javascript" src="/static/js/modules/statistics/riverpatrol-home.js"></script>
    <style>
        * {
            padding: 0;
            margin: 0;
        }
    </style>
    <script>
        $(function () {
            resizeDivContainer();
        });

        $(window).resize(function () {
            resizeDivContainer();
        });

        function resizeDivContainer() {
            var windowH = $(window).height();
            var windowW = $(window).width();

            var h = (windowH - $("#div0").height() - 40) / 2;

            //$(document).attr("title", "" + windowH + "，" + h);

            //$("#div0").html(windowbody + "," + h);
            $("#div1").height(h);
            $("#div2").height(h);

            var w = h / 2 * 3;
            $("#div1_1").width(w);
            $("#div2_1").width(w);

            $("#div1_2").width(windowW - w - 20 - 10 - 4);
            $("#div2_2").width(windowW - w - 20 - 10 - 4);
        }

        function queryData() {
            alert("查询");
        }

        function exportData() {
            alert("导出");
        }
    </script>
</head>
<body>
    <div id="div0" style="position: absolute; top: 10px; left: 10px; right: 10px; height: 60px; line-height: 60px; overflow: hidden; background-color: yellow;">
        <div style="float: left; margin-left: 20px; font-size: 16px; color: #585858;">江苏省：</div>
        <div style="float: left;">
            <select id="Select1" style="height: 30px; width: 100px; color: RGBA(102,102,102,0.7); line-height: 30px;">
                <option>徐州市</option>
                <option>南京市</option>
            </select>
        </div>
        <div style="float: left; margin-left: 20px; font-size: 16px; color: #585858;">开始时间：</div>
        <div style="float: left;">
            <input id="Text1" type="text" style="height: 20px; width: 100px; padding: 2px;" />
        </div>
        <div style="float: left; margin-left: 20px; font-size: 16px; color: #585858;">结束时间：</div>
        <div style="float: left;">
            <input id="Text2" type="text" style="height: 20px; width: 100px; padding: 2px;" />
        </div>

        <div style="float: left; margin-left: 20px;"><a href="javascript:void(0);" onclick="queryData()">查询</a></div>
        <div style="float: left; margin-left: 10px;"><a href="javascript:void(0);" onclick="exportData()">导出</a></div>

        <div style="clear: both;"></div>
    </div>

    <div id="div1" style="position: absolute; top: 80px; left: 10px; right: 10px; height: 60px; overflow: hidden;">
        <div id="div1_1" style="position: absolute; top: 2px; bottom: 2px; left: 2px; width: 100px; background-color: #eeeeee;">
            <div style="position: absolute; top: 0; left: 0; right: 0; height: 40px; line-height: 40px; background: url('/images/hzxhtj/left-icon.png') 20px center no-repeat;">
                <div style="position: absolute; top: 0; left: 40px; right: 20px; height: 40px; line-height: 40px; font-size: 18px; font-family: 'Microsoft YaHei'; font-weight: bold;">
                    地区图表
                </div>
            </div>
            <div style="position: absolute; top: 40px; left: 0; right: 0; bottom: 0; background-color: yellow; overflow: auto;">
                <div id="area_main" style="width: 520px; height: 310px"></div>
            </div>
        </div>
        <div id="div1_2" style="position: absolute; top: 2px; bottom: 2px; right: 2px; width: 100px; background-color: #eeeeee;">
            <div style="position: absolute; top: 0; left: 0; right: 0; height: 40px; line-height: 40px; background: url('/images/hzxhtj/left-icon.png') 20px center no-repeat;">
                <div style="position: absolute; top: 0; left: 40px; right: 20px; height: 40px; line-height: 40px; font-size: 18px; font-family: 'Microsoft YaHei'; font-weight: bold;">
                    地区表格
                </div>
            </div>
            <div style="position: absolute; top: 40px; left: 0; right: 0; bottom: 0; background-color: yellow; overflow: auto;">
                <table id="area_table" class="table"></table>
            </div>
        </div>
    </div>

    <div id="div2" style="position: absolute; bottom: 10px; left: 10px; right: 10px; height: 60px; overflow: hidden;">
        <div id="div2_1" style="position: absolute; top: 2px; bottom: 2px; left: 2px; width: 100px; background-color: #eeeeee;">
            <div style="position: absolute; top: 0; left: 0; right: 0; height: 40px; line-height: 40px; background: url('/images/hzxhtj/left-icon.png') 20px center no-repeat;">
                <div style="position: absolute; top: 0; left: 40px; right: 20px; height: 40px; line-height: 40px; font-size: 18px; font-family: 'Microsoft YaHei'; font-weight: bold;">
                    河长图表
                </div>
            </div>
            <div style="position: absolute; top: 40px; left: 0; right: 0; bottom: 0; background-color: yellow; overflow: auto;">
                <div id="river_main" style="width: 520px; height: 310px"></div>
            </div>
        </div>
        <div id="div2_2" style="position: absolute; top: 2px; bottom: 2px; right: 2px; width: 100px; background-color: #eeeeee;">
            <div style="position: absolute; top: 0; left: 0; right: 0; height: 40px; line-height: 40px; background: url('/images/hzxhtj/left-icon.png') 20px center no-repeat;">
                <div style="position: absolute; top: 0; left: 40px; right: 20px; height: 40px; line-height: 40px; font-size: 18px; font-family: 'Microsoft YaHei'; font-weight: bold;">
                    河长表格
                </div>
            </div>
            <div style="position: absolute; top: 40px; left: 0; right: 0; bottom: 0; background-color: yellow; overflow: auto;">
                <table id="river_table" class="table"></table>
            </div>
        </div>
    </div>
</body>
</html>
