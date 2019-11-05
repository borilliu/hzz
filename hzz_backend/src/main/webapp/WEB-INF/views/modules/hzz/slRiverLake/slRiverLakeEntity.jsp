<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <t:base type="jquery,easyui,tools"></t:base>
    <script type="text/javascript">
        $(function () {
            $('#entityTree').combotree({
                url: 'slRiverLakeController.do?combotree&param_river_lake_type=10_11_',
                panelHeight: 200,
                width: 157,
                onClick: function (node) {
                    $("#parentId").val(node.id);
                }
            });

            if ($('#id').val()) {
                $('#categoryTree').combotree('disable');
            }

            $('#dd').combotree({
                url: 'slDivisionController.do?setPFunction&selfId=${depart.id}',
                width: 155,
                onSelect: function (node) {

                    //changeOrgType(node.id);

                }
            });


        });

        function openMapDetail(paramType) {
            $.dialog.setting.zIndex = getzIndex();

            $.dialog({
                content: 'url:slRiverLakeController.do?mapDetail&paramType=' + paramType,
                zIndex: getzIndex(),
                title: '地图资料查询',
                lock: true,
                width: '600px',
                height: '600px',
                opacity: 0.4,
                button: [{
                    name: '<t:mutiLang langKey="common.confirm"/>',
                    callback: callbackDepartmentSelect1,
                    focus: true
                },
                    {
                        name: '<t:mutiLang langKey="common.cancel"/>',
                        callback: function () {
                        }
                    }]
            }).zindex();
        }

        function callbackDepartmentSelect1() {

            var iframe = this.iframe.contentWindow;
            //var paramtype = iframe.$.fn.paramType.val();
            var paramType = iframe.$.find("#paramType")[0].value
            if (paramType == "10") {
                var str = iframe.$.find("#paramTypeValue")[0].value
                if (str.length > 0) {
                    var json1 = JSON.parse(str);
                    //$.messager.alert('错误', paramtype);
                    $("#mapCenterLngLat").val("{\"lng\":" + json1.lng + ",\"lat\":" + json1.lat + "}");
                    $("#mapZoom").val(json1.zoom);
                }
            }
            if (paramType == "11") {
                var str = iframe.$.find("#paramTypeValue")[0].value
                if (str.length > 0) {
                    str = str.substr(0, str.length - 1);
                    $("#riverRangeData").val("[" + str + "]");
                }
            }
        }

        function openhzSelect() {
            $.dialog.setting.zIndex = getzIndex();
            var hzids = $("#hzids").val();

            $.dialog({
                content: 'url:slRiverLakeController.do?riverChargeSelect&ifcheckdept=N&orgIds=' + hzids,
                zIndex: getzIndex(),
                title: '选择河长',
                lock: true,
                width: '400px',
                height: '350px',
                opacity: 0.4,
                button: [{
                    name: '<t:mutiLang langKey="common.confirm"/>',
                    callback: callbackDepartmentSelect,
                    focus: true
                },
                    {
                        name: '<t:mutiLang langKey="common.cancel"/>',
                        callback: function () {
                        }
                    }]
            }).zindex();

        }

        function callbackDepartmentSelect() {
            var iframe = this.iframe.contentWindow;
            var treeObj = iframe.$.fn.zTree.getZTreeObj("departSelect");
            var nodes = treeObj.getCheckedNodes(true);
            if (nodes.length > 0) {
                var ids = '', names = '';
                for (i = 0; i < nodes.length; i++) {
                    var node = nodes[i];
                    ids += node.id + ',';
                    names += node.name + ',';
                }
                $('#hzname').val(names);
                $('#hzname').blur();
                $('#hzids').val(ids);
            }
        }

        function callbackClean() {
            $('#hzname').val('');
            $('#hzids').val('');
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
                    var arrayFileObj = jQuery.parseJSON(data.jsonStr).obj;

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
                        if (location.href.indexOf("load=detail") != -1) {
                            $(":input").attr("disabled", "true");
                            $(".jeecgDetail").css("display", "none");
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
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="slRiverLakeController.do?saveEntity" callback="jeecgFormFileCallBack@Override">
    <input id="paramType" name="paramType" type="hidden" value="${paramType }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <c:if test="${empty entity.id}">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <c:if test="${paramType == 'a'}">
                            *河流编码:
                        </c:if>
                        <c:if test="${paramType == 'b'}">
                            *湖泊编码:
                        </c:if>
                        <c:if test="${paramType == 'c'}">
                            *河段编码:
                        </c:if>
                        <c:if test="${paramType == 'd'}">
                            *水库编码:
                        </c:if>
                        <c:if test="${paramType == 'a1'}">
                            *取水口编码:
                        </c:if>
                        <c:if test="${paramType == 'b1'}">
                            *水功能区编码:
                        </c:if>
                        <c:if test="${paramType == 'c1'}">
                            *测站编码:
                        </c:if>
                        <c:if test="${paramType == 'd1'}">
                            *排污口编码:
                        </c:if>
                    </label>
                </td>
                <td class="value">
                    <input class="inputxt" id="code" name="code" datatype="*" value="${entity.code}"
                            <c:if test="${empty entity.id}">
                                ajaxurl="slRiverLakeController/checkRiverLakeCode.do?type=${paramType}"
                                sucmsg="通知编号验证通过!" nullmsg="请输入通知编号！"
                                errormsg="已存在此通知编号！"
                            </c:if>
                    />
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
        </c:if>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <c:if test="${paramType == 'a'}">
                        *河流名称:
                    </c:if>
                    <c:if test="${paramType == 'b'}">
                        *湖泊名称:
                    </c:if>
                    <c:if test="${paramType == 'c'}">
                        *河段名称:
                    </c:if>
                    <c:if test="${paramType == 'd'}">
                        *水库名称:
                    </c:if>
                    <c:if test="${paramType == 'a1'}">
                        *取水口名称:
                    </c:if>
                    <c:if test="${paramType == 'b1'}">
                        *水功能区名称:
                    </c:if>
                    <c:if test="${paramType == 'c1'}">
                        *测站名称:
                    </c:if>
                    <c:if test="${paramType == 'd1'}">
                        *排污口名称:
                    </c:if>
                </label>
            </td>
            <td class="value">
                <input class="inputxt" id="name" name="name" datatype="*" value="${entity.name}">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> 行政地区:</label>
            </td>
            <td class="value">
                <input id="dd" name="TSLDivision.code" value="${entity.TSLDivision.code}">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <c:if test="${paramType == 'a'}">
                        上级河流:
                    </c:if>
                    <c:if test="${paramType == 'b'||paramType == 'd'||paramType == 'a1'}">
                        上级河湖:
                    </c:if>
                    <c:if test="${paramType == 'c'}">
                        所属河流:
                    </c:if>
                    <c:if test="${paramType == 'b1'}">
                        流域名称:
                    </c:if>
                    <c:if test="${paramType == 'c1'}">
                        监测河湖:
                    </c:if>
                    <c:if test="${paramType == 'd1'}">
                        所属河湖:
                    </c:if>
                </label>
            </td>
            <td class="value">
                <input id="entityTree" value="${entity.parent.name}">
                <input id="parentId" name="parent.code" style="display: none;" value="${entity.parent.code}">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label">
                <c:if test="${paramType == 'a'}">
                    *河长姓名:
                </c:if>
                <c:if test="${paramType == 'b'}">
                    *湖长姓名:
                </c:if>
                <c:if test="${paramType == 'c'}">
                    *河段长姓名:
                </c:if>
                <c:if test="${paramType == 'd'}">
                    *水库长姓名:
                </c:if>
                <c:if test="${paramType == 'a1'}">
                    *取水口负责人:
                </c:if>
                <c:if test="${paramType == 'b1'}">
                    *水功能区负责人:
                </c:if>
                <c:if test="${paramType == 'c1'}">
                    *测站负责人:
                </c:if>
                <c:if test="${paramType == 'd1'}">
                    *排污口负责人:
                </c:if>
            </label>
            </td>
            <td class="value">
                <input id="hzname" name="hzname" type="text" readonly="readonly" class="inputxt" datatype="*"
                       value="${hzname}"/>
                <input id="hzids" name="hzids" type="hidden" value="${hzids}"/>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="hzSearch"
                   onclick="openhzSelect()">选择</a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo" id="hzRedo"
                   onclick="callbackClean()">清空</a>
                <span class="Validform_checktip">可多选</span>
            </td>
        </tr>
        <c:if test="${paramType == 'a'||paramType == 'b'||paramType == 'c'||paramType == 'd'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">*流域面积:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="waterArea" name="waterArea" datatype="d" errormsg="数字非法"
                           value="${entity.waterArea}"> 平方公里
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
        </c:if>
        <c:if test="${paramType == 'a'||paramType == 'c'}">
            <td align="right">
                <label class="Validform_label">
                    <c:if test="${paramType == 'a'}">
                        河流类型:
                    </c:if>
                    <c:if test="${paramType == 'c'}">
                        河段类型:
                    </c:if>
                </label>
            </td>
            <td class="value">
                <t:dictSelect id="riverType" field="riverType" typeGroupCode="riverType" hasLabel="false"
                              defaultVal="${entity.riverType==null?'10':(entity.riverType)}"></t:dictSelect>
                <span class="Validform_checktip"></span></td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <c:if test="${paramType == 'a'}">
                            *河流长度:
                        </c:if>
                        <c:if test="${paramType == 'c'}">
                            *河段长度:
                        </c:if>
                    </label>
                </td>
                <td class="value">
                    <input class="inputxt" id="riverLength" name="riverLength" datatype="d" errormsg="数字非法"
                           value="${entity.riverLength}"> 公里
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">河源所在位置: </label>
                </td>
                <td class="value">
                    <input class="inputxt" id="startPositionName" name="startPositionName" ignore="ignore"
                           value="${entity.startPositionName}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">河口所在位置:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="endPositiomName" name="endPositiomName" ignore="ignore"
                           value="${entity.endPositiomName}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">河流流经地:</label>
                </td>
                <td class="value">
                    <textarea rows="4" cols="15" class="inputxtarea" id="riverDesc" name="riverDesc" ignore="ignore"
                              style="margin: 0px; height: 50px; width: 260px;">${entity.riverDesc}</textarea>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
        </c:if>
        <c:if test="${paramType == 'b'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">咸淡属性:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="saltyAttr" field="saltyAttr" typeGroupCode="saltyAttr" hasLabel="false"
                                  defaultVal="${entity.saltyAttr==null?'10':(entity.saltyAttr)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*平均水深:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="meanDepth" name="meanDepth" datatype="d" errormsg="数字非法"
                           value="${entity.meanDepth}"> 米
                    <span class="Validform_checktip"></span></td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*湖泊容积:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="lakeVolume" name="lakeVolume" datatype="d" errormsg="数字非法"
                           value="${entity.lakeVolume}">m³
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*最大水深:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="maxWaterDepth" name="maxWaterDepth" datatype="d" errormsg="数字非法"
                           value="${entity.maxWaterDepth}">米
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
        </c:if>
        <c:if test="${paramType == 'd'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">水库类型:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="reservoirType" field="reservoirType" typeGroupCode="reservoirType"
                                  hasLabel="false"
                                  defaultVal="${entity.reservoirType==null?'10':(entity.reservoirType)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">工程等别:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="engineeringGrade" field="engineeringGrade" typeGroupCode="engineeringGrade"
                                  hasLabel="false"
                                  defaultVal="${entity.engineeringGrade==null?'10':(entity.engineeringGrade)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">工程规模:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="engineeringScale" field="engineeringScale" typeGroupCode="engineeringScale"
                                  hasLabel="false"
                                  defaultVal="${entity.engineeringScale==null?'10':(entity.engineeringScale)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">水库等级:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="reservoirGrade" name="reservoirGrade" ignore="ignore"
                           value="${entity.reservoirGrade}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*防洪高水位:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="highFloodLevel" name="highFloodLevel" datatype="d" errormsg="数字非法"
                           value="${entity.highFloodLevel}"> 米
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*正常蓄水位:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="normalWaterLevel" name="normalWaterLevel" datatype="d" errormsg="数字非法"
                           value="${entity.normalWaterLevel}"> 米
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*主汛期防洪限制水位:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="floodPeriodLevel" name="floodPeriodLevel" datatype="d" errormsg="数字非法"
                           value="${entity.floodPeriodLevel}"> 米
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*总库容:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="totalCapacity" name="totalCapacity" datatype="d" errormsg="数字非法"
                           value="${entity.totalCapacity}"> m³
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*调洪库容:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="floodCapacity" name="floodCapacity" datatype="d" errormsg="数字非法"
                           value="${entity.floodCapacity}"> m³
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*防洪库容:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="floodStorage" name="floodStorage" datatype="d" errormsg="数字非法"
                           value="${entity.floodStorage}"> m³
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
        </c:if>
        <c:if test="${paramType == 'a1'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">取水口位置:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="address" name="address" ignore="ignore" value="${entity.address}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">取水方式:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="takeType" field="takeType" typeGroupCode="takeType" hasLabel="false"
                                  defaultVal="${entity.takeType==null?'10':(entity.takeType)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">水闸名称:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="gateName" name="gateName" ignore="ignore"
                           value="${entity.gateName}"><span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">引调水工程名称:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="diversionProjectName" name="diversionProjectName" ignore="ignore"
                           value="${entity.diversionProjectName}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">地表水水源地名称:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="waterSourceSName" name="waterSourceSName" ignore="ignore"
                           value="${entity.waterSourceSName}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
        </c:if>

        <c:if test="${paramType == 'b1'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">一级水功能区名称:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="primaryName" name="primaryName" ignore="ignore"
                           value="${entity.primaryName}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">二级水功能区名称:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="secondaryName" name="secondaryName" ignore="ignore"
                           value="${entity.secondaryName}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">用水类型:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="useWaterType" field="useWaterType" typeGroupCode="useWaterType" hasLabel="false"
                                  defaultVal="${entity.useWaterType==null?'10':(entity.useWaterType)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">*水功能区长度:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="waterFunctionLength" name="waterFunctionLength" datatype="d"
                           errormsg="数字非法" value="${entity.waterFunctionLength}"> 千米
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">水功能区水质目标:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="waterQuality" field="waterQuality" typeGroupCode="waterQuality" hasLabel="false"
                                  defaultVal="${entity.waterQuality==null?'10':(entity.waterQuality)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">水功能区起始断面:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="startSection" name="startSection" ignore="ignore"
                           value="${entity.startSection}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">水功能区终止断面:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="endSection" name="endSection" ignore="ignore"
                           value="${entity.endSection}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">监测断面:</label>
                </td>
                <td class="value"><input class="inputxt" id="monitoringSection" name="monitoringSection" ignore="ignore"
                                         value="${entity.monitoringSection}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
        </c:if>

        <c:if test="${paramType == 'c1'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">测站所在地:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="location" name="location" ignore="ignore" value="${entity.location}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">测站年月:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="stationYear" name="stationYear" ignore="ignore"
                           value="${entity.stationYear}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">测站类别:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="stationType" field="stationType" typeGroupCode="stationType" hasLabel="false"
                                  defaultVal="${entity.stationType==null?'10':(entity.stationType)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
        </c:if>
        <c:if test="${paramType == 'd1'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">排水口位置:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="location" name="location" ignore="ignore" value="${entity.location}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">排污口类型:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="sewageOutType" field="sewageOutType" typeGroupCode="sewageOutType"
                                  hasLabel="false"
                                  defaultVal="${entity.sewageOutType==null?'10':(entity.sewageOutType)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">入河方式:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="riverEntryMode" field="riverEntryMode" typeGroupCode="riverEntryMode"
                                  hasLabel="false"
                                  defaultVal="${entity.riverEntryMode==null?'10':(entity.riverEntryMode)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">排放方式:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="emissionMode" field="emissionMode" typeGroupCode="emissionMode" hasLabel="false"
                                  defaultVal="${entity.emissionMode==null?'10':(entity.emissionMode)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">排入一级水功能区域:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="intoPrimary" name="intoPrimary" ignore="ignore"
                           value="${entity.intoPrimary}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">排入二级水功能区域:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="intoSecondary" name="intoSecondary" ignore="ignore"
                           value="${entity.intoSecondary}">
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">污水主要来源:</label>
                </td>
                <td class="value">
                    <t:dictSelect id="sourcesSewageType" field="sourcesSewageType" typeGroupCode="sourcesSewageType"
                                  hasLabel="false"
                                  defaultVal="${entity.sourcesSewageType==null?'10':(entity.sourcesSewageType)}"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label"> 排污量:</label>
                </td>
                <td class="value">
                    <input class="inputxt" id="pollutantDischarge" name="pollutantDischarge" datatype="d"
                           errormsg="数字非法" value="${entity.pollutantDischarge}"> 万吨
                    <span class="Validform_checktip"></span>
                </td>
            </tr>
        </c:if>

        <tr>
            <td align="right">
                <label class="Validform_label">地图中心经纬度:</label>
            </td>
            <td class="value">
                <input class="inputxt" id="mapCenterLngLat" name="mapCenterLngLat" ignore="ignore"
                       value="${fn:escapeXml(entity.mapCenterLngLat)}">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="hzSearch1"
                   onclick="openMapDetail(10)">地图资料查询</a>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">地图缩放级:</label>
            </td>
            <td class="value">
                <input class="inputxt" id="mapZoom" name="mapZoom" datatype="d" errormsg="数字非法" ignore="ignore"
                       value="${entity.mapZoom}">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">地图区域经纬度:</label>
            </td>
            <td class="value">
                <textarea rows="4" cols="15" class="inputxtarea" id="riverRangeData" name="riverRangeData"
                          ignore="ignore"
                          style="margin: 0px; height: 150px; width: 260px;">${entity.riverRangeData} </textarea>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="hzSearch1"
                   onclick="openMapDetail(11)">地图资料查询</a>
                <span class="Validform_checktip"></span></td>
        </tr>
            <%-- <tr>
                <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.icon" />:</label></td>
                <td class="value">
                    <select name="icon.id">
                        <c:forEach items="${iconlist}" var="icon">
                            <option value="${icon.id}" <c:if test="${icon.id==categoryPage.icon.id}">selected="selected"</c:if> >
                                <t:mutiLang langKey="${icon.iconName}" />
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr> --%>
        <tr>
            <td align="right">
                <label class="Validform_label" style="white-space: nowrap;"> 附件: </label>
            </td>
            <td class="value">
                <div class="form jeecgDetail">
                    <t:upload name="testFile1" id="testFile1" queueID="filediv_testFile1"
                              uploader="cgUploadController.do?saveFiles" extend=""
                              formData="cgFormId,cgFormName,cgFormField_testFile1" outhtml="true"></t:upload>
                </div>
                <div class="form" id="filediv_testFile1"></div>
                <table id="photo_fileTable"></table>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">附件</label>
            </td>
        </tr>
        <input id="cgFormId" name="id" type="hidden" value="${entity.id }">
        <input id="cgFormName" name="cgFormName" type="hidden" value="t_sl_base_river_lake">
        <input id="cgFormField_testFile1" type="hidden" value="photo">
    </table>
</t:formvalid>
</body>