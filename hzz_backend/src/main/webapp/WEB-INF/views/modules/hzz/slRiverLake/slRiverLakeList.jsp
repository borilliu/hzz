<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div style="height: 90%;">
	<c:if test="${paramType == '10'}">
		<t:tabs id="tabsOne" iframe="true" tabPosition="top" fit="false">
			<t:tab href="slRiverLakeController.do?listEntity&paramType=a"
				icon="icon-tip" title="河流" id="tab1" closable="false"></t:tab>
			<t:tab href="slRiverLakeController.do?listEntity&paramType=b"
				icon="icon-tip" title="湖泊" id="tab2" closable="false"></t:tab>
			<t:tab href="slRiverLakeController.do?listEntity&paramType=c"
				icon="icon-tip" title="河段" id="tab2" closable="false"></t:tab>
			<t:tab href="slRiverLakeController.do?listEntity&paramType=d"
				icon="icon-tip" title="水库" id="tab2" closable="false"></t:tab>
		</t:tabs>
	</c:if>
	<c:if test="${paramType == '11'}">
		<t:tabs id="tabsOne" iframe="true" tabPosition="top" fit="false">
			<t:tab href="slRiverLakeController.do?listEntity&paramType=a1"
				icon="icon-tip" title="取水口" id="tab1" closable="false"></t:tab>
			<t:tab href="slRiverLakeController.do?listEntity&paramType=b1"
				icon="icon-tip" title="水功能区" id="tab2" closable="false"></t:tab>
			<t:tab href="slRiverLakeController.do?listEntity&paramType=c1"
				icon="icon-tip" title="测站" id="tab2" closable="false"></t:tab>
			<t:tab href="slRiverLakeController.do?listEntity&paramType=d1"
				icon="icon-tip" title="排污口" id="tab2" closable="false"></t:tab>
		</t:tabs>
	</c:if>
</div>

