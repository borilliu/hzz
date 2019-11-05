<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery-webos,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 0px; border: 0px">
        <t:datagrid name="hzStatsReport" checkbox="true" pagination="true" fitColumns="true" title="统计信息"
                    actionUrl="/hz/report/datagrid.do?datagrid&divisionCode=${divisionCode}" idField="id" queryMode="group">
            <t:dgCol title="id" field="id" hidden="true" width="140"></t:dgCol>
            <t:dgCol title="市州" field="areaName" width="150"></t:dgCol>
            <t:dgCol title="姓名" field="riverChiefName" width="80"></t:dgCol>
            <t:dgCol title="职务" field="dutyName" width="150"></t:dgCol>
            <t:dgCol title="河流名称" field="riverLakeNames" width="100"></t:dgCol>
            <t:dgCol title="上级河流名称" field="parentRiverLakeNames" width="100"></t:dgCol>
            <t:dgCol title="电话" field="phone" width="100"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

</script>