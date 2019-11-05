<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">

		<c:if test="${paramType == 'a'}">
			<t:datagrid name="entityList" title="河流管理"
				actionUrl="slRiverLakeController.do?datagridEntity&paramType=${paramType}"
				idField="id" fit="true" fitColumns="true" queryMode="group">
				<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
				<t:dgCol title="河流名称" sortable="false" field="name" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="河流代码" sortable="false" field="code" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="河长姓名" sortable="false" field="createName"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="上级河湖" sortable="false" field="parent.name"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="河流长度(公里)" sortable="false" field="riverLength"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="流域面积(平方公里)" sortable="false" field="waterArea"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="河流类型" sortable="false" field="riverType"
					dictionary="riverType" query="false" width="100"></t:dgCol>
				<t:dgCol title="common.operation" field="opt" width="200"></t:dgCol>
				<t:dgDelOpt title="common.delete"
					url="slRiverLakeController.do?delEntity&id={id}&paramType=${paramType}"
					urlclass="ace_button" urlfont="fa-trash-o"
					urlStyle="background-color:#ec4758;" />

				<t:dgFunOpt title="一河一档" funname="updateEntity1(id)"
					urlclass="ace_button" urlfont="fa-cog" />
				<t:dgFunOpt title="一河一策" funname="updateEntity2(id)"
					urlclass="ace_button" urlfont="fa-cog" />
				<t:dgFunOpt title="河长公示牌" funname="updateEntity3(id)"
					urlclass="ace_button" urlfont="fa-cog" />


				<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="addEntity"></t:dgToolBar>


				<t:dgToolBar icon="icon-edit" title="common.edit"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="updateEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="河流地图"
					url="slRiverLakeController.do?mapDetail1&paramType=${paramType}"
					funname="mapDetail1"></t:dgToolBar>

				<t:dgToolBar title="一河一档" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=archivesDoc"></t:dgToolBar>
				<t:dgToolBar title="一河一策" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=policyDoc"></t:dgToolBar>
				<t:dgToolBar title="河长公示牌" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=publicSignsDoc"></t:dgToolBar>

			</t:datagrid>
		</c:if>
		<c:if test="${paramType == 'b'}">
			<t:datagrid name="entityList" title="湖泊管理"
				actionUrl="slRiverLakeController.do?datagridEntity&paramType=${paramType}"
				idField="id" fit="true" fitColumns="true" queryMode="group">
				<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
				<t:dgCol title="湖泊名称" sortable="false" field="name" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="湖泊代码" sortable="false" field="code" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="湖长姓名" sortable="false" field="createName"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="上级河湖" sortable="false" field="parent.name"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="流域面积(平方公里)" sortable="false" field="waterArea"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="平均水深(米)" sortable="false" field="meanDepth"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="湖泊容积(m³)" sortable="false" field="lakeVolume"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="最大水深(米)" sortable="false" field="maxWaterDepth"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="咸淡属性" sortable="false" field="saltyAttr"
					dictionary="saltyAttr" query="false" width="100"></t:dgCol>
				<t:dgCol title="common.operation" field="opt" width="200"></t:dgCol>
				<t:dgDelOpt title="common.delete"
					url="slRiverLakeController.do?delEntity&id={id}&paramType=${paramType}"
					urlclass="ace_button" urlfont="fa-trash-o"
					urlStyle="background-color:#ec4758;" />

				<t:dgFunOpt title="一河一档" funname="updateEntity1(id)"
					urlclass="ace_button" urlfont="fa-cog" />
				<t:dgFunOpt title="一河一策" funname="updateEntity2(id)"
					urlclass="ace_button" urlfont="fa-cog" />
				<t:dgFunOpt title="河长公示牌" funname="updateEntity3(id)"
					urlclass="ace_button" urlfont="fa-cog" />


				<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="addEntity"></t:dgToolBar>


				<t:dgToolBar icon="icon-edit" title="common.edit"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="updateEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="湖泊地图"
					url="slRiverLakeController.do?mapDetail1&paramType=${paramType}"
					funname="mapDetail1"></t:dgToolBar>

				<t:dgToolBar title="一河一档" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=archivesDoc"></t:dgToolBar>
				<t:dgToolBar title="一河一策" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=policyDoc"></t:dgToolBar>
				<t:dgToolBar title="河长公示牌" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=publicSignsDoc"></t:dgToolBar>

			</t:datagrid>
		</c:if>
		<c:if test="${paramType == 'c'}">
			<t:datagrid name="entityList" title="河段管理"
				actionUrl="slRiverLakeController.do?datagridEntity&paramType=${paramType}"
				idField="id" fit="true" fitColumns="true" queryMode="group">
				<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
				<t:dgCol title="河段名称" sortable="false" field="name" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="河段代码" sortable="false" field="code" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="河段长姓名" sortable="false" field="createName"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="上级河湖" sortable="false" field="parent.name"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="河段长度(公里)" sortable="false" field="riverLength"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="流域面积(平方公里)" sortable="false" field="waterArea"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="河段类型" sortable="false" field="riverType"
					dictionary="riverType" query="false" width="100"></t:dgCol>
				<t:dgCol title="common.operation" field="opt" width="200"></t:dgCol>
				<t:dgDelOpt title="common.delete"
					url="slRiverLakeController.do?delEntity&id={id}&paramType=${paramType}"
					urlclass="ace_button" urlfont="fa-trash-o"
					urlStyle="background-color:#ec4758;" />

				<t:dgFunOpt title="一河一档" funname="updateEntity1(id)"
					urlclass="ace_button" urlfont="fa-cog" />
				<t:dgFunOpt title="一河一策" funname="updateEntity2(id)"
					urlclass="ace_button" urlfont="fa-cog" />
				<t:dgFunOpt title="河长公示牌" funname="updateEntity3(id)"
					urlclass="ace_button" urlfont="fa-cog" />


				<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="addEntity"></t:dgToolBar>


				<t:dgToolBar icon="icon-edit" title="common.edit"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="updateEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="河段地图"
					url="slRiverLakeController.do?mapDetail1&paramType=${paramType}"
					funname="mapDetail1"></t:dgToolBar>

				<t:dgToolBar title="一河一档" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=archivesDoc"></t:dgToolBar>
				<t:dgToolBar title="一河一策" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=policyDoc"></t:dgToolBar>
				<t:dgToolBar title="河长公示牌" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=publicSignsDoc"></t:dgToolBar>

			</t:datagrid>
		</c:if>
		<c:if test="${paramType == 'd'}">
			<t:datagrid name="entityList" title="水库管理"
				actionUrl="slRiverLakeController.do?datagridEntity&paramType=${paramType}"
				idField="id" fit="true" fitColumns="true" queryMode="group">
				<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
				<t:dgCol title="水库名称" sortable="false" field="name" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="水库代码" sortable="false" field="code" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="水库长姓名" sortable="false" field="createName"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="防洪高水位(m)" sortable="false" field="highFloodLevel"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="正常蓄水位 (m)" sortable="false" field="normalWaterLevel"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="主汛期防洪限制水位(m)" sortable="false"
					field="floodPeriodLevel" query="false" width="100"></t:dgCol>
				<t:dgCol title="总库容(m³)" sortable="false" field="totalCapacity"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="调洪库容(m³)" sortable="false" field="floodCapacity"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="防洪库容(m³)" sortable="false" field="floodStorage"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="水库类型" sortable="false" field="reservoirType"
					dictionary="reservoirType" query="false" width="100"></t:dgCol>
				<t:dgCol title="common.operation" field="opt" width="250"></t:dgCol>
				<t:dgDelOpt title="common.delete"
					url="slRiverLakeController.do?delEntity&id={id}&paramType=${paramType}"
					urlclass="ace_button" urlfont="fa-trash-o"
					urlStyle="background-color:#ec4758;" />
				<t:dgFunOpt title="一河一档" funname="updateEntity1(id)"
					urlclass="ace_button" urlfont="fa-cog" />
				<t:dgFunOpt title="一河一策" funname="updateEntity2(id)"
					urlclass="ace_button" urlfont="fa-cog" />
				<t:dgFunOpt title="河长公示牌" funname="updateEntity3(id)"
					urlclass="ace_button" urlfont="fa-cog" />
				<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="addEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="common.edit"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="updateEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="河段地图"
					url="slRiverLakeController.do?mapDetail1&paramType=${paramType}"
					funname="mapDetail1"></t:dgToolBar>
				<t:dgToolBar title="一河一档" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=archivesDoc"></t:dgToolBar>
				<t:dgToolBar title="一河一策" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=policyDoc"></t:dgToolBar>
				<t:dgToolBar title="河长公示牌" icon="icon-edit" funname="update"
					url="slRiverLakeController.do?addorupdateFiles&paramType=${paramType}&paramType1=publicSignsDoc"></t:dgToolBar>
			</t:datagrid>
		</c:if>
		<c:if test="${paramType == 'a1'}">
			<t:datagrid name="entityList" title="取水口管理"
				actionUrl="slRiverLakeController.do?datagridEntity&paramType=${paramType}"
				idField="id" fit="true" fitColumns="true" queryMode="group">
				<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
				<t:dgCol title="取水口代码" sortable="false" field="code" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="取水口名称" sortable="false" field="name" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="取水口位置" sortable="false" field="address"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="取水方式" sortable="false" field="takeType"
					dictionary="takeType" query="false" width="100"></t:dgCol>
				<t:dgCol title="负责人" sortable="false" field="createName"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="水闸名称" sortable="false" field="gateName"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="引调水工程名称" sortable="false"
					field="diversionProjectName" query="false" width="100"></t:dgCol>
				<t:dgCol title="地表水水源地名称" sortable="false" field="waterSourceSName"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="上级河湖" sortable="false" field="parent.name"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
				<t:dgDelOpt title="common.delete"
					url="slRiverLakeController.do?delEntity&id={id}&paramType=${paramType}"
					urlclass="ace_button" urlfont="fa-trash-o"
					urlStyle="background-color:#ec4758;" />
				<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="addEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="common.edit"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="updateEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="河段地图"
					url="slRiverLakeController.do?mapDetail1&paramType=${paramType}"
					funname="mapDetail1"></t:dgToolBar>
			</t:datagrid>
		</c:if>

		<c:if test="${paramType == 'b1'}">
			<t:datagrid name="entityList" title="水功能区管理"
				actionUrl="slRiverLakeController.do?datagridEntity&paramType=${paramType}"
				idField="id" fit="true" fitColumns="true" queryMode="group">
				<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
				<t:dgCol title="水功能区代码" sortable="false" field="code" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="水功能区名称" sortable="false" field="name" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="一级水功能区名称" sortable="false" field="primaryName"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="二级水功能区名称" sortable="false" field="secondaryName"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="用水类型" sortable="false" field="useWaterType"
					dictionary="useWaterType" query="false" width="100"></t:dgCol>
				<t:dgCol title="水功能区长度(千米)" sortable="false"
					field="waterFunctionLength" query="false" width="100"></t:dgCol>
				<t:dgCol title="水功能区水质目标" sortable="false" field="waterQuality"
					dictionary="waterQuality" query="false" width="100"></t:dgCol>
				<t:dgCol title="水功能区起始断面" sortable="false" field="startSection"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="水功能区终止断面" sortable="false" field="endSection"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="监测断面" sortable="false" field="monitoringSection"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="上级河湖" sortable="false" field="parent.name"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
				<t:dgDelOpt title="common.delete"
					url="slRiverLakeController.do?delEntity&id={id}&paramType=${paramType}"
					urlclass="ace_button" urlfont="fa-trash-o"
					urlStyle="background-color:#ec4758;" />
				<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="addEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="common.edit"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="updateEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="河段地图"
					url="slRiverLakeController.do?mapDetail1&paramType=${paramType}"
					funname="mapDetail1"></t:dgToolBar>
			</t:datagrid>
		</c:if>

		<c:if test="${paramType == 'c1'}">
			<t:datagrid name="entityList" title="测站管理"
				actionUrl="slRiverLakeController.do?datagridEntity&paramType=${paramType}"
				idField="id" fit="true" fitColumns="true" queryMode="group">
				<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
				<t:dgCol title="测站代码" sortable="false" field="code" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="测站名称" sortable="false" field="name" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="测站所在地" sortable="false" field="location"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="测站年月" sortable="false" field="stationYear"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="测站类别" sortable="false" field="stationType"
					dictionary="stationType" query="false" width="100"></t:dgCol>
				<t:dgCol title="监测河湖" sortable="false" field="parent.name"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
				<t:dgDelOpt title="common.delete"
					url="slRiverLakeController.do?delEntity&id={id}&paramType=${paramType}"
					urlclass="ace_button" urlfont="fa-trash-o"
					urlStyle="background-color:#ec4758;" />
				<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="addEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="common.edit"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="updateEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="河段地图"
					url="slRiverLakeController.do?mapDetail1&paramType=${paramType}"
					funname="mapDetail1"></t:dgToolBar>
			</t:datagrid>
		</c:if>

		<c:if test="${paramType == 'd1'}">
			<t:datagrid name="entityList" title="排污口管理"
				actionUrl="slRiverLakeController.do?datagridEntity&paramType=${paramType}"
				idField="id" fit="true" fitColumns="true" queryMode="group">
				<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
				<t:dgCol title="排污口代码" sortable="false" field="code" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="排污口名称" sortable="false" field="name" query="true"
					width="100"></t:dgCol>
				<t:dgCol title="排水口位置" sortable="false" field="location"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="排污口类型" sortable="false" field="sewageOutType"
					dictionary="sewageOutType" query="false" width="100"></t:dgCol>
				<t:dgCol title="入河方式" sortable="false" field="riverEntryMode"
					dictionary="riverEntryMode" query="false" width="100"></t:dgCol>
				<t:dgCol title="排放方式" sortable="false" field="emissionMode"
					dictionary="emissionMode" query="false" width="100"></t:dgCol>
				<t:dgCol title="排入一级水功能区域" sortable="false" field="intoPrimary"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="排入二级水功能区域" sortable="false" field="intoSecondary"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="污水主要来源" sortable="false" field="sourcesSewageType"
					dictionary="sourcesSewageType" query="false" width="100"></t:dgCol>
				<t:dgCol title="排污量(万吨)" sortable="false" field="pollutantDischarge"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="上级河湖" sortable="false" field="parent.name"
					query="false" width="100"></t:dgCol>
				<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
				<t:dgDelOpt title="common.delete"
					url="slRiverLakeController.do?delEntity&id={id}&paramType=${paramType}"
					urlclass="ace_button" urlfont="fa-trash-o"
					urlStyle="background-color:#ec4758;" />
				<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="addEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="common.edit"
					url="slRiverLakeController.do?addorupdateEntity&paramType=${paramType}"
					funname="updateEntity"></t:dgToolBar>
				<t:dgToolBar icon="icon-edit" title="河段地图"
					url="slRiverLakeController.do?mapDetail1&paramType=${paramType}"
					funname="mapDetail1"></t:dgToolBar>
			</t:datagrid>
		</c:if>
	</div>
</div>
<script type="text/javascript">
	function formatAgeFun(tsUser,row,index){
		if ($.isEmptyObject(tsUser)){
			
		}else{
			alert(tsUser[0].userName);	
		}
				
		var str="原：";
		return str;
	}
	
	
	function addEntity(title, url, id) {
		add(title, url, 'entityList');
	}
	
	function openViewFile(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&code=' + rowData.id;
		}
		alert(url);
	}
	function updateEntity(title, url, id) {
		var rowData = $('#' + id).datagrid('getSelected');
		if (rowData) {
			url += '&code=' + rowData.id;
		}
		update(title, url, 'entityList');
	}
	function updateEntity1(id) {
		var cgFormId=id;
		var doctype="archives_doc";
		updateEntity11(id,doctype);
	}
	function updateEntity2(id) {
		var cgFormId=id;
		var doctype="policy_doc";
		updateEntity11(id,doctype);
	}
	function updateEntity3(id) {
		var cgFormId=id;
		var doctype="public_signs_doc";
		updateEntity11(id,doctype);
	}
	function updateEntity11(cgFormId,doctype) {
		$.ajax({
	  		   type: "post",
	  		   url: "multiUploadController.do?getFiles&id=" +  cgFormId,
	  		   success: function(data){
	  			 var arrayFileObj = jQuery.parseJSON(data).obj;
	  			 var bfind=false;
	  			$.each(arrayFileObj,function(n,file){
	  				var fieldName = file.field.toLowerCase();
	  				if (fieldName==doctype){
	  					bfind=true;
	  					openwindow("预览","commonController.do?openViewFile&fileid=" + file.fileKey + "&subclassname=com.saili.hzz.web.cgform.entity.upload.CgUploadEntity","fList",700,500);
	  				}
	  			 });
	  			if (!bfind){
	  				tip("没有找到资源!",1);
	  			}
	  		   }
	  		});		
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