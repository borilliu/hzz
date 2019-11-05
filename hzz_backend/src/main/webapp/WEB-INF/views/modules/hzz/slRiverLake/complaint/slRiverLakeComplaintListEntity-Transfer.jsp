<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>jeecg_demo</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  function openhzSelect() {
		$.dialog.setting.zIndex = getzIndex(); 
		var hzids = $("#hzids").val();

		$.dialog({
		    content: 'url:slRiverLakeController.do?riverChargeSelect&ifcheckdept=Y&orgIds=' + hzids,
		    zIndex: getzIndex(),
		    title: '选择转派部门和人员',
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
		        callback: function() {}
		    }]
		}).zindex();

	}
  function callbackDepartmentSelect() {
	  var iframe = this.iframe.contentWindow;
	  var treeObj = iframe.$.fn.zTree.getZTreeObj("departSelect");
	  var nodes = treeObj.getCheckedNodes(true);
	  if(nodes.length>0){
	  var ids='',names='';
	  for(i=0;i<nodes.length;i++){
	     var node = nodes[i];
	     ids += node.id+',';
	    names += node.name+',';
	 }
	 $('#hzname').val(names);
	 $('#hzname').blur();		
	 $('#hzids').val(ids);		
	}
}
  function callbackClean(){
		$('#hzname').val('');
		$('#hzids').val('');	
	}
  </script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="slRiverLakeController.do?doComplaintTransfer">
		<input id="id" name="id" type="hidden" value="${entity.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						转派部门及人员: </label></td>
				<td class="value"><input id="hzname" name="hzname" type="text"
					readonly="readonly" class="inputxt" datatype="*" value="${hzname}" />
					<input id="hzids" name="hzids" type="hidden" value="${hzids}" /> <a
					href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					id="hzSearch" onclick="openhzSelect()">选择</a> <a href="#"
					class="easyui-linkbutton" plain="true" icon="icon-redo" id="hzRedo"
					onclick="callbackClean()">清空</a> <span class="Validform_checktip">转派部门及人员可多选</span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 转派备注:
				</label></td>
				<td class="value"><textarea name="content" rows="6" cols="36"></textarea>
					<span class="Validform_checktip"></span></td>
			</tr>
		</table>
	</t:formvalid>
</body>