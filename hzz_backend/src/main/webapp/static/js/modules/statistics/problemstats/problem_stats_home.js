function resizeDivContainer() {
    var windowH = $(window).height();
    var windowW = $(window).width();

    var h = (windowH - 260) / 2;

    $("#div1").height(h);
    $("#div2").height(h);

    var w = (windowW - 30) / 10;
    $("#div1_1").width(w * 4);
    $("#div1_2").width(w * 6);
}

function statsProblemIndex(datas) {
    $('#statsTypeTotal').html(datas && datas.statsTypeTotal);
    $('#eventTotal').html(datas && datas.eventTotal);
    $('#processedTotal').html(datas && datas.processedTotal);
    $('#pendingTotal').html(datas && datas.pendingTotal);
}

function statsProblemRatio(datas) {
    var dom = document.getElementById("pratolRatioEchart");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        title: {
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"

        },
        legend: {
            type: 'scroll',
            left: '50',
            data: datas && datas.legendData
        },
        series: [
            {
                name: '类型',
                type: 'pie',
                radius: '60%',
                center: ['50%', '60%'],
                data: datas && datas.seriesData,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]


    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}

function statsProblemExecute(datas) {
    var dom = document.getElementById("proceesedEchart");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    app.title = '折柱混合';

    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend: {
            top: '2%',
            data: ['已处理', '待处理', '完成率']
        },
        xAxis: [
            {
                axisLabel: {rotate: 50},
                data: datas && datas.legendData,
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        grid: {
            left: '3%',
            right: '4%',
            bottom: '7%',
            top: '25%',
            containLabel: true
        },
        yAxis: [
            {
                type: 'value',
                name: '事件',
                min: 0,
                max: 60,
                interval: 20,
                axisLabel: {
                    formatter: '{value} 件'
                }
            },
            {
                type: 'value',
                name: '完成率',
                min: 0,
                max: 120,
                interval: 40,
                axisLabel: {
                    formatter: '{value} %'
                }
            }
        ],
        series: [
            {
                name: '已处理',
                type: 'bar',
                data: datas && datas.pendingData
            },
            {
                name: '待处理',
                type: 'bar',
                data: datas && datas.processedData
            },
            {
                name: '完成率',
                type: 'line',
                yAxisIndex: 1,
                data: datas && datas.completeRateData
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}

function statsYearProblemHandled(datas) {
    var dom = document.getElementById("yearEchart");
    var myChart = echarts.init(dom);

    var seriesData = new Array();
    var series = new Array();
    if (datas && datas.series) {
        series = datas.series;
    }
    $.each(series, function (index, data) {
        var serie = {
            name: data.name,
            type: 'line',
            data: data.data
        };
        seriesData.push(serie);
    })

    var app = {};
    option = null;
    option = {
        title: {},
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            type: 'scroll',
            data: datas && datas.legendData
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: datas && datas.xAxisData
        },
        yAxis: {
            type: 'value',
            name: '次数',
            min: 0,
            max: 60,
            interval: 20,
            axisLabel: {
                formatter: '{value} 次'
            }
        },
        grid: {
            top: '27%',
            left: '2%',
            right: '2%',
            bottom: '10%',
            containLabel: true
        },
        series: seriesData
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}

function listArea(parentCode) {
    debugger
    if (!parentCode) {
        parentCode = '320000000000';
    }
    $('#areacode').val(parentCode);
    $.post('/slDivisionController/listDivision.do', {
        parentCode: parentCode
    }, function (res) {
        if (!res.parent || res.parent.code == '0') {
            var html = '<div style="padding-top:10px; float: left; "><span>当前地区：</span>' +
                '<button type="button" data-loading-text="Loading..." class="btn btn-sm btn-Info" ' +
                'style="padding:5px 10px;font-size:14px; background-color:#EEE0E5;" ' +
                'onclick="listArea(\'' + res.code + '\')">' + res.name +
                '</button></div>';
            $('#divArea').html(html);
        } else {
            var html =
                '<div style="padding-top:10px; "><span>当前地区：</span>' +
                '<button type="button" data-loading-text="Loading..." class="btn btn-sm btn-Info"' +
                'style="padding:5px 10px;font-size:14px;margin-ridght:15px; background-color:#EEE0E5;"' +
                'onclick="listArea(\'' + res.code + '\')">' + res.name +
                '</button>' +
                '<span>上一级地区：</span>' +
                '<button type="button" data-loading-text="Loading..." class="btn btn-sm btn-Info"' +
                'style="padding:5px 10px;font-size:14px; background-color:#EEE0E5;"' +
                'onclick="listArea(\'' + res.parent.code + '\')">' + res.parent.name +
                '</button></div>';
            $('#divArea').html(html);
        }

        var childrenList = new Array();
        if (res.childrenList) {
            childrenList = res.childrenList;
        }
        var tblContent = '<div style="float:left;">';
        $.each(childrenList, function (index, obj) {
            tblContent +=
                '<div style=" float: left;margin:1px 5px;">' +
                '<button type="button" data-loading-text="Loading..." class="btn btn- sm btn- Info"' +
                'style="background-color:#FFF;border:1px solid #eee;"' +
                'onclick="listArea(\'' + obj.code + '\')">' + obj.name +
                '</button></div>';
        });
        tblContent += '</div>';
        $('#tblContent').html(tblContent);
        debugger
        search();
    }, 'json');
}

function search() {
    debugger
    $.post('/stats/problem/getStatsData.do', {
        statsType: $('#statsType').val(),
        startDay: $('#startDay').val(),
        endDay: $('#endDay').val(),
        'area.code': $('#areacode').val()
    }, function (res) {
        //地区
        $("#areaname").html(res.area && res.area.name);
        //查询日期
        $("#searchdate").html(res.searchDate);
        // 各指标数量统计
        statsProblemIndex(res.problemIndexStatsDo);
        // 巡河问题占比统计
        statsProblemRatio(res.problemRatioStatsDo);
        // 巡河处理完成情况统计
        statsProblemExecute(res.problemExecuteStatsDo);
        //近一年巡河问题处理完成次数变化趋势图
        statsYearProblemHandled(res.yearProblemHandledStatsDo);
    }, 'json');
}

function searBycurYear() {
    var startDate = new Date();
    startDate.setMonth(0);
    startDate.setDate(1)
    $("#startDay").val(dateFormat(startDate, 'yyyy-MM-dd'));
    $("#endDay").val(dateFormat(new Date(), 'yyyy-MM-dd'));
    search();
}

function searBycurMonth() {
    var startDate = new Date();
    startDate.setDate(1)
    $("#startDay").val(dateFormat(startDate, 'yyyy-MM-dd'));
    $("#endDay").val(dateFormat(new Date(), 'yyyy-MM-dd'));
    search();
}

function init() {
    $("#startDay").val(dateFormat(addDate(new Date(), -30), 'yyyy-MM-dd'))
    $("#endDay").val(dateFormat(new Date(), 'yyyy-MM-dd'))
    $('#searchbtn').click(search);
    $('#searBycurYearbtn').click(searBycurYear);
    $('#searBycurMonthbtn').click(searBycurMonth);

    listArea();
    search();
}

$(window).resize(function () {
    resizeDivContainer();
});

$(document).ready(function () {
    resizeDivContainer();
    init();
});
