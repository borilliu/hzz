<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>common.notice</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" charset="utf-8" src="plug-in/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="plug-in/ueditor/ueditor.all.js"></script>
    <script type="text/javascript">
        //编写自定义JS代码
        function setContent() {
            if (editor.queryCommandState('source'))
                editor.execCommand('source');//切换到编辑模式才提交，否则有bug

            if (editor.hasContents()) {
                editor.sync();
                $("#noticeContent").val(editor.getContent());
            }
        }

        function dataytpeSelect(name) {
            $("#roleName").removeAttr('datatype');
            $("#roleName_span").hide()
            $("#userName").removeAttr('datatype');
            $("#userName_span").hide()
            if (name) {
                $("#" + name).attr('datatype', '*');
                $("#" + name + "_span").show()
            }
        }

    </script>

    <script src="/static/js/modules/demo/multiUpload.js"></script>
    <script type="text/javascript">
        //加载 已存在的 文件
        $(function () {
            var cgFormId = $("input[name='id']").val();

            $.ajax({
                type: "post",
                url: "multiUploadController.do?getFiles&id=" + cgFormId,
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
                        var td_del = $("<td><a style=\"margin-left:10px;\" href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=" + file.fileKey + "',this)\">删除</a></td>");
                        tr.appendTo(table);
                        td_title.appendTo(tr);
                        td_download.appendTo(tr);
                        td_view.appendTo(tr);
                        td_del.appendTo(tr);
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

        var neibuClickFlag = false;

        function neibuClick() {
            neibuClickFlag = true;
            $('#btn_sub').trigger('click');
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
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="noticeController.do?doAdd"
             callback="jeecgFormFileCallBack@Override" tiptype="4" beforeSubmit="setContent()">
    <input id="cgFormId" name="id" type="hidden" value="${tSNoticePage.id}">
    <input id="cgFormName" name="cgFormName" type="hidden" value="t_s_notice">
    <input id="cgFormField_testFile1" type="hidden" value="attachment_doc_file">
    <input id="cgFormField_testFile2" type="hidden" value="title_picture_file">
    <table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
        <c:if test="${empty tSNoticePage.id}">
            <tr>
                <td align="right">
                    <label class="Validform_label"> *编号: </label>
                </td>
                <td class="value">
                        <%-- <input id="noticeCode" name="noticeCode" type="text" style="width: 95%" class="inputxt" datatype="*"
                                value='${tSNoticePage.noticeCode}'>--%>
                    <input type="text" value="${tSNoticePage.noticeCode}" name="noticeCode" datatype="*"
                            <c:if test="${empty tSNoticePage.id}">
                                ajaxurl="noticeController.do?checkNoticeCode"
                                sucmsg="通知编号验证通过!" nullmsg="请输入通知编号！"
                                errormsg="已存在此通知编号！"
                            </c:if>
                    />
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">通知编号</label>
                </td>
            </tr>
        </c:if>
        <tr>
            <td align="right"><label class="Validform_label"> *标题: </label>
            </td>
            <td class="value">
                <input id="noticeTitle" name="noticeTitle" type="text" style="width: 95%" class="inputxt" datatype="s"
                       value='${tSNoticePage.noticeTitle}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">通知标题</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> 发布来源:</label>
            </td>
            <td class="value">
                <input id="noticeSource" name="noticeSource" type="text" style="width: 95%" class="inputxt"
                       value='${tSNoticePage.noticeSource}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">发布来源</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">通知公告类型: </label>
            </td>
            <td class="value">
                <t:dictSelect id="noticeType" field="noticeType" typeGroupCode="noticeType" hasLabel="false"
                              defaultVal="${tSNoticePage.noticeType==null?'0':(tSNoticePage.noticeType)}"></t:dictSelect>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> 发布单位:</label>
            </td>
            <td class="value">
                <input id="noticeUnit" name="noticeUnit" type="text" style="width: 95%" class="inputxt"
                       value='${tSNoticePage.noticeUnit}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">发布单位</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> 内容: </label>
            </td>
            <td class="value">
                <input id="noticeContent" name="noticeContent" type="hidden" value='${tSNoticePage.noticeContent}'>
                <script id="content" type="text/plain" style="width: 95%"
                        value='${tSNoticePage.noticeContent}'></script>
                <script type="text/javascript">
                    var editor = UE.getEditor('content', {
                        toolleipi: true,//是否显示，设计器的 toolbars
                        textarea: 'design_content',
                        wordCount: false,
                        elementPathEnabled: false,
                        initialFrameHeight: 250
                    });
                    editor.ready(function () {
                        editor.setContent($('#noticeContent').val());
                    });
                </script>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">通知公告内容</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label" style="white-space: nowrap;"> 授权级别: </label>
            </td>
            <td class="value">
                <input type="radio" onclick="dataytpeSelect()" id="noticeLevel" name="noticeLevel" value="1"
                       datatype="*"
                       <c:if test="${tSNoticePage.noticeLevel=='1'}">checked="checked"</c:if>
                       <c:if test="${empty tSNoticePage.noticeLevel}">checked="checked"</c:if> />全员
                <br/>
                <input type="radio" onclick="dataytpeSelect('roleName')" name="noticeLevel" value="2"
                       <c:if test="${tSNoticePage.noticeLevel=='2'}">checked="checked"</c:if> />角色授权
                <span id="roleName_span" <c:if test="${tSNoticePage.noticeLevel!='2'}">style="display: none"</c:if>>
						<input name="roleid" name="roleid" type="hidden" value="${rolesid}" id="roleid">
						<input name="roleName" class="inputxt" value="${rolesName }" id="roleName" readonly="readonly"/>
						<t:choose hiddenName="roleid" hiddenid="id" url="userController.do?roles" name="roleList"
                                  icon="icon-search" title="common.role.list" textname="roleName" isclear="true"
                                  isInit="true"></t:choose> &nbsp;&nbsp;
					</span>
                <br/>
                <input type="radio" onclick="dataytpeSelect('userName')" name="noticeLevel" value="3"
                       <c:if test="${tSNoticePage.noticeLevel=='3'}">checked="checked"</c:if> />用户授权
                <span id="userName_span" <c:if test="${tSNoticePage.noticeLevel!='3'}">style="display: none"</c:if>>
						<input name="userid" name="userid" type="hidden" value="${usersid}" id="userid">
						<input name="userName" class="inputxt" value="${usersName }" id="userName" readonly="readonly"/>
						<t:choose hiddenName="userid" hiddenid="id" url="noticeAuthorityUserController.do?selectUser"
                                  name="userList" icon="icon-search" title="common.user.list" textname="userName"
                                  isclear="true" isInit="true"></t:choose>
					</span>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">授权级别</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> 首面显示:</label>
            </td>
            <td class="value">
                <t:dictSelect field="appVisible" type="radio" typeGroupCode="appVisible"
                              defaultVal="${tSNoticePage.appVisible==null?'10':(tSNoticePage.appVisible)}"
                              hasLabel="false" title="是否app首面显示"></t:dictSelect>
                <span class="Validform_checktip"></span></td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label" style="white-space: nowrap;"> 附件: </label>
            </td>
            <td class="value">
                <div class="form jeecgDetail">
                    <t:upload name="testFile1" id="testFile1"
                              queueID="filediv_testFile1"
                              uploader="cgUploadController.do?saveFiles" extend=""
                              formData="cgFormId,cgFormName,cgFormField_testFile1"
                              outhtml="false">
                    </t:upload>
                </div>
                <div class="form" id="filediv_testFile1"></div>
                <table id="attachment_doc_file_fileTable"></table>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">附件</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> 标题图片:</label>
            </td>
            <td class="value">
                <div class="form jeecgDetail">
                    <t:upload name="testFile2" id="testFile2"
                              queueID="filediv_testFile2"
                              uploader="cgUploadController.do?saveFiles" extend=""
                              formData="cgFormId,cgFormName,cgFormField_testFile2"
                              outhtml="true">
                    </t:upload>
                </div>
                <div class="form" id="filediv_testFile2"></div>
                <table id="title_picture_file_fileTable"></table>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">标题图片</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label" style="white-space: nowrap;"> 发布时间: </label>
            </td>
            <td class="value">
                <input type="text" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 150px"
                       id="releaseTime" name="releaseTime" datatype="*"
                       value="<fmt:formatDate value='${tSNoticePage.releaseTime}' type="date" pattern="yyyy-MM-dd"/>">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">发布时间</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> 发布备注:</label>
            </td>
            <td class="value">
                <input id="noticeMemo" name="noticeMemo" type="text" style="width: 95%" class="inputxt"
                       value='${tSNoticePage.noticeMemo}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">发布备注</label>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>