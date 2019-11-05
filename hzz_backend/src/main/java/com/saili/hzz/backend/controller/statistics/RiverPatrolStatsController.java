package com.saili.hzz.backend.controller.statistics;

import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.backend.service.statistics.RiverPatrolStatsService;
import com.saili.hzz.core.vo.AreaRiverPatrolStatsEChartsVo;
import com.saili.hzz.core.vo.HzProtalStatsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/***
 * @Title: 巡河统计 controller
 * @Description:
 * @author: whuab_mc
 * @date: 2019-09-11 10:52:10:52
 * @version: V1.0
 */
@Controller
@RequestMapping("riverPatrolStats/")
public class RiverPatrolStatsController {
    Logger logger = LoggerFactory.getLogger(RiverPatrolStatsController.class);

    @Autowired
    private RiverPatrolStatsService countyRiverPatrolStatsService;
    @Autowired
    private RiverPatrolStatsService cityRiverPatrolStatsService;

    /**
     * 跳转县级巡河统计首页
     * @return
     */
    @RequestMapping("home")
    public String home(@ModelAttribute(value = "homeForm") StatsForm form) {
        return "modules/hzz/statistics/hz_patrol_home";
    }

    /**
     * 跳转县级巡河统计首页
     * @return
     */
    @RequestMapping("county/home")
    public String countyHome(@ModelAttribute(value = "homeForm") StatsForm form) {
        return "modules/hzz/statistics/hz_patrol_home";
    }

    /**
     * 跳转市级巡河统计首页
     * @return
     */
    @RequestMapping("city/home")
    public String cityHome(@ModelAttribute(value = "homeForm") StatsForm form) {
        return "modules/hzz/statistics/hz_patrol_home";
    }

    /**
     * 跳转县级巡河统计首页
     * @return
     */
    @RequestMapping("page/county/echart")
    public String countyEchart(@ModelAttribute(value = "echartForm") StatsForm form, Model model) {
        return "modules/hzz/statistics/hz_patrol_echart";
    }

    /**
     * 跳转县级巡河统计首页
     * @return
     */
    @RequestMapping("page/county/grid")
    public String countyTable(@ModelAttribute(value = "tableForm") StatsForm tableForm, Model model) {
        return "modules/hzz/statistics/hz_patrol_table";
    }

    /**
     * 县级地区巡河数据数据表格
     * @param form 查询条件
     * @return
     */
    @RequestMapping("county/grid")
    @ResponseBody
    public List<HzProtalStatsVo> countyAreaDataGrid(StatsForm form) {
        return countyRiverPatrolStatsService.listGrid(form);
    }

    /**
     * 县级地区巡河数据数据图表
     * @param form 查询条件
     * @return
     */
    @RequestMapping("county/echarts")
    @ResponseBody
    public AreaRiverPatrolStatsEChartsVo countyAreaChart(StatsForm form) {
        return countyRiverPatrolStatsService.listECharts(form);
    }

    /**
     * 市级地区巡河数据数据表格
     * @param form 查询条件
     * @return
     */
    @RequestMapping("city/grid")
    @ResponseBody
    public List<HzProtalStatsVo> cityAreaDataGrid(StatsForm form) {
        return cityRiverPatrolStatsService.listGrid(form);
    }

    /**
     * 市级地区巡河数据数据图表
     * @param form 查询条件
     * @return
     */
    @RequestMapping("city/echarts")
    @ResponseBody
    public AreaRiverPatrolStatsEChartsVo cityAreaChart(StatsForm form) {
        return cityRiverPatrolStatsService.listECharts(form);
    }
}
