<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>行政区划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
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

<script type="text/javascript">
	$(function(){
		$('#categoryTree').combotree({
			url : 'slDivisionController.do?divisionTree&selfId=${division.code}',
            width: 155,
            onSelect : function(node) {

            	$("#parentId").val(node.id);

            }
        });

		if('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#categoryTree').combotree('setValue', '${pid}');
        }
	});
	function changeOrgType(parentId) { // 处理组织类型，不显示公司选择项
        
    }	
  		
  		 /**树形列表数据转换**/
  function convertTreeData(rows, textField) {
      for(var i = 0; i < rows.length; i++) {
          var row = rows[i];
          row.text = row[textField];
          if(row.children) {
          	row.state = "open";
              convertTreeData(row.children, textField);
          }
      }
  }
  /**树形列表加入子元素**/
  function joinTreeChildren(arr1, arr2) {
      for(var i = 0; i < arr1.length; i++) {
          var row1 = arr1[i];
          for(var j = 0; j < arr2.length; j++) {
              if(row1.id == arr2[j].id) {
                  var children = arr2[j].children;
                  if(children) {
                      row1.children = children;
                  }
                  
              }
          }
      }
  }
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" callback="@Override callbackTreeLoad"
		action="slDivisionController.do?doSave">
		<input id="id" name="id" type="hidden" value="${division.id }" />
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						行政区划编号: </label></td>
				<td class="value"><input id="divisionCode" name="code"
					type="text" maxlength="32" style="width: 150px" class="inputxt"
					value="${division.code }" datatype="s1-20" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">行政区划编号</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						行政区划名称: </label></td>
				<td class="value"><input id="divisionName" name="divisionName"
					type="text" maxlength="32" style="width: 150px" class="inputxt"
					value="${division.divisionName }" datatype="s1-20" /> <span
					class="Validform_checktip">行政区划名称范围1~20位字符</span> <label
					class="Validform_label" style="display: none;">行政区划名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						上级行政区划: </label></td>
				<td class="value"><input id="categoryTree"
					value="${division.parent.divisionName}"> <input
					id="parentId" name="parent.code"
					style="display: none;"
					value="${division.parent.code}"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">行政区划名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						行政区划排序: </label></td>
				<td class="value"><input id="divisionOrder"
					name="divisionOrder" type="text" maxlength="32"
					style="width: 150px" class="inputxt" ignore="ignore"
					value="${division.divisionOrder }" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">行政区划描述</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						行政区划备注: </label></td>
				<td class="value"><input id="divisionMemo" name="divisionMemo"
					type="text" maxlength="32" style="width: 150px" class="inputxt"
					ignore="ignore" value="${division.divisionMemo }" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">行政区划备注</label></td>
			</tr>


		</table>
	</t:formvalid>
	<script type="text/javascript">
function callbackTreeLoad(data){
		var win = frameElement.api.opener;
		if (data.success == true) {
			frameElement.api.close();
			win.tip(data.msg);
		} else {
			if (data.responseText == ''
					|| data.responseText == undefined) {
				$.messager.alert('错误', data.msg);
				$.Hidemsg();
			} else {
				try {
					var emsg = data.responseText
							.substring(
									data.responseText
											.indexOf('错误描述'),
									data.responseText
											.indexOf('错误信息'));
					$.messager.alert('错误', emsg);
					$.Hidemsg();
				} catch (ex) {
					$.messager.alert('错误',
							data.responseText + "");
					$.Hidemsg();
				}
			}
			return false;
		}
		win.reloadTreeNode();
}
</script>
</body>