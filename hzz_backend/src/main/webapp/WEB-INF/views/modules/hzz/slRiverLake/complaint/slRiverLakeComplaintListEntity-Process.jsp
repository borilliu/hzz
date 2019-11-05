<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>jeecg_demo</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
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
		layout="table" action="slRiverLakeController.do?doComplaintProcess"
		callback="jeecgFormFileCallBack@Override">
		<input id="cgFormId" name="id" type="hidden" value="${entity.id }">
		<input id="cgFormName" name="cgFormName" type="hidden"
			value="t_sl_base_river_lake_patrol_event_photo1">
		<input id="cgFormField_testFile1" type="hidden" value="photo1">
		<input id="paramType" name="paramType" type="hidden"
			value="${paramType }">

		<table style="width: 100%;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 处理结果:
				</label></td>
				<td class="value"><textarea name="handleMemo" rows="6"
						cols="36" datatype="*"></textarea> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"
					style="white-space: nowrap;"> 处理时间: </label></td>
				<td class="value"><input type="text" class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					style="width: 150px" readonly id="handleDate" name="handleDate"
					value="<fmt:formatDate value='${curDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"
					datatype="*"> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">处理时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 是否属实:</label></td>
				<td class="value"><t:dictSelect id="handleIsTF"
						field="handleIsTF" typeGroupCode="handleIsTF" hasLabel="false"
						defaultVal="10" datatype="*"></t:dictSelect> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 处理类型:</label></td>
				<td class="value"><t:dictSelect id="handleType"
						field="handleType" typeGroupCode="handleType" hasLabel="false"
						defaultVal="10" datatype="*"></t:dictSelect> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						处理人姓名:</label></td>
				<td class="value"><input class="inputxt" id="handleName"
					name="handleName" datatype="*"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						处理人电话:</label></td>
				<td class="value"><input class="inputxt" id="handlePhone"
					name="handlePhone" ignore="ignore"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						处理人部门:</label></td>
				<td class="value"><input class="inputxt" id="handleDept"
					name="handleDept" ignore="ignore"> <span
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
					<table id="photo1_fileTable"></table> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">附件</label>
				</td>
			</tr>


		</table>
	</t:formvalid>
</body>