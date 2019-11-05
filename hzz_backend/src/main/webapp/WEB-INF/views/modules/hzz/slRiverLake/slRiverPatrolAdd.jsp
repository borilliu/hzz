<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新增巡河记录</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link href="${webRoot}/plug-in/hplus/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${webRoot}/plug-in/hplus/css/style.css" rel="stylesheet">
    <script src="${webRoot}/plug-in/hplus/js/plugins/chosen/chosen.jquery.js"></script>
    <script type="text/javascript">
        function saveCallBack(data) {
            // $('#mainList').attr('src', $('#mainList').attr('src'));
            var win = frameElement.api.opener;
            if (data.success == true) {
                frameElement.api.close();
                win.reloadIfram();
                win.tip(data.msg);
            } else {
                if (data.responseText == '' || data.responseText == undefined) {
                    $.messager.alert('错误', data.msg);
                    $.Hidemsg();
                } else {
                    try {
                        var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
                        $.messager.alert('错误', emsg);
                        $.Hidemsg();
                    } catch (ex) {
                        $.messager.alert('错误', data.responseText + "");
                        $.Hidemsg();
                    }
                }
                return false;
            }
        }

        //编写自定义JS代码
        $(function () {
            $('#division').combotree({
                url: 'slDivisionController.do?setPFunction',
                width: 155
            });

            $('#riverLake').combotree({
                url : 'slRiverLakeController.do?combotree&param_river_lake_type=10_11_',
                panelHeight : 200,
                width : 157
            });
        });
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="riverLakePatrolController.do?save" callback="@Override saveCallBack">
    <input id="id" name="id" type="hidden"/>
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    巡河人员:
                </label>
            </td>
            <td class="value">
                <input id="riverLakeChiefId" name="riverLakeChief.id" type="hidden" value="" />
                <input id="riverLakeChiefName" name="riverLakeChief.name" class="inputxt" value="" readonly="readonly" datatype="*" />
                <t:choose hiddenName="riverLakeChiefId" inputTextname="riverLakeChiefName" hiddenid="id" url="riverLakeChiefController.do?riverLakeChiefs" name="riverLakeChiefList" icon="icon-search" title="选择巡查人" textname="name" isclear="true" isInit="true"></t:choose>

<%--                <input id="user" name="riverLakeChief.name" type="text" maxlength="32" style="width: 150px" class="inputxt" ignore="ignore"/>--%>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">巡河人员</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    行政区划:
                </label>
            </td>
            <td class="value">
                <input id="division" name="division.code">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">行政区划</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    巡查里程:
                </label>
            </td>
            <td class="value">
                <input id="patrolMileage" name="patrolMileage" type="text" maxlength="32" style="width: 150px" class="inputxt" ignore="ignore"/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">巡查里程</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    开始巡查时间:
                </label>
            </td>
            <td class="value">
                <input id="patrolStartDate" name="patrolStartDate" type="text" style="width: 150px" class="Wdate"
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*" ignore="checked"/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">开始巡查时间</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    结束巡查时间:
                </label>
            </td>
            <td class="value">
                <input id="patrolEndDate" name="patrolEndDate" type="text" style="width: 150px" class="Wdate"
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*" ignore="checked"/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">结束巡查时间</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    巡查河流属性:
                </label>
            </td>
            <td class="value">
                <t:dictSelect type="select" field="category" typeGroupCode="riverCategory" title="巡查河流属性"></t:dictSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">巡查河流属性</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    巡查河流:
                </label>
            </td>
            <td class="value">
                <input id="riverLake" name="tslBaseRiverLake.code">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">巡查河流</label>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>
