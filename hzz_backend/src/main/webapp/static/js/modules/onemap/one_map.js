//# sourceURL=one_map.js
/* 江苏省各市坐标
118.778074408,32.0572355018 江苏省-南京市
120.873800951,32.0146645408 江苏省-南通市
118.296893379,33.9520497337 江苏省-宿迁市
119.981861013,31.7713967447 江苏省-常州市
117.188106623,34.2715534311 江苏省-徐州市
119.427777551,32.4085052546 江苏省-扬州市
120.305455901,31.5700374519 江苏省-无锡市
119.919606016,32.4760532748 江苏省-泰州市
119.030186365,33.6065127393 江苏省-淮安市
120.148871818,33.3798618771 江苏省-盐城市
120.619907115,31.317987368 江苏省-苏州市
119.173872217,34.601548967 江苏省-连云港市
119.455835405,32.2044094436 江苏省-镇江市*/


var map;
var divisionCtrl;

//一河一档
function goArchives () {
    var src = "/" + $("#archives").attr("datasrc");
    if (src == "") {
        alert("暂未上传一河一档");
    } else {
        window.open(src);
    }
}

//一河一策
function goPolicy () {
    var src = "/" + $('#policy').attr("datasrc");
    if (src == "") {
        alert("暂未上传一河一策");
    } else {
        window.open(src);
    }
}


function init() {
    map = new BMap.Map("container");
    var point = new BMap.Point(118.778074408, 32.0572355018);
    map.centerAndZoom(point, 13);
    map.addControl(new BMap.NavigationControl());
    map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();


    // 创建控件
    divisionCtrl = new DivisionControl("division_select", map);
    DivisionControl.prototype.initialize(map)
    // 添加到地图当中
    map.addControl(divisionCtrl);
}

$(document).ready(function () {
    init();
});



