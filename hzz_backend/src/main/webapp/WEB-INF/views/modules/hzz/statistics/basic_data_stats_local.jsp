<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery-webos,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 0px; border: 0px">
        <t:datagrid name="localDataGrid" checkbox="true" pagination="true" fitColumns="true"
                    actionUrl="/basicDataStats/localDataGrid.do?datagrid&divisionCode=${divisionCode}" idField="id" queryMode="group">
            <t:dgCol title="id" field="id" hidden="true" width="140"></t:dgCol>
            <t:dgCol title="第一总河长人数" field="firstPresidentHzCount" width="150"></t:dgCol>
            <t:dgCol title="总河长人数" field="presidentHzCount" width="80"></t:dgCol>
            <t:dgCol title="副总河长人数" field="vicePresidentHzCount" width="150"></t:dgCol>
            <t:dgCol title="河长人数" field="hzCount" width="100"></t:dgCol>
            <t:dgCol title="副河长人数" field="viceHzCount" width="100"></t:dgCol>
            <t:dgCol title="河流数量" field="riverCount" width="100"></t:dgCol>
            <t:dgCol title="湖泊数量" field="lakeCount" width="100"></t:dgCol>
            <t:dgCol title="河段数量" field="reachCount" width="100"></t:dgCol>
            <t:dgCol title="水库数量" field="reservoirCount" width="100"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

</script>