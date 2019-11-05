<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools"></t:base>
<%--<div class="easyui-layout" fit="true">
    <div region="center" style="height:100px; margin-top: 10px" border="false">
            <div style="text-align:center;">
                <input type="radio" name="river" checked value="1">同级河长
                <input type="radio" name="river" value="2">下级河长
            </div>
            <hr>
    &lt;%&ndash;                <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="functionListPanel">&ndash;%&gt;
            &lt;%&ndash; <input type="hidden" name="roleId" value="${roleId}" id="rid">
             <input type="hidden" id="typeMode" value="1"/>
             <a id="selecrAllBtn" onclick="selecrAll();"><t:mutiLang langKey="select.all"/></a>
             <a id="resetBtn" onclick="reset();"><t:mutiLang langKey="common.reset"/></a>
             <a id="changeBtn" onclick="changeMode();"><t:mutiLang langKey="common.typemode"/></a>&ndash;%&gt;
            <ul id="functionid" class="ztree"></ul>
    &lt;%&ndash;                </div>&ndash;%&gt;
            <div style="margin-top: 50px">
                <label>审核意见</label>
                <textarea name="resation" placeholder="请输入描述" style="width:100%;height:120px;font-size:15px;" rows="2" cols="20"></textarea>
            </div>
    </div>
</div>--%>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="/riverLakePatrolController/task/dispatch/${taskId}.do" callback="jeecgFormFileCallBack@Override">
    <table style="width: 500px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
        <td align="left">
            <label class="Validform_label">
                选择交办对象:
            </label>
        </td>
        <td class="value">
            <input type="radio" name="locallevel" checked value="true">同级河长
            <input type="radio" name="locallevel" value="false">下级河长
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">问题编号</label>
        </td>
    </tr>
    <tr>
        <td align="left">
            <label class="Validform_label">
                选择交办对象:
            </label>
        </td>
        <td class="value">
            <input type="hidden" id="rivers" name="rivers">
            <ul id="functionid" class="ztree"></ul>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">问题编号</label>
        </td>
    </tr>
    <tr>
        <td align="left">
            <label class="Validform_label">
                审核意见:
            </label>
        </td>
        <td class="value">
            <textarea name="reason" placeholder="请输入描述" style="width:100%;height:120px;font-size:15px;" rows="2" cols="20"></textarea>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">审核意见</label>
        </td>
    </tr>
</t:formvalid>
<link rel="stylesheet" type="text/css" href="/plug-in/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript">
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        async: {
            enable: true,
            url: "/riverLakeChiefController/sameLevelRiverTree.do",
            dataFilter: filter
        },
        callback: {
            onCheck: function(event, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("functionid");
                var node = zTree.getCheckedNodes(true);
                //加入实际被选中的节点
                var checkedNodes = '';
                for (var i = 0; i < node.length; i++) {
                    debugger
                    if (node[i].isParent) {
                        continue;
                    }
                    checkedNodes += node[i].id + ',';
                }
                checkedNodes = checkedNodes.substring(0, checkedNodes.length - 1);
                $('#rivers').val(checkedNodes);
            },
            onAsyncSuccess: function(event, treeId, treeNode, msg) {
                expandAll();
            }
        }
    };

    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i = 0, l = childNodes.length; i < l; i++) {
            childNodes[i].name = childNodes[i].text;
            //childNodes[i].open = (childNodes[i].state === "open");//异步加载，该项无效
            if (childNodes[i].children != null) {
                childNodes[i].nodes = childNodes[i].children;
                filter(null, childNodes[i], childNodes[i].nodes);//递归设置子节点
            }
        }
        return childNodes;
    }

    $(function () {

        $.fn.zTree.init($("#functionid"), setting);

        $("#functionListPanel").panel({
            title: '<t:mutiLang langKey="menu.list"/>',
            tools: [{
                iconCls: 'icon-save',
                handler: function () {
                    mysubmit();
                }
            }]
        });

        $(":radio").click(function(){
            debugger
            if ("true" === $(this).val()) {
                var zTree = $.fn.zTree.getZTreeObj("functionid");
                zTree.setting.async.url = '/riverLakeChiefController/sameLevelRiverTree.do';
                zTree.reAsyncChildNodes(null, "refresh");
            }
            if ("false" === $(this).val()) {
                var zTree = $.fn.zTree.getZTreeObj("functionid");
                zTree.setting.async.url = '/riverLakeChiefController/lowerLevelRiverTree.do';
                zTree.reAsyncChildNodes(null, "refresh");
            }
        });
    });

    function mysubmit() {
        var zTree = $.fn.zTree.getZTreeObj("functionid");
        var node = zTree.getCheckedNodes(true);
        //加入实际被选中的节点
        var checkedNodes = '';
        for (var i = 0; i < node.length; i++) {
            checkedNodes += node[i].id + ',';
        }
        checkedNodes = checkedNodes.substring(0, checkedNodes.length - 1);
        doSubmit("roleController.do?updateAuthority&rolefunctions=" + s + "&roleId=" + roleId);
    }

    function expandAll() {
        var zTree = $.fn.zTree.getZTreeObj("functionid");
        zTree.expandAll(true);
    }

    function selecrAll() {
        var zTree = $.fn.zTree.getZTreeObj("functionid");
        zTree.checkAllNodes(true);
    }

    function reset() {
        $.fn.zTree.init($("#functionid"), setting);
    }

    function changeMode() {
        var zTree = $.fn.zTree.getZTreeObj("functionid");
        var typeMode = $("#typeMode").val();
        var type = typeMode == 1 ? {"Y": "", "N": ""} : {"Y": "ps", "N": "ps"};
        zTree.setting.check.chkboxType = type;
        $("#typeMode").val(typeMode % 2 + 1);
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
        /*if (!neibuClickFlag) {
            var win = frameElement.api.opener;
            win.reloadTable();
        }*/
    }

    $('#selecrAllBtn').linkbutton({});
    $('#resetBtn').linkbutton({});
    $('#changeBtn').linkbutton({});


</script>



