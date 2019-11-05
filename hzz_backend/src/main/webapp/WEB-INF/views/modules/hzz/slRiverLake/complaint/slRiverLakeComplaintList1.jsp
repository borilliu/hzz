<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid checkbox="false" name="entityList" title="公众投诉管理"
			actionUrl="slRiverLakeController.do?datagridComplaintEntity&userRight=${userRight}"
			idField="id" fit="true" fitColumns="true" queryMode="group">
			<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="投诉编号" sortable="true" field="code" query="true"
				width="100"></t:dgCol>
			<t:dgCol title="投诉来源" sortable="true" field="sourceType"
				dictionary="sourceType" query="false" width="100"></t:dgCol>
			<t:dgCol title="投拆人" sortable="true" field="complainantName"
				query="true" width="100"></t:dgCol>
			<t:dgCol title="联系方式" sortable="true" field="complainantPhone"
				query="true" width="100"></t:dgCol>
			<t:dgCol title="地址" sortable="false" field="eventAddress"
				query="true" width="100"></t:dgCol>
			<t:dgCol title="河湖名称" sortable="true" field="tslBaseRiverLake.name"
				width="100"></t:dgCol>
			<t:dgCol title="投诉类型" sortable="false" field="questType"
				dictionary="questType" query="false" width="100"></t:dgCol>
			<t:dgCol title="投诉时间" sortable="true" field="eventDate" query="false"
				width="100"></t:dgCol>
			<t:dgCol title="处理用时" sortable="false" field="handleHour"
				query="false" width="100"></t:dgCol>
			<t:dgCol title="处理状态" sortable="false" field="eventStatus"
				dictionary="eventStatus" query="false" width="100"></t:dgCol>
			<t:dgCol title="common.operation" field="opt" width="200"></t:dgCol>

			<t:dgFunOpt title="修改" exp="eventStatus#eq#20,200"
				urlclass="ace_button" urlfont="fa-edit" funname="editoperation(id)"></t:dgFunOpt>

			<t:dgDelOpt title="删除" exp="eventStatus#eq#20,200"
				url="slRiverLakeController.do?delComplaintEntity&id={id}"
				urlclass="ace_button" urlfont="fa-trash-o" />
			<t:dgConfOpt title="送审" exp="eventStatus#eq#20,200"
				urlclass="ace_button" urlfont="fa-toggle-on" message="确认送审"
				url="slRiverLakeController.do?processComplaint&id={id}" />

			<t:dgFunOpt title="转派" exp="eventStatus#eq#22,23"
				urlclass="ace_button" urlfont="fa-edit" funname="szqm1(id)"></t:dgFunOpt>
			<t:dgFunOpt title="处理" exp="eventStatus#eq#22,23"
				urlclass="ace_button" urlfont="fa-edit" funname="szqm2(id)"></t:dgFunOpt>


			<t:dgToolBar icon="icon-add" title="录入单据"
				url="slRiverLakeController.do?addorupdateComplaintEntity&paramType=${paramType}"
				funname="addEntity"></t:dgToolBar>
			<t:dgToolBar title="查看投诉" icon="icon-search"
				url="slRiverLakeController.do?addorupdateComplaintEntity"
				funname="detailEntity"></t:dgToolBar>
			<t:dgToolBar title="查看处理" icon="icon-search"
				url="slRiverLakeController.do?viewComplaintProcess"
				funname="detailEntity"></t:dgToolBar>
			<t:dgToolBar title="查看流程" icon="icon-search"
				url="slRiverLakeController.do?goViewProcess" funname="detailEntity"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	function editoperation(operationId)
	{
		createwindow("<t:mutiLang langKey="common.edit.param" langArg="common.operation"/>","slRiverLakeController.do?addorupdateComplaintEntity&id="+operationId,"100%","100%");
	}
	function szqm(id) {
		createwindow('公众投诉审查', 'slRiverLakeController.do?goComplaintCheck&id=' + id,400,200);
	}
	function szqm1(id) {
		createwindow('公众投诉转派', 'slRiverLakeController.do?goComplaintTransfer&id=' + id,800,200);
	}
	function szqm2(id) {
		createwindow('公众投诉处理', 'slRiverLakeController.do?goComplaintProcess&id=' + id,800,500);
	}
	function szqm3(id) {
		createwindow('公众投诉反馈', 'slRiverLakeController.do?goComplaintReturn&id=' + id,400,200);
	}
	function formatAgeFun(tsUser,row,index){
		if ($.isEmptyObject(tsUser)){
			
		}else{
			alert(tsUser[0].userName);	
		}
				
		var str="原：";
		return str;
	}
	function addEntity(title, url, id) {
		add(title, url, 'entityList', '100%', '100%');
	}
	function detailEntity(title, url, id) {
		detail(title, url, 'entityList', '100%', '100%');
	}
	function updateEntity(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&code=' + rowData.id;
		}
		update(title, url, 'entityList', '100%', '100%');
	}
	function mapDetail1(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&id=' + rowData.id;
		}
		//update(title, url, 'entityList', '600px', '600px');
		
		$.dialog.setting.zIndex = getzIndex(); 

		$.dialog({
		    content: 'url:'+url,
		    zIndex: getzIndex(),
		    title: title,
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
	function patrolList(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&code=' + rowData.id;
		}
		update(title, url, 'entityList', '100%', '100%');
	}
</script>