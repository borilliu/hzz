/**
 * 巡河统计
 * @returns {{init: *, refreshAreaChart: *, search: *, refreshAreaTable: *, refreshRiverChart: *, refreshRiverTable: *}}
 * @constructor
 */
function RiverPatrol() {
    /**
     * 全局变量
     * @type {{areaChart: *, riverChartOption: *, areaChartOption: *, riverChart: *}}
     */
    let global = {
        areaChart: undefined,
        areaChartOption: undefined,
        riverChart: undefined,
        riverChartOption: undefined,
        areaChartResults: undefined,
        moduleCode: $('#moduleCode').val()
    };


    /**
     * 初始化城市下拉框
     */
    function initDivistionSelect() {
        $.post('/slDivisionController/listCity.do', {}, function (res) {
            $.each(res, function (index, item) {
                $('#division').append(new Option(item.divisionName, item.divisionCode))
            });
        }, 'json');
    }

    /**
     * 地区巡河统计折线图
     */
    function initAreaChart() {
        let options = new EachartOption();
        let title = '地区巡河统计';
        let legend = [];
        let xAxis = [];
        let series = [];
        global.areaChartOption = options.barOption(title, legend, xAxis, series);
        // 基于准备好的dom，初始化echarts实例
        global.areaChart = echarts.init(document.getElementById('area_main'));
        // 使用刚指定的配置项和数据显示图表。
        global.areaChart.setOption(global.areaChartOption);
    }

    /**
     * 河长巡河统计折线图
     */
    function initRiverChart() {
        let options = new EachartOption();
        let title = '河长巡河统计';
        let legend = [];
        let xAxis = [];
        let series = [];
        global.riverChartOption = options.barOption(title, legend, xAxis, series);
        // 基于准备好的dom，初始化echarts实例
        global.riverChart = echarts.init(document.getElementById('river_main'));
        // 使用刚指定的配置项和数据显示图表。
        global.riverChart.setOption(global.riverChartOption);
    }

    /**
     * 初始化 地区巡河统计列表
     */
    function initAreaGrid() {
        let title = '地区巡河统计列表';
        let url = '/riverPatrolStats/' + global.moduleCode + '/grid.do';
        let params = {
            antype: 'area',
            'city.code': $('#city_division').val(),
            'province.code': $('#province_division').val(),
            month: $('#month').val()
        };
        let colNames = ['地区', '应巡人数', '已巡人数', '完成率（%）'];
        let colModel = [
            {name: 'areaName', index: 'areaName', align: "center"},
            {name: 'target', index: 'target', align: "center", sorttype: "float"},
            {name: 'value', index: 'value', align: "center", sorttype: "float"},
            {name: 'passRate', index: 'passRate', align: "center", sorttype: "float"}
        ];
        let option = new JqGridOption();
        let areaOption = option.statsServer(title, url, params, colNames, colModel);
        $("#area_table").jqGrid(areaOption);
    }

    /**
     * 初始化 河长巡河统计列表
     */
    function initRiverGrid() {
        let title = '河长巡河统计列表';
        let url = '/riverPatrolStats/' + global.moduleCode + '/grid.do';
        let params = {
            antype: 'river',
            'city.code': $('#city_division').val(),
            'province.code': $('#province_division').val(),
            month: $('#month').val()
        };
        let colNames = ['区县', '职务', '应巡人数', '已巡人数', '未巡人员', '完成率（%）'];
        let colModel = [
            {name: 'areaName', index: 'areaName', align: "center",
                //①给当前想合并的单元格设置id
                cellattr: function(rowId, tv, rawObject, cm, rdata) {
                    return 'id=\'areaName' + rowId + "\'";
                }},
            {name: 'standName', index: 'standName', align: "center"},
            {name: 'target', index: 'target', align: "center", sorttype: "float"},
            {name: 'value', index: 'value', align: "center", sorttype: "float"},
            {name: 'tax', index: 'tax', align: "center"},
            {name: 'passRate', index: 'passRate', align: "center", sorttype: "float"}
        ];
        let option = new JqGridOption();
        // let riverOption = option.statsLocal(title, colNames, colModel, data);
        let riverOption = option.statsServer(title, url, params, colNames, colModel);
        riverOption.gridComplete = function() {
            //②在gridComplete调用合并方法
            var gridName = "river_table";
            option.merger(gridName, 'areaName');
        }
        $("#river_table").jqGrid(riverOption);
    }

    /**
     * 刷新地区巡河统计表格
     */
    function refreshAreaTable() {
        $("#area_table").jqGrid("setGridParam", {
            postData: {
                antype: 'area',
                'city.code': $('#city_division').val(),
                'province.code': $('#province_division').val(),
                month: $('#month').val()
            }
        }).trigger("reloadGrid");
    }

    /**
     * 刷新河长巡河统计表格
     */
    function refreshRiverTable() {
        $("#river_table").jqGrid("setGridParam", {
            postData: {
                antype: 'river',
                'city.code': $('#city_division').val(),
                'province.code': $('#province_division').val(),
                month: $('#month').val()
            }
        }).trigger("reloadGrid");
    }

    /**
     * 刷新地区巡河统计折线图
     */
    function refreshAreaChart() {
        $.post('/riverPatrolStats/' + global.moduleCode + '/echarts.do', {
            antype: 'area',
            'city.code': $('#city_division').val(),
            'province.code': $('#province_division').val(),
            month: $('#month').val()
        }, function (res) {
                global.areaChartOption.legend.data = res.legend;
                global.areaChartOption.xAxis.data = res.xAxis;
                global.areaChartOption.yAxis.data = res.yAxis;
                global.areaChartOption.series = res.series;
                global.areaChart.setOption(global.areaChartOption);
        }, 'json')
    }

    /**
     * 刷新河长巡河统计折线图
     */
    function refreshRiverChart() {
        $.post('/riverPatrolStats/' + global.moduleCode + '/echarts.do', {
            antype: 'river',
            'city.code': $('#city_division').val(),
            'province.code': $('#province_division').val(),
            'month': $('#month').val()
        }, function (res) {
            global.riverChartOption.legend.data = res.legend;
            global.riverChartOption.xAxis.data = res.xAxis;
            global.riverChartOption.yAxis.data = res.yAxis;
            global.riverChartOption.series = res.series;
            global.riverChart.setOption(global.riverChartOption);
        }, 'json')
    }

    function toGridHtml(url) {
        window.location.href = url + $('#homeForm').serialize();
    }

    function toEchartHtml(url) {
        window.location.href = url + $('#homeForm').serialize();
    }

    /**
     * 初始化 巡河统计js对象
     */
    function init() {
        initAreaChart();
        initAreaGrid();

        initRiverGrid();
    }

    /**
     * 查询 巡河统计数据
     */
    function search() {
        refreshAreaTable();
        refreshAreaChart();

        refreshRiverTable();
    }


    return {
        initDivistionSelect: initDivistionSelect,

        initAreaChart: initAreaChart,
        initRiverChart: initRiverChart,
        initAreaGrid: initAreaGrid,
        initRiverGrid: initRiverGrid,

        refreshAreaTable: refreshAreaTable,
        refreshRiverTable: refreshRiverTable,
        refreshAreaChart: refreshAreaChart,
        refreshRiverChart: refreshRiverChart,

        toGridHtml: toGridHtml,
        toEchartHtml: toEchartHtml,

        init: init,
        search: search
    }
}

function toGridHtml(url, antype) {
    debugger
    window.location.href = url + "?antype=" + antype + "&" + decodeURIComponent($('#homeForm').serialize());
}

function toEchartHtml(url, antype) {
    debugger
    window.location.href = url + "?antype=" + antype + "&" + decodeURIComponent($('#homeForm').serialize());
}