<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery-webos,easyui,tools,DatePicker,autocomplete"></t:base>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 0px; border: 0px; overflow-x: hidden;">
        <div id="localList" class="easyui-accordion"
             style="padding-right: 15px; overflow-x: hidden; box-sizing: border-box;">
            <div title="本级基础数据统计" data-options="iconCls:'icon-ok'" style="overflow: auto; box-sizing: border-box;">
                <iframe id="mainList"
                        src="${ctx}/basicDataStats/goLocal.do?divisionCode=${divisionCode}&divisionLevel=${divisionLevel}"
                        frameborder="0"
                        height="16%" width="100%"></iframe>
            </div>
            </br>
        </div>
        <div id="allLevelList" class="easyui-accordion"
             style="padding-right: 15px; overflow-x: hidden; box-sizing: border-box;">
            <div title="市县基础数据统计信息" data-options="iconCls:'icon-ok'" style="overflow: auto; box-sizing: border-box;">
                <iframe id="customerList"
                        src="${ctx}/basicDataStats/goAllLevel.do?divisionCode=${divisionCode}&divisionLevel=${divisionLevel}"
                        frameborder="0" width="100%" height="69%"></iframe>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

</script>