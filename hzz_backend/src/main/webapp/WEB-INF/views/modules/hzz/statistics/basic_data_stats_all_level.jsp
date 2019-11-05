<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 0px; border: 0px">
        <t:datagrid name="allLevelDataGrid" checkbox="true"
                    pagination="true" fitColumns="false"
                    actionUrl="/basicDataStats/allLevelDataGrid.do?datagrid&divisionCode=${divisionCode}"
                    idField="id" fit="false">

            <t:dgCol title="id" field="id" hidden="true" width="140"></t:dgCol>
            <t:dgCol title="<b>地区</b>" field="division.name" rowspan="2" width="60"></t:dgCol>
            <c:if test="${divisionLevel == 'province'}">
                <t:dgCol title="<b>市级</b>" colspan="9"></t:dgCol>
                <t:dgCol title="<b>县级</b>" colspan="10"></t:dgCol>
                <t:dgCol title="<b>乡级</b>" colspan="10"></t:dgCol>
                <t:dgCol title="<b>村级</b>" colspan="10" newColumn="true"></t:dgCol>
            </c:if>
            <c:if test="${divisionLevel == 'city'}">
                <t:dgCol title="<b>县级</b>" colspan="9"></t:dgCol>
                <t:dgCol title="<b>乡级</b>" colspan="10"></t:dgCol>
                <t:dgCol title="<b>村级</b>" colspan="10" newColumn="true"></t:dgCol>
            </c:if>
            <c:if test="${divisionLevel == 'province'}">
                <t:dgCol title="地区" field="cityLevel.areaCount" hidden="true" width="50"></t:dgCol>
                <t:dgCol title="第一总河长" field="cityLevel.firstPresidentHzCount" width="80"></t:dgCol>
                <t:dgCol title="总河长" field="cityLevel.presidentHzCount" width="60"></t:dgCol>
                <t:dgCol title="副总河长" field="cityLevel.vicePresidentHzCount" width="70"></t:dgCol>
                <t:dgCol title="河长" field="cityLevel.hzCount" width="50"></t:dgCol>
                <t:dgCol title="副河长" field="cityLevel.viceHzCount" width="60"></t:dgCol>
                <t:dgCol title="河流" field="cityLevel.riverCount" width="50"></t:dgCol>
                <t:dgCol title="湖泊" field="cityLevel.lakeCount" width="50"></t:dgCol>
                <t:dgCol title="河段" field="cityLevel.reachCount" width="50"></t:dgCol>
                <t:dgCol title="水库" field="cityLevel.reservoirCount" width="50"></t:dgCol>
            </c:if>


            <c:if test="${divisionLevel == 'province'}">
                <t:dgCol title="地区" field="countyLevel.areaCount" width="50"></t:dgCol>
            </c:if>
            <c:if test="${divisionLevel == 'city'}">
                <t:dgCol title="地区" field="countyLevel.areaCount" hidden="true" width="50"></t:dgCol>
            </c:if>
            <t:dgCol title="第一总河长" field="countyLevel.firstPresidentHzCount" width="80"></t:dgCol>
            <t:dgCol title="总河长" field="countyLevel.presidentHzCount" width="60"></t:dgCol>
            <t:dgCol title="副总河长" field="countyLevel.vicePresidentHzCount" width="70"></t:dgCol>
            <t:dgCol title="河长" field="countyLevel.hzCount" width="50"></t:dgCol>
            <t:dgCol title="副河长" field="countyLevel.viceHzCount" width="60"></t:dgCol>
            <t:dgCol title="河流" field="countyLevel.riverCount" width="50"></t:dgCol>
            <t:dgCol title="湖泊" field="countyLevel.lakeCount" width="50"></t:dgCol>
            <t:dgCol title="河段" field="countyLevel.reachCount" width="50"></t:dgCol>
            <t:dgCol title="水库" field="countyLevel.reservoirCount" width="50"></t:dgCol>


            <t:dgCol title="地区" field="townLevel.areaCount" width="50"></t:dgCol>
            <t:dgCol title="第一总河长" field="townLevel.firstPresidentHzCount" width="80"></t:dgCol>
            <t:dgCol title="总河长" field="townLevel.presidentHzCount" width="60"></t:dgCol>
            <t:dgCol title="副总河长" field="townLevel.vicePresidentHzCount" width="70"></t:dgCol>
            <t:dgCol title="河长" field="townLevel.hzCount" width="50"></t:dgCol>
            <t:dgCol title="副河长" field="townLevel.viceHzCount" width="60"></t:dgCol>
            <t:dgCol title="河流" field="townLevel.riverCount" width="50"></t:dgCol>
            <t:dgCol title="湖泊" field="townLevel.lakeCount" width="50"></t:dgCol>
            <t:dgCol title="河段" field="townLevel.reachCount" width="50"></t:dgCol>
            <t:dgCol title="水库" field="townLevel.reservoirCount" width="50"></t:dgCol>


            <t:dgCol title="地区" field="villageLevel.areaCount" width="50"></t:dgCol>
            <t:dgCol title="第一总河长" field="villageLevel.firstPresidentHzCount" width="80"></t:dgCol>
            <t:dgCol title="总河长" field="villageLevel.presidentHzCount" width="60"></t:dgCol>
            <t:dgCol title="副总河长" field="villageLevel.vicePresidentHzCount" width="70"></t:dgCol>
            <t:dgCol title="河长" field="villageLevel.hzCount" width="50"></t:dgCol>
            <t:dgCol title="副河长" field="villageLevel.viceHzCount" width="60"></t:dgCol>
            <t:dgCol title="河流" field="villageLevel.riverCount" width="50"></t:dgCol>
            <t:dgCol title="湖泊" field="villageLevel.lakeCount" width="50"></t:dgCol>
            <t:dgCol title="河段" field="villageLevel.reachCount" width="50"></t:dgCol>
            <t:dgCol title="水库" field="villageLevel.reservoirCount" width="50"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

</script>