/**
 *
 * @author: whuab_mc
 * @date: 2019-09-11 14:03:14:03
 * @version: V1.0
 */

function JqGridOption() {
    return {
        statsLocal: statsLocal,
        statsServer: statsServer,
        merger: Merger
    }
}

function statsServer(title, url, params, colNames, colModel) {
    return {
        styleUI: 'Bootstrap',
        caption: title,
        datatype: "local",
        height: 'auto',
        // width:"100%",
        autowidth:true,
        // scroll : false,
        shrinkToFit: false,
        viewrecords : true,
        mtype: "POST",
        datatype: "json",
        url: url,
        postData: params,
        colNames: colNames,
        colModel: colModel,
        gridComplete: function () {

        }
    }
}

function statsLocal(title, colNames, colModel, data) {
    return {
        caption: title,
        datatype: "local",
        height: '100%',
        autowidth: false,
        shrinkToFit: false,
        colNames: colNames,
        colModel: colModel,
        data: data
    }
}



//公共调用方法
function Merger(gridName, CellName) {
    //得到显示到界面的id集合
    var mya = $("#" + gridName + "").getDataIDs();
    //当前显示多少条
    var length = mya.length;
    for (var i = 0; i < length; i++) {
        //从上到下获取一条信息
        var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);
        //定义合并行数
        var rowSpanTaxCount = 1;
        for (j = i + 1; j <= length; j++) {
            //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
            var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);
            if (before[CellName] == end[CellName]) {
                rowSpanTaxCount++;
                $("#" + gridName + "").setCell(mya[j], CellName, '', { display: 'none' });
            } else {
                rowSpanTaxCount = 1;
                break;
            }
            $("#" + CellName + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
        }
    }
}