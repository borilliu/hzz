<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true" scroll="no">
    <div data-options="region:'center'" title="">
		<div id="tt" tabPosition="top" border=flase
			 style="width: 100%; height: 100%; margin: 0px; padding: 0px; overflow-x: hidden; width: auto;"
			 class="easyui-tabs" fit="true"></div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $.post("${ctx}/basicDataStats/listCityDivision.do", {}, function (res) {
            $.each(res, function (i, o) {
                var id = o.id;
                var icon = 'icon-user-set';
                var title = o.name;
                var url = "${ctx}/basicDataStats/goMain.do?divisionLevel=${divisionLevel}&divisionCode=" + o.code;
                var closable = 'false';
                addtt(title, url, id, icon, closable);
            })
            $("#tt").tabs("select", 0);
        }, 'json');
		$('#tt').tabs({
			onSelect: function (title) {
				debugger
				var p = $(this).tabs('getTab', title);
				var url = p.find('iframe').attr('src');
				p.find('iframe').attr('src', url);
			}
		});
    })

    function addtt(title, url, id, icon, closable) {
        $('#tt').tabs('add', {
            id: id,
            title: title,
            content: createFramett(id, url),
			selected: false,
            closable: closable = (closable == 'false') ? false : true,
            icon: icon
        });
    }

    function createFramett(id, url) {
        var s = '<iframe id="' + id + '" scrolling="yes" frameborder="0"  src="' + url + '" width="100%" height="100%"></iframe>';
        return s;
    }
</script>