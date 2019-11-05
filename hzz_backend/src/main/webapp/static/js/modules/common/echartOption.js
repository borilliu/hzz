/**
 *
 * @author: whuab_mc
 * @date: 2019-09-11 13:57:13:57
 * @version: V1.0
 */
function EachartOption() {
    return {
        lineOption: lineOption,
        barOption: barOption,
        pieOption: pieOption
    }
}


function lineOption(title, xAxis, series) {
    // 指定图表的配置项和数据
    return {
        title: {
            text: title
        },
        tooltip: {},
        legend: {
            data: []
        },
        xAxis: {
            data: xAxis
        },
        yAxis: {},
        series: series
    };
}

function barOption(title, legend, xAxis, series) {
    // 指定图表的配置项和数据
    return {
        title: {
            text: title
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: legend
        },
        xAxis: {
            data: xAxis
        },
        yAxis: {},
        series: series
    };
}

function pieOption(title, xAxis, series) {

}

// +++++++++++++++++++

function xh_barOption() {
    return {
        color: ['#003366', '#006699', '#4cabce', '#e5323e'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['Forest', 'Steppe', 'Desert', 'Wetland']
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                axisTick: {show: false},
                position:'bottom',
                axisPointer: {
                    type: 'shadow'
                },
                axisTick:{
                    show:true
                },
                axisLabel:{
                    margin:10
                },
                data: ['2012', '2013', '2014', '2015', '2016']
            },
            {
                type: 'category',
                axisPointer: {
                    type: 'shadow'
                },
                position:'bottom',
                offset:0,
                axisLabel:{
                    margin:30,
                    fontWeight:'bolder',
                    fontSize: 14
                },
                axisTick:{
                    show:true,
                    length:80,
                    lineStyle:{
                        type:'dotted'
                    }
                },
                data: ['1月']
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: 'Forest',
                type: 'bar',
                barGap: 0,
                data: [320, 332, 301, 334, 390]
            },
            {
                name: 'Steppe',
                type: 'bar',
                data: [220, 182, 191, 234, 290]
            },
            {
                name: 'Desert',
                type: 'bar',
                data: [150, 232, 201, 154, 190]
            },
            {
                name: 'Wetland',
                type: 'bar',
                data: [98, 77, 101, 99, 40]
            }
        ]
    };
}