/**
 * 自定义控件： 行政区划 控件
 * @author: whuab_mc
 * @date: 2019-10-23 10:59:10:59
 * @version: V1.0
 */
function DivisionControl(id, map) {
    // 默认停靠位置和偏移量
    this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
    this.defaultOffset = new BMap.Size(10, 10);

    var blist = [];
    var districtLoading = 0;

    // 通过JavaScript的prototype属性继承于BMap.Control
    DivisionControl.prototype = new BMap.Control();
    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
    DivisionControl.prototype.initialize = function(map) {
        var control = document.createElement("div");
        $.post("/oneMap/loadControl.do", {}, function (res) {
            control.innerHTML = res;
            map.getContainer().appendChild(control);
            divisionSelect();
            // 将DOM元素返回
            return control;
        }, 'html');
    };


    /**
     * 在地图绘制河湖
     * @param {} params
     */
    function drawRiverLake(params) {
        var riverLakeLinePoints = params.riverLakeLinePoints || new Array();
        var points = new Array();
        $.each(riverLakeLinePoints, function (i, o) {
            points.push(new BMap.Point(o.lng, o.lat));
        });
        var polylineOpts = {
            strokeColor: "#F7080D",
            strokeWeight: 3,
            strokeOpacity: 0.5
        };
        map.addOverlay(new BMap.Polyline(points, polylineOpts));

        var labelName = params.labelName;
        var publicSignsLng = params.publicSignsLng;
        var publicSignsLat = params.publicSignsLat;
        var lableOpts = {
            position: new BMap.Point(publicSignsLng, publicSignsLat),    // 指定文本标注所在的地理位置
        }
        var label = new BMap.Label(labelName, lableOpts);  // 创建文本标注对象
        label.setStyle({
            color: "red",
            fontSize: "16px",
            height: "20px",
            lineHeight: "20px",
            fontFamily: "微软雅黑"
        });
        label.addEventListener("click", function () {
            var point = label.getPosition();
            var postParams = {
                publicSignsLng: point.lng,
                publicSignsLat: point.lat
            }

            $.post("/oneMap/loadInfoWindows.do", postParams, function (res) {
                var infoWindowOpts = {
                    width: 550,     // 信息窗口宽度
                    height: 450,     // 信息窗口高度
                    title: "</br>"  // 信息窗口标题
                }
                var html = res;
                var infoWindow = new BMap.InfoWindow(html, infoWindowOpts);  // 创建信息窗口对象

                map.openInfoWindow(infoWindow, point);
            }, 'html')
        });
        map.addOverlay(label);
    }

    function listRiverLakeByDivision(divisonCode) {
        $.post("/oneMap/listDrawRiverLakeData.do", {
            'code': divisonCode
        }, function (res) {
            $.each(res, function (i, o) {
                var params = {};
                params.riverLakeLinePoints = o.riverLakeLinePoints;
                params.labelName = o.labelName;
                params.publicSignsLng = o.publicSignsLng;
                params.publicSignsLat = o.publicSignsLat;
                drawRiverLake(params);
            });

        }, 'json')
    }



    function divisionSelect() {

        $('#' + id).on("change", function (o) {
            var divisionCode = $(this).val()
            getBoundary(divisionCode);
            listRiverLakeByDivision(divisionCode);
        });

        $.post("/slDivisionController/listByParentCode.do", {
            divisionCode: "320000000000"
        }, function (res) {
            var results = new Array();
            var datas = res || new Array();
            $.each(datas, function (i, o) {
                var result = {
                    "id": o.code,
                    "text": o.name,
                };
                results.push(result);
            });
            $('#' + id).select2({
                data: results
            });

            $('#' + id).val($('select').children('option').eq(2).val());
            $('#' + id).change();
        }, 'json');
    }

    function getBoundary(divisionCode) {
        $.post('/slDivisionController/listByParentCode.do', {
            divisionCode: divisionCode
        }, function (res) {
            map.clearOverlays();
            // 清空数组
            blist = [];
            districtLoading = 0;
            $.each(res, function (i, o) {
                if (o.name != '市辖区') {
                    //设置指定显示区域
                    addDistrict("江苏省" + o.parent.name + o.name);
                }
            });
        }, 'json');
    }


    function addDistrict(districtName) {
        //使用计数器来控制加载过程
        districtLoading++;
        var bdary = new BMap.Boundary();
        bdary.get(districtName, function (rs) { //获取行政区域
            var count = rs.boundaries.length; //行政区域的点有多少个
            if (count === 0) {
                alert('未能获取行政区域： ' + districtName);
                districtLoading--;
                return;
            }
            for (var i = 0; i < count; i++) {
                // 随机生成颜色
                var red = parseInt(Math.random() * 257).toString(16);
                var blue = parseInt(Math.random() * 257).toString(16);
                var green = parseInt(Math.random() * 257).toString(16);
                var color = '#' + red + blue + green;

                blist.push({points: rs.boundaries[i], name: districtName, color: color});
            }
            ;
            //加载完成区域点后计数器-1
            districtLoading--;
            if (districtLoading == 0) {
                //全加载完成后画端点
                drawBoundary();
            }
        });
    }

    function drawBoundary() {
        //包含所有区域的点数组
        var pointArray = [];

        /*画遮蔽层的相关方法
         *思路: 首先在中国地图最外画一圈，圈住理论上所有的中国领土，然后再将每个闭合区域合并进来，并全部连到西北角。
         *      这样就做出了一个经过多次西北角的闭合多边形*/
        //定义中国东南西北端点，作为第一层
        var pNW = {lat: 59.0, lng: 73.0}
        var pNE = {lat: 59.0, lng: 136.0}
        var pSE = {lat: 3.0, lng: 136.0}
        var pSW = {lat: 3.0, lng: 73.0}

        //向数组中添加一次闭合多边形，并将西北角再加一次作为之后画闭合区域的起点
        var pArray = [];
        pArray.push(pNW);
        pArray.push(pSW);
        pArray.push(pSE);
        pArray.push(pNE);
        pArray.push(pNW);
        //循环添加各闭合区域
        for (var i = 0; i < blist.length; i++) {
           /* //添加显示用标签层
            var label = new BMap.Label(blist[i].name, {offset: new BMap.Size(20, -10)});
            label.hide();
            map.addOverlay(label);*/

            //添加多边形层并显示
            var ply = new BMap.Polygon(blist[i].points, {
                strokeWeight: 1,
                strokeColor: "#5185E6",
                fillOpacity: 0.3,
                fillColor: blist[i].color
            }); //建立多边形覆盖物
            ply.name = blist[i].name;
            /*ply.label = label;*/
            map.addOverlay(ply);

            //将点增加到视野范围内
            var path = ply.getPath();
            pointArray = pointArray.concat(path);
            //将闭合区域加到遮蔽层上，每次添加完后要再加一次西北角作为下次添加的起点和最后一次的终点
            pArray = pArray.concat(path);
            pArray.push(pArray[0]);
        }

        //限定显示区域，需要引用api库
        var boundply = new BMap.Polygon(pointArray);
        BMapLib.AreaRestriction.setBounds(map, boundply.getBounds());
        map.setViewport(pointArray); //调整视野

        //添加遮蔽层(描边线条，遮罩层颜色)
        var plyall = new BMap.Polygon(pArray, {
            strokeOpacity: 0.0000001,
            strokeColor: "#ffffff",
            strokeWeight: 0.00001,
            fillColor: "#ffffff",
            fillOpacity: 0.7
        }); //建立多边形覆盖物
        map.addOverlay(plyall);
    }

    return {
        listRiverLakeByDivision: listRiverLakeByDivision
    }
}