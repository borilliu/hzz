<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>工作简报信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:base type="uploadify"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="workReportController.do?doAdd" callback="jeecgFormFileCallBack@Override">
					<input id="id" name="id" type="hidden" value="${workReportPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							简报标题:
						</label>
					</td>
					<td class="value">
					     	 <input id="title" name="title" type="text" maxlength="32" style="width: 150px" class="inputxt"  datatype="*"  ignore="checked" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">简报标题</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							简报批次:
						</label>
					</td>
					<td class="value">
					     	 <input id="batch" name="batch" type="text" maxlength="32" style="width: 150px" class="inputxt"  datatype="*"  ignore="checked" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">简报批次</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发布日期:
						</label>
					</td>
					<td class="value">
							   <input id="publishDate" name="publishDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"  datatype="*"  ignore="checked" />    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布日期</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发布来源:
						</label>
					</td>
					<td class="value">
					     	 <input id="publishSources" name="publishSources" type="text" maxlength="100" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布来源</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发布单位编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="publishOrgCode" name="publishOrgCode" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布单位编码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发布单位:
						</label>
					</td>
					<td class="value">
					     	 <input id="publishOrgName" name="publishOrgName" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布单位</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							附件:
						</label>
					</td>
					<td class="value">
		<div class="form jeecgDetail">
			<t:upload name="accessory" id="accessory" queueID="filediv_accessory" outhtml="false" uploader="cgUploadController.do?saveFiles"  extend="office" buttonText='添加文件'  onUploadStart="accessoryOnUploadStart"> </t:upload>
			<div class="form" id="filediv_accessory"></div>
			<script type="text/javascript">
				function accessoryOnUploadStart(file){
					var cgFormId=$("input[name='id']").val();
					$('#accessory').uploadify("settings", "formData", {
						'cgFormId':cgFormId,
						'cgFormName':'t_sl_work_report',
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
							发布内容:
						</label>
					</td>
					<td class="value" >
								<script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.config.js"></script>
								<script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.all.min.js"></script>
						    	<textarea name="publishContent" id="publishContent" style="width: 650px;height:300px"></textarea>
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
						  	 <textarea style="height:auto;width:95%" class="inputxt" rows="6" id="remarks" name="remarks"  ignore="ignore" ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
	  	<script type="text/javascript">
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
			}
			
			var neibuClickFlag = false;
			function neibuClick() {
				neibuClickFlag = true; 
				$('#btn_sub').trigger('click');
			}
			function cancel() {
					$('#accessory').uploadify('cancel', '*');
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
