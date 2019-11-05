<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>行政区划</title>
    <t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
    <script type="text/javascript" src="/plug-in/Validform/js/Validform_v5.3.1.js"></script>
    <style type="text/css">
        .combo_self {
            height: 22px !important;
            width: 150px !important;
        }

        .layout-header .btn {
            margin: 0;
            float: none !important;
        }

        .btn-default {
            height: 35px;
            line-height: 35px;
            font-size: 14px;
        }
    </style>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="slRiverLakeController.do?saveEntityPR">
    <input id="id" name="id" type="hidden" value="${entity.id}"/>
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
            <tr>
                <td align="right">
                    <label class="Validform_label"> *行政区划:</label>
                </td>
                <td class="value">
                    <input id="categoryTree" value="${entity.tslDivision.divisionName}"
                        <c:if test="${not empty entity.id}">
                           disabled="disabled"
                        </c:if>
                    >
                    <input id="parentId" name="parent.code" style="display: none;"
                            <c:if test="${empty entity.id}">
                                   datatype="recordUniqueness"
                            </c:if>
                           value="${entity.tslDivision.code}">
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">行政区划名称</label>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label"> *年月:</label>
                </td>
                <td class="value">
                    <input id="ym" name="ym" type="text" maxlength="32" class="inputxt" datatype="*"
                           value="${entity.ym }"
                           class="Wdate" onClick="WdatePicker({dateFmt:'yyyyMM'})"
                           style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent; width: 120px;"
                            <c:if test="${empty entity.id}">
                                datatype="recordUniqueness"
                            </c:if>
                            <c:if test="${not empty entity.id}">
                                disabled="disabled"
                            </c:if>
                    />
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">划描述</label>
                </td>
            </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> 应巡人数: </label>
            </td>
            <td class="value">
                <input class="inputxt" id="shouldNumber"
                       name="shouldNumber" datatype="d" errormsg="数字非法"
                       value="${entity.shouldNumber}">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">已巡人数</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> 已巡人数:</label>
            </td>
            <td class="value">
                <input class="inputxt" id="visitedNumber"
                       name="visitedNumber" datatype="d" errormsg="数字非法"
                       value="${entity.visitedNumber}">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">已巡人数</label>
            </td>
        </tr>
    </table>
</t:formvalid>
<script type="text/javascript">
    $(function () {
        $('#categoryTree').combotree({
            url: 'slDivisionController.do?setPFunction&selfId=${division.code}',
            width: 155,
            onSelect: function (node) {

                $("#parentId").val(node.id);

            }
        });

        if ('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#categoryTree').combotree('setValue', '${pid}');
        }

        // 由于document加载顺序是自上而下的，自定义Validform初始化参数说明要放在页面最后加载，才可以覆盖jeecg标签t:formvalid的Validform初始化参数说明
        $("#formobj").Validform({
            btnSubmit: "#btn_sub",
            tiptype: 1,
            // postonce:true,
            datatype: {
                "recordUniqueness": function (gets, obj, curform, regxp) {
                    //参数gets是获取到的表单元素值，obj为当前表单元素，curform为当前验证的表单，regxp为内置的一些正则表达式的引用;
                    var result = true;
                    var division = curform.find(":input[name='parent.code']").val();
                    var date = curform.find(":input[name='ym']").val();
                    var params = {
                        divisionCode: division,
                        yearMonth: date
                    };

                    $.ajax({
                        async: false,
                        url: "patrolReportController/checkRecordUniqueness.do",
                        type: "POST",
                        dataType: "JSON",
                        data: params,
                        success: function (data) {
                            if (data.status === "n") {
                                result = data.info;
                            }
                        },
                        error: function () {
                            result = "验证服务器异常，请联系管理员！"
                        }
                    });
                    return result;
                    //注意return可以返回true 或 false 或字符串文字，true表示验证通过，返回字符串表示验证失败，字符串作为错误提示显示，返回false则用errmsg或默认的错误提示;
                }
            }
        });
    });

    function setContentc() {
        var cc = $("input[name='parent.code']").val();
        if (isEmpty(cc)) {
            //$.messager.alert('错误', "监测断面没有输入");
            layer.msg("行政区划没有输入!");
            return false;
        }
    }

    //判断字符是否为空的方法
    function isEmpty(obj) {
        if (typeof obj == "undefined" || obj == null || obj == "") {
            return true;
        } else {
            return false;
        }
    }
</script>
</body>