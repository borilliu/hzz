//# sourceURL=hz_report_forms.js
/**
 *
 * @author: whuab_mc
 * @date: 2019-09-30 14:59:14:59
 * @version: V1.0
 */

var flag = true;
var TimeFn = null;

//beforeDblClick事件
function beforeDbl() {
    flag = false;
    return true;
}

//加载树
var orgTree;

function loadTree() {
    var zNodes;
    jQuery.ajax({
        async: false,
        cache: false,
        type: 'POST',
        dataType: "json",
        url: '/hz/report/getAreaTree.do',//请求的action路径
        error: function () {//请求失败处理函数
            alert('请求失败');
        },
        success: function (data) { //请求成功后处理函数。
            zNodes = data.obj;   //把后台封装好的简单Json格式赋给zNodes
        }
    });
    var ztreeCreator = new ZtreeCreator('areaTree', "/hz/report/getAreaTree.do", zNodes)
        .setCallback({
            onClick: zTreeOnLeftClick,
            onRightClick: zTreeOnRightClick,
            onDblClick: zTreeOnDblClick,
            beforeDblClick: beforeDbl
        })
        .initZtree({}, function (treeObj) {
            // $.fn.zTree.getZTreeObj("areaTree").expandAll(false);
            orgTree = treeObj
        });
};

//左击
function zTreeOnLeftClick(event, treeId, treeNode) {
    var selectNode = getSelectNode();
    var level = selectNode.level;
    flag = true;
    clearTimeout(TimeFn);
    setTimeout(function () {
        if (flag) {
            curSelectNode = treeNode;
            var parentId = treeNode.id;
            var url = "/hz/report/page/main.do?divisionCode=" + selectNode.id;
            $("#listFrame").attr("src", url);
        }
    }, 301);
};

/**
 * 树右击事件
 */
function zTreeOnRightClick(e, treeId, treeNode) {

};

//双击事件
function zTreeOnDblClick(event, treeId, treeNode) {
    var selectNode = getSelectNode();
    var level = selectNode.level;
    curSelectNode = treeNode;
    var url = "departAuthGroupController.do?departRoleAuthGroupRel&id=" + selectNode.id;
    if (level == 0) {
        url = "departAuthGroupController.do?showDepartRoleAuth&id=" + selectNode.id;
    }
    $("#listFrame").attr("src", url);
}

//选择资源节点。
function getSelectNode() {
    orgTree = $.fn.zTree.getZTreeObj("areaTree");
    var nodes = orgTree.getSelectedNodes();
    var node = nodes[0];
    return node;
};

//查询供应商信息
function selectSupplier() {
    var supplier = $("#supplier").val();
    if (supplier == null || supplier.trim() == "") {
        loadTree();
    } else {
        jQuery.ajax({
            type: 'POST',
            dataType: "json",
            url: "departAuthGroupController.do?selectSupplier&supplier=" + supplier,
            error: function () {
                tip('查询供应商信息失败');
            },
            success: function (data) {
                var ztreeCreator = new ZtreeCreator('areaTree', "departAuthGroupController.do?getMainTreeData", data)
                    .setCallback({
                        onClick: zTreeOnLeftClick,
                        onRightClick: zTreeOnRightClick,
                        onDblClick: zTreeOnDblClick,
                        beforeDblClick: beforeDbl
                    })
                    .initZtree({}, function (treeObj) {
                        orgTree = treeObj
                    });
            }
        });
    }
}

$(document).ready(function () {
    loadTree();
});