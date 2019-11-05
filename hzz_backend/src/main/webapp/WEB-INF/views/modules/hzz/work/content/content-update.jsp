<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>公文管理信息报</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:base type="uploadify"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="contentController.do?doUpdate" callback="jeecgFormFileCallBack@Override">
					<input id="id" name="id" type="hidden" value="${contentPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容标题:
							</label>
						</td>
						<td class="value">
						    <input id="title" name="title" type="text" maxlength="100" style="width: 150px" class="inputxt"  datatype="*" ignore="checked"  value='${contentPage.title}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">内容标题</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								分类名称:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="groupCode" type="list"  datatype="*" typeGroupCode="content_class_name"   defaultVal="${contentPage.groupCode}" hasLabel="false"  title="分类名称" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分类名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发布来源:
							</label>
						</td>
						<td class="value">
						    <input id="publishSources" name="publishSources" type="text" maxlength="100" style="width: 150px" class="inputxt"  ignore="ignore"  value='${contentPage.publishSources}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布来源</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发布时间:
							</label>
						</td>
						<td class="value">
									  <input id="publishDate" name="publishDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  datatype="*" ignore="checked" value='<fmt:formatDate value='${contentPage.publishDate}' type="date" pattern="yyyy-MM-dd"/>'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发布单位编码:
							</label>
						</td>
						<td class="value">
						    <input id="publishOrgCode" name="publishOrgCode" type="text" maxlength="20" style="width: 150px" class="inputxt"  ignore="ignore"  value='${contentPage.publishOrgCode}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布单位编码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发布单位名称:
							</label>
						</td>
						<td class="value">
						    <input id="publishOrgName" name="publishOrgName" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore"  value='${contentPage.publishOrgName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布单位名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								附件:
							</label>
						</td>
						<td class="value">
		<table id="accessory_fileTable"></table>
		<div class="form jeecgDetail">
			<t:upload name="accessory" id="accessory" queueID="filediv_accessory" outhtml="false" uploader="cgUploadController.do?saveFiles"  extend="office" buttonText='添加文件'  onUploadStart="accessoryOnUploadStart"> </t:upload>
			<div class="form" id="filediv_accessory"></div>
			<script type="text/javascript">
				function accessoryOnUploadStart(file){
					var cgFormId=$("input[name='id']").val();
					$('#accessory').uploadify("settings", "formData", {
						'cgFormId':cgFormId,
						'cgFormName':'t_sl_content',
						'cgFormField':'ACCESSORY'
					});
				}
			</script>
		</div>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">附件</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								标题图片:
							</label>
						</td>
						<td class="value">
		<table id="title_image_fileTable"></table>
		<div class="form jeecgDetail">
			<t:upload name="titleImage" id="titleImage" queueID="filediv_titleImage" outhtml="false" uploader="cgUploadController.do?saveFiles"  extend="pic" buttonText='添加图片'  onUploadStart="titleImageOnUploadStart"> </t:upload>
			<div class="form" id="filediv_titleImage"></div>
			<script type="text/javascript">
				function titleImageOnUploadStart(file){
					var cgFormId=$("input[name='id']").val();
					$('#titleImage').uploadify("settings", "formData", {
						'cgFormId':cgFormId,
						'cgFormName':'t_sl_content',
						'cgFormField':'TITLE_IMAGE'
					});
				}
			</script>
		</div>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标题图片</label>
						</td>
					</tr>
				
					<tr>
						<td align="right">
							<label class="Validform_label">
								发布内容:
							</label>
						</td>
						<td class="value" >
									<script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.config.js"></script>
									<script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.all.min.js"></script>
                                <textarea name="publishContent" id="publishContent" style="width: 650px;height:300px">${contentPage.publishContent }</textarea>

                                <script type="text/javascript">
							        var publishContent_editor = UE.getEditor('publishContent');
								    </script>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布内容</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value" >
						  	 	<textarea id="remarks" style="height:auto;width:95%;" class="inputxt" rows="6" name="remarks"  ignore="ignore" >${contentPage.remarks}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
	  	<script type="text/javascript">
		  	//加载 已存在的 文件
		  	$(function(){
	  			var cgFormId=$("input[name='id']").val();
		  		$.ajax({
		  		   type: "post",
		  		   url: "contentController.do?getFiles&id=" +  cgFormId,
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
		  		  		tr.appendTo(table);
		  		  		td_title.appendTo(tr);
		  		  		td_download.appendTo(tr);
		  		  		td_view.appendTo(tr);
		  		  		if(location.href.indexOf("load=detail")==-1){
			  		  		var td_del = $("<td><a style=\"margin-left:10px;\" href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=" + file.fileKey + "',this)\">删除</a></td>");
			  		  		td_del.appendTo(tr);
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
	  		function upload() {
					$('#accessory').uploadify('upload', '*');	
					$('#titleImage').uploadify('upload', '*');	
			}
			
			var neibuClickFlag = false;
			function neibuClick() {
				neibuClickFlag = true; 
				$('#btn_sub').trigger('click');
			}
			function cancel() {
					$('#accessory').uploadify('cancel', '*');	
					$('#titleImage').uploadify('cancel', '*');	
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
