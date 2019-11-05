<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>分类管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	$(function() {
		$('#entityTree').combotree({
			url : 'slRiverLakeController.do?combotree&param_river_lake_type=10_11_',
			panelHeight : 200,
			width : 157,
			onClick : function(node) {
				$("#parentId").val(node.id);
			}
		});

		if ($('#id').val()) {
			$('#categoryTree').combotree('disable');
		}
		
		$('#dd').combotree({
			url : 'slDivisionController.do?setPFunction&selfId=${depart.id}',
            width: 155,
            onSelect : function(node) {

                //changeOrgType(node.id);

            }
        });
		
		
	});
	function openMapDetail(){
		$.dialog.setting.zIndex = getzIndex(); 

		$.dialog({
		    content: 'url:slRiverLakeController.do?mapDetail',
		    zIndex: getzIndex(),
		    title: '地图资料查询',
		    lock: true,
		    width: '600px',
		    height: '600px',
		    opacity: 0.4,
		    button: [{
		        name: '<t:mutiLang langKey="common.confirm"/>',
		        callback: function() {},
		        focus: true
		    },
		    {
		        name: '<t:mutiLang langKey="common.cancel"/>',
		        callback: function() {}
		    }]
		}).zindex();
	}
	function openhzSelect() {
		$.dialog.setting.zIndex = getzIndex(); 
		var hzids = $("#hzids").val();

		$.dialog({
		    content: 'url:slRiverLakeController.do?riverChargeSelect&orgIds=' + hzids,
		    zIndex: getzIndex(),
		    title: '河长列表',
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

<script src="/static/js/modules/demo/multiUpload.js"></script>
<script type="text/javascript">
		  	//加载 已存在的 文件
		  	$(function(){
		  		var cgFormId=$("input[name='id']").val();
		  		
		  		$.ajax({
		  		   type: "post",
		  		   url: "multiUploadController.do?getFiles&id=" +  cgFormId,
		  		   success: function(data){
		  			 var arrayFileObj = jQuery.parseJSON(data).obj;
		  			 
		  			$.each(arrayFileObj,function(n,file){
		  				var fieldName = file.field.toLowerCase();
		  				var table = $("#"+fieldName+"_fileTable");
		  				var tr = $("<tr style=\"height:34px;\"></tr>");
		  				var title = file.title;
		  				if(title.length > 15){
		  					title = title.substring(0,12) + "...";
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
			  		  	if(location.href.indexOf("load=detail")!=-1){
				  			$(":input").attr("disabled","true");
				  			$(".jeecgDetail").css("display","none");
				  		}
		  			 });
		  		   }
		  		});
		  		

		  	});
		  	
		  	/**
		 	 * 删除图片数据资源
		 	 */
		  	function del(url,obj){
		  		var content = "请问是否要删除该资源";
		  		var navigatorName = "Microsoft Internet Explorer"; 
		  		if( navigator.appName == navigatorName ){ 
		  			$.dialog.confirm(content, function(){
		  				submit(url,obj);
		  			}, function(){
		  			});
		  		}else{
		  			layer.open({
						title:"提示",
						content:content,
						icon:7,
						yes:function(index){
							submit(url,obj);
						},
						btn:['确定','取消'],
						btn2:function(index){
							layer.close(index);
						}
					});
		  		}
		  	}
		  	
		  	function submit(url,obj){
		  		$.ajax({
		  			async : false,
		  			cache : false,
		  			type : 'POST',
		  			url : url,// 请求的action路径
		  			error : function() {// 请求失败处理函数
		  			},
		  			success : function(data) {
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
		  	
	  		function jeecgFormFileCallBack(data){
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
						} catch(ex) {
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
			function uploadFile(data){
				if(!$("input[name='id']").val()){
					if(data.obj!=null && data.obj!='undefined'){
						$("input[name='id']").val(data.obj.id);
					}
				}
				if($(".uploadify-queue-item").length>0){
					upload();
				}else{
					if (neibuClickFlag){
						alert(data.msg);
						neibuClickFlag = false;
					}else {
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
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="slRiverLakeController.do?saveComplaintEntity"
		callback="jeecgFormFileCallBack@Override">
		<input id="cgFormId" name="id" type="hidden" value="${entity.id}">
		<input id="cgFormName" name="cgFormName" type="hidden"
			value="t_sl_base_river_lake_patrol_event_photo">
		<input id="cgFormField_testFile1" type="hidden" value="photo">
		<input id="paramType" name="paramType" type="hidden"
			value="${paramType }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 投诉编号:</label></td>
				<td class="value"><input class="inputxt" id="code" name="code"
					ignore="ignore" value="${entity.code}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 投诉来源:</label></td>
				<td class="value"><t:dictSelect id="sourceType"
						field="sourceType" typeGroupCode="sourceType" removeTypeCode="10"
						hasLabel="false"
						defaultVal="${entity.sourceType==null?'0':(entity.sourceType)}"
						datatype="*"></t:dictSelect> <span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 投拆人:</label></td>
				<td class="value"><input class="inputxt" id="complainantName"
					name="complainantName" ignore="ignore"
					value="${entity.complainantName}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 联系方式:</label></td>
				<td class="value"><input class="inputxt" id="complainantPhone"
					name="complainantPhone" ignore="ignore"
					value="${entity.complainantPhone}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 投诉类型:</label></td>
				<td class="value"><t:dictSelect id="questType"
						field="questType" typeGroupCode="questType" hasLabel="false"
						defaultVal="${entity.questType==null?'0':(entity.questType)}"
						datatype="*"></t:dictSelect> <span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 行政地区:</label></td>
				<td class="value"><input id="dd"
					name="tslDivision.code"
					value="${entity.tslDivision.code}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 投诉河湖:</label></td>
				<td class="value"><input id="entityTree"
					value="${entity.tslBaseRiverLake.name}"> <input
					id="parentId" name="parentId" style="display: none;"
					value="${entity.tslBaseRiverLake.code}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						投诉具体地址:</label></td>
				<td class="value"><input class="inputxt" id="eventAddress"
					name="eventAddress" ignore="ignore" value="${entity.eventAddress}">
					<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 问题描述:</label></td>
				<td class="value"><textarea rows="4" cols="15"
						class="inputxtarea" id="memo" name="memo" ignore="ignore"
						style="margin: 0px; height: 150px; width: 260px;">${entity.memo} </textarea><span
					class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"
					style="white-space: nowrap;"> 附件: </label></td>
				<td class="value">
					<div class="form jeecgDetail">
						<t:upload name="testFile1" id="testFile1"
							queueID="filediv_testFile1"
							uploader="cgUploadController.do?saveFiles" extend=""
							formData="cgFormId,cgFormName,cgFormField_testFile1"
							outhtml="true"></t:upload>
					</div>
					<div class="form" id="filediv_testFile1"></div>
					<table id="photo_fileTable"></table> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">附件</label>
				</td>
			</tr>


			<tr>
				<td align="right"><label class="Validform_label"
					style="white-space: nowrap;"> 投诉时间: </label></td>
				<td class="value"><input type="text" class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					style="width: 150px" id="eventDate" name="eventDate"
					ignore="ignore"
					value="<fmt:formatDate value='${entity.eventDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">投诉时间</label></td>
			</tr>


		</table>
	</t:formvalid>
</body>