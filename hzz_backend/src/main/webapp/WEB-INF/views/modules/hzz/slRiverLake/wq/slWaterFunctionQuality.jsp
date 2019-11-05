<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>行政区划</title>
    <t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
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

    <script>
        (function ($) {

            //设置值
            function _setValues(jq, values, remainText) {
                var options = $.data(jq, "combogrid").options;
                var grid = $.data(jq, "combogrid").grid;
                var rows = grid.datagrid("getRows");
                var ss = [];
                for (var i = 0; i < values.length; i++) {
                    var index = grid.datagrid("getRowIndex", values[i]);
                    if (index >= 0) {
                        grid.datagrid("selectRow", index);
                        ss.push(rows[index][options.textField]);
                    } else {
                        ss.push(values[i]);
                    }
                }
                if ($(jq).combo("getValues").join(",") == values.join(",")) {
                    return;
                }
                $(jq).combo("setValues", values);
                if (!remainText) {
                    $(jq).combo("setText", ss.join(options.separator));
                }
            };

            //查询
            function _query(jq, q) {
                var options = $.data(jq, "combogrid").options;
                var grid = $.data(jq, "combogrid").grid;
                $.data(jq, "combogrid").remainText = true;
                if (options.multiple && !q) {
                    _setValues(jq, [], true);
                } else {
                    _setValues(jq, [q], true);
                }
                if (options.mode == "remote") {
                    grid.datagrid("clearSelections");
                    grid.datagrid("load", $.extend({}, options.queryParams, {q: q}));
                } else {
                    if (!q) {
                        return;
                    }
                    var rows = grid.datagrid("getRows");
                    for (var i = 0; i < rows.length; i++) {
                        if (options.filter.call(jq, q, rows[i])) {
                            grid.datagrid("clearSelections");
                            grid.datagrid("selectRow", i);
                            return;
                        }
                    }
                }
            };
            //解析器
            $.fn.combogrid.parseOptions = function (target) {
                var t = $(target);
                return $.extend({}, $.fn.combo.parseOptions(target),
                    $.fn.datagrid.parseOptions(target),
                    $.parser.parseOptions(target, ["idField", "textField", "mode"]));
            };
            $.fn.combogrid.defaults = $.extend({}, $.fn.combo.defaults,
                $.fn.datagrid.defaults, {
                    loadMsg: null,//在数据表格加载远程数据的时候显示消息
                    idField: null,//ID字段名称
                    textField: null,//ID字段名称
                    //定义在文本改变的时候如何读取数据网格数据。设置为'remote'，
                    //数据表格将从远程服务器加载数据。当设置为'remote'模式的时候，用户输入将会发送到名为'q'的http请求参数，向服务器检索新的数据。
                    mode: "local",

                    keyHandler: {
                        up: function () {
                            selectRow(this, -1);
                        },
                        down: function () {
                            selectRow(this, 1);
                        },
                        enter: function () {
                            selectRow(this, 0);
                            $(this).combo("hidePanel");
                        },
                        query: function (q) {
                            _query(this, q);
                        }
                    },
                    //定义在'mode'设置为'local'的时候如何选择本地数据，返回true时则选择该行
                    filter: function (q, row) {
                        var options = $(this).combogrid("options");
                        return row[options.textField].indexOf(q) == 0;
                    }
                });
        })(jQuery);

        function setContentc() {
            var cc = $("input[name='cc']").val();
            if (isEmpty(cc)) {
                //$.messager.alert('错误', "监测断面没有输入");
                layer.msg("监测断面没有输入!");
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

        $(document).ready(function () {
            //$("input[name='ym']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
            $("input[name='cc']").val("${entity.tslRLProjectWaterFunction.id }");
        });
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password"
             layout="table" beforeSubmit="setContentc"
             action="slRiverLakeController.do?saveEntityWQ">
    <input id="id" name="id" type="hidden" value="${entity.id }"/>
    <table style="width: 600px;" cellpadding="0" cellspacing="1"
           class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">*监测断面: </label>
            </td>
            <td class="value">
                <input id="cc" name="cc" class="easyui-combogrid" style="width: 250px"
                       data-options="
                           panelWidth: 500,
                           idField: 'id',
                           textField: 'monitoringSection',
				           url: 'slRiverLakeController.do?datagridWF&id=${entity.id }&field=id,code,name,extField1,extField2,extField3,primaryName,secondaryName,useWaterType,monitoringSection,',
				           columns: [[
                               {field:'code',title:'代码',width:80},
                               {field:'name',title:'名称',width:80},
                               {field:'extField3',title:'所属河流',width:80},
                               {field:'primaryName',title:'一级',width:80},
                               {field:'secondaryName',title:'二级',width:80},
                               {field:'extField1',title:'用水类型',width:120},
                               {field:'extField2',title:'水质目标',width:80},
                               {field:'monitoringSection',title:'断面',width:80},
				           ]],
				           fitColumns: true
				        ">
            </td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label"> *年月:
            </label></td>
            <td class="value"><input id="ym" name="ym" type="text"
                                     maxlength="32" class="inputxt" datatype="*" value="${entity.ym }"
                                     class="Wdate" onClick="WdatePicker({dateFmt:'yyyyMM'})"
                                     style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent; width: 120px;"/>
                <span class="Validform_checktip"></span> <label
                        class="Validform_label" style="display: none;">划描述</label></td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label"> 水质类别:
            </label></td>
            <td class="value"><t:dictSelect id="waterQuality"
                                            field="waterQuality" typeGroupCode="waterQuality" hasLabel="false"
                                            defaultVal="${entity.waterQuality==null?'10':(entity.waterQuality)}"></t:dictSelect>
                <span class="Validform_checktip"></span></td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label"> 达标评价:
            </label></td>
            <td class="value"><t:dictSelect id="waterEvaluate"
                                            field="waterEvaluate" typeGroupCode="waterEvaluate"
                                            hasLabel="false"
                                            defaultVal="${entity.waterEvaluate==null?'10':(entity.waterEvaluate)}"></t:dictSelect>
                <span class="Validform_checktip"></span></td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label">
                主要超标项目及超标倍数: </label></td>
            <td class="value"><input id="projectMultiples"
                                     name="projectMultiples" type="text" maxlength="32"
                                     style="width: 150px" class="inputxt"
                                     value="${entity.projectMultiples }"/> <span
                    class="Validform_checktip">范围1~20位字符</span> <label
                    class="Validform_label" style="display: none;">主要超标项目及超标倍数</label>
            </td>
        </tr>


    </table>
</t:formvalid>
</body>