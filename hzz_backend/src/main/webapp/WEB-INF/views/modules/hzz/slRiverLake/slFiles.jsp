
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>文件列表</title>
<t:base type="jquery,easyui,tools"></t:base>
<style>
.form {
	min-height: 46px;
}
</style>
</head>
<body style="overflow-y: auto" scroll="no">
	<t:formvalid formid="formobj" layout="div" dialog="true"
		action="slRiverLakeController.do?saveFiles"
		beforeSubmit="myBeforeSubmit"
		callback="jeecgFormFileCallBack@Override">
		<input type="hidden" id="riverId" name="riverId"
			<c:if test="${not empty entity }">value="${entity.id }"</c:if>>
		<input type="hidden" id="paramType1" value="${paramType1}">
		<fieldset class="step">
			<c:if test="${paramType1 == 'publicSignsDoc'}">
				<div class="form">
					<label class="Validform_label"> 公示牌经纬度: </label> <input
						name="publicSignsLngLat" id="publicSignsLngLat" datatype="*3-50"
						type="text" value=${entity.publicSignsLngLat}> <a
						href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
						id="hzSearch1" onclick="openMapDetail1()">地图资料查询</a> <span
						class="Validform_checktip"></span>
				</div>
			</c:if>
			<div class="form">
				<t:upload name="fiels" buttonText="上传文件"
					uploader="cgUploadController.do?saveFiles" extend=""
					id="file_upload" outhtml="true"
					formData="cgFormId,cgFormName,cgFormField_testFile1"></t:upload>
			</div>

			<c:if test="${paramType1 == 'archivesDoc'}">
				<div class="form" id="filediv">
					<table id="archives_doc_fileTable"></table>
				</div>
				<input id="cgFormField_testFile1" type="hidden" value="archives_doc">

			</c:if>

			<c:if test="${paramType1 == 'policyDoc'}">
				<div class="form" id="filediv">
					<table id="policy_doc_fileTable"></table>
				</div>
				<input id="cgFormField_testFile1" type="hidden" value="policy_doc">
			</c:if>

			<c:if test="${paramType1 == 'publicSignsDoc'}">
				<div class="form" id="filediv">
					<table id="public_signs_doc_fileTable"></table>
				</div>
				<input id="cgFormField_testFile1" type="hidden"
					value="public_signs_doc">
			</c:if>



			<input type="hidden" id="cgFormId" name="id"
				<c:if test="${not empty entity }">value="${entity.id }"</c:if>>
			<input id="cgFormName" name="cgFormName" type="hidden"
				value="t_sl_base_river_lake"> <input id="paramType"
				name="paramType" type="hidden" value="${paramType }">

		</fieldset>
	</t:formvalid>
	<script src="/static/js/modules/demo/multiUpload.js"></script>
	<script type="text/javascript">
//加载 已存在的 文件
	$(function(){
		var cgFormId=$("input[name='riverId']").val();
		
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
	<script type="text/javascript">
function myBeforeSubmit(){

	var fileLen = $("#filediv").find(".uploadify-queue-item").length;
	if(fileLen>0){
		// upload 为jeecg方法,总是返回false,这边应该是jeecg的bug
		var result = upload();
		return true;
	}else{
		tip("请选择文件")
		return false;
	}
}

function openMapDetail(){
	$.dialog.setting.zIndex = getzIndex(); 

	$.dialog({
	    content: 'url:slRiverLakeController.do?openViewFile&fileid=${entity.id }&paramType1=${paramType1}',
	    zIndex: getzIndex(),
	    title: '档案资料',
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


function openMapDetail1(){
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
	        callback: function() {
				var iframe = this.iframe.contentWindow;
				var paramTypeValue = iframe.$("#paramTypeValue").val();
				$('#publicSignsLngLat').val(paramTypeValue);
			},
	        focus: true
	    },
	    {
	        name: '<t:mutiLang langKey="common.cancel"/>',
	        callback: function() {}
	    }]
	}).zindex();
}
</script>
</body>
</html>