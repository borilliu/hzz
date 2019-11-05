<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>问题信息表</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <t:base type="uploadify"></t:base>
    <link rel="stylesheet" href="plug-in/select2/css/select2.min.css">
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="problemController.do?doUpdate"
             callback="jeecgFormFileCallBack@Override">
    <input id="id" name="id" type="hidden" value="${problemPage.id }"/>
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    问题编号:
                </label>
            </td>
            <td class="value">
                <input id="code" name="code" type="text" maxlength="100" style="width: 150px" class="inputxt"
                       datatype="*" ignore="checked" value='${problemPage.code}'/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">问题编号</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    问题类型:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="type" type="select" datatype="*" typeGroupCode="problemType"
                              defaultVal="${problemPage.type}"
                              hasLabel="false" title="问题类型"></t:dictSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">问题类型</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    地区编号:
                </label>
            </td>
            <td class="value">
                <t:comboTree
                        url="slDivisionController.do?setPFunction"
                        name="divisionCode" id="divisionCode" value="${problemPage.divisionCode}"
                        width="200"></t:comboTree>

                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">地区编号</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    相关河长:
                </label>
            </td>
            <td class="value">
                <input type="text" id="riverChief" name="riverChief" value="${problemPage.riverChief}" class="ac_input">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">相关河长</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    相关河段:
                </label>
            </td>
            <td class="value">
                <input id="reach" name="reach" type="text" maxlength="100" style="width: 150px" class="inputxt"
                       ignore="ignore" value='${problemPage.reach}'/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">相关河段</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    地址:
                </label>
            </td>
            <td class="value">
                <input id="address" name="address" type="text" maxlength="100" style="width: 150px" class="inputxt"
                       ignore="ignore" value='${problemPage.address}'/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">地址</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    上报时间:
                </label>
            </td>
            <td class="value">
                <input id="reportTime" name="reportTime" type="text" style="width: 150px" class="Wdate"
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*" ignore="checked"
                       value='<fmt:formatDate value='${problemPage.reportTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>'/>

                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">上报时间</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    上报事件:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="event" type="select" typeGroupCode="questType" defaultVal="${problemPage.event}"
                              hasLabel="false" title="上报事件"></t:dictSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">上报事件</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    问题上报图片:
                </label>
            </td>
            <td class="value">
                <table id="photo_fileTable"></table>
                <div class="form jeecgDetail">
                    <t:upload name="photo" id="photo" queueID="filediv_photo" outhtml="false"
                              uploader="cgUploadController.do?saveFiles" extend="pic" buttonText='添加图片'
                              onUploadStart="photoOnUploadStart"> </t:upload>
                    <div class="form" id="filediv_photo"></div>
                    <script type="text/javascript">
                        function photoOnUploadStart(file) {
                            var cgFormId = $("input[name='id']").val();
                            $('#photo').uploadify("settings", "formData", {
                                'cgFormId': cgFormId,
                                'cgFormName': 't_sl_problem',
                                'cgFormField': 'PHOTO'
                            });
                        }
                    </script>
                </div>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">问题上报图片</label>
            </td>
        </tr>

        <tr>
            <td align="right">
                <label class="Validform_label">
                    问题描述:
                </label>
            </td>
            <td class="value">
                <textarea id="remarks" style="height:auto;width:95%;" class="inputxt" rows="6" name="remarks"
                          ignore="ignore">${problemPage.remarks}</textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">问题描述</label>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>
<script type="text/javascript" src="plug-in/select2/js/select2.full.min.js"></script>
<script type="text/javascript">
    //加载 已存在的 文件
    $(function () {
        var cgFormId = $("input[name='id']").val();
        $.ajax({
            type: "post",
            url: "problemController.do?getFiles&id=" + cgFormId,
            success: function (data) {
                var arrayFileObj = jQuery.parseJSON(data).obj;

                $.each(arrayFileObj, function (n, file) {
                    var fieldName = file.field.toLowerCase();
                    var table = $("#" + fieldName + "_fileTable");
                    var tr = $("<tr style=\"height:34px;\"></tr>");
                    var title = file.title;
                    if (title.length > 15) {
                        title = title.substring(0, 12) + "...";
                    }
                    var td_title = $("<td>" + title + "</td>");
                    var td_download = $("<td><a style=\"margin-left:10px;\" href=\"commonController.do?viewFile&fileid=" + file.fileKey + "&subclassname=com.saili.hzz.web.cgform.entity.upload.CgUploadEntity\" title=\"下载\">下载</a></td>")
                    var td_view = $("<td><a style=\"margin-left:10px;\" href=\"javascript:void(0);\" onclick=\"openwindow('预览','commonController.do?openViewFile&fileid=" + file.fileKey + "&subclassname=com.saili.hzz.web.cgform.entity.upload.CgUploadEntity','fList',700,500)\">预览</a></td>");
                    tr.appendTo(table);
                    td_title.appendTo(tr);
                    td_download.appendTo(tr);
                    td_view.appendTo(tr);
                    if (location.href.indexOf("load=detail") == -1) {
                        var td_del = $("<td><a style=\"margin-left:10px;\" href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=" + file.fileKey + "',this)\">删除</a></td>");
                        td_del.appendTo(tr);
                    }
                });
            }
        });

        $('#divisionCode').combotree({
            onSelect: function (node) {
                var select2Data = new Array();
                $.ajax({
                    url: 'userController.do?datagridByDivisionCode&divisionCode=' + node.id,
                    type: 'GET',
                    dataType: 'JSON',
                    delay: 250,
                    cache: true,
                    success: function (data) {
                        for (var i = 0; i < data.length; i++) {
                            var select2Obj = {};
                            select2Obj.id = data[i].id;
                            select2Obj.text = data[i].userName;
                            select2Data.push(select2Obj);
                        }
                        $("#riverChief").select2({
                            data: select2Data,
                            placeholder: '请选择河长',//默认文字提示
                            language: "zh-CN",//汉化
                            allowClear: true//允许清空
                        });
                    }
                });
            }
        });
    });

    /**
     * 删除图片数据资源
     */
    function del(url, obj) {
        var content = "请问是否要删除该资源";
        var navigatorName = "Microsoft Internet Explorer";
        if (navigator.appName == navigatorName) {
            $.dialog.confirm(content, function () {
                submit(url, obj);
            }, function () {
            });
        } else {
            layer.open({
                title: "提示",
                content: content,
                icon: 7,
                yes: function (index) {
                    submit(url, obj);
                },
                btn: ['确定', '取消'],
                btn2: function (index) {
                    layer.close(index);
                }
            });
        }
    }

    function submit(url, obj) {
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            url: url,// 请求的action路径
            error: function () {// 请求失败处理函数
            },
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    obj.parentNode.parentNode.parentNode.deleteRow(obj.parentNode.parentNode);
                } else {
                    tip(d.msg);
                }
            }
        });
    }

    function jeecgFormFileCallBack(data) {
        if (data.success == true) {
            uploadFile(data);
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
                    $.messager.alert('错误', data.responseText + '');
                }
            }
            return false;
        }
        if (!neibuClickFlag) {
            var win = frameElement.api.opener;
            win.reloadTable();
        }
    }

    function upload() {
        $('#photo').uploadify('upload', '*');
    }

    var neibuClickFlag = false;

    function neibuClick() {
        neibuClickFlag = true;
        $('#btn_sub').trigger('click');
    }

    function cancel() {
        $('#photo').uploadify('cancel', '*');
    }

    function uploadFile(data) {
        if (!$("input[name='id']").val()) {
            if (data.obj != null && data.obj != 'undefined') {
                $("input[name='id']").val(data.obj.id);
            }
        }
        if ($(".uploadify-queue-item").length > 0) {
            upload();
        } else {
            if (neibuClickFlag) {
                alert(data.msg);
                neibuClickFlag = false;
            } else {
                var win = frameElement.api.opener;
                win.reloadTable();
                win.tip(data.msg);
                frameElement.api.close();
            }
        }
    }
</script>
