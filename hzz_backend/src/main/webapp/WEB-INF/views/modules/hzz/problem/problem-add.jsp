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
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="problemController.do?doAdd"
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
                       datatype="*" ignore="checked"/>
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
                <t:dictSelect field="type" type="list" datatype="*" typeGroupCode="problemType" defaultVal="${problemPage.type}"
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
<%--                <t:selectZTree id="divisionCode" url="jeecgFormDemoController.do?getTreeData"  windowWidth="400px" windowHeight="30px"></t:selectZTree>--%>
                <t:comboTree
                        url="slDivisionController.do?setPFunction"
                        name="divisionCode" id="divisionCode"
                        width="200"></t:comboTree>
                <span class="Validform_checktip"></span>
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
                <%--<t:dictSelect field="riverChief" type="list" typeGroupCode="" defaultVal="${problemPage.riverChief}"
                              hasLabel="false" title="相关河长"></t:dictSelect>--%>

                <input type="text" id="riverChief" name="riverChief" class="ac_input">
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
                       ignore="ignore"/>
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
                       ignore="ignore"/>
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
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*" ignore="checked"/>
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
					<textarea style="height:auto;width:95%" class="inputxt" rows="6" id="remarks" name="remarks"
                              ignore="ignore"></textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">问题描述</label>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>
<script type="text/javascript" src="plug-in/select2/js/select2.full.min.js"></script>
<script type="text/javascript">
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


    $(function () {

        $('#divisionCode').combotree({
            onSelect : function(node) {
                var select2Data = new Array();
                $.ajax({
                    url:'userController.do?datagridByDivisionCode&divisionCode=' + node.id,
                    // url:'userController.do?datagridByDivisionCode&divisionCode='+$('#divisionCode').val(),
                    type:'GET',
                    dataType:'JSON',
                    delay: 250,
                    cache: true,
                    success: function(data){
                        for(var i = 0; i < data.length; i++){
                            var select2Obj = {};
                            select2Obj.id = data[i].id;
                            select2Obj.text = data[i].userName;
                            select2Data.push(select2Obj);
                        }
                        $("#riverChief").select2({
                            data: select2Data,
                            placeholder:'请选择河长',//默认文字提示
                            language: "zh-CN",//汉化
                            allowClear: true//允许清空
                        });
                    }
                });
            }
        })




    })
</script>
