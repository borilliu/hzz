package com.saili.hzz.backend.controller.statistics;

import com.google.common.collect.Lists;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.enums.DivisionLevelEnum;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.backend.service.statistics.BasicDataStatsService;
import com.saili.hzz.core.vo.BaseBasicDataStatsVo;
import com.saili.hzz.core.vo.LocalBasicDataStatsVo;
import com.saili.hzz.core.constant.Constants;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.tag.core.easyui.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-07 11:50:11:50
 * @version: V1.0
 */
@Controller
@RequestMapping("/basicDataStats/")
public class BasicDataStatsController {
    Logger logger = LoggerFactory.getLogger(BasicDataStatsController.class);

    @Autowired
    private BasicDataStatsService basicDataStatsService;
    @Autowired
    private DivisionService divisionService;

    @RequestMapping("goHome")
    public String goHome(String divisionLevel, Model model) {
        model.addAttribute("divisionLevel", divisionLevel);

        if (DivisionLevelEnum.CITY_LEVEL.getCode().equals(divisionLevel)) {
            List<TSLDivisionEntity> divisionList = divisionService.listByParentCode(Constants.ROOT_DIVISION_CODE);
            model.addAttribute("divisionList", divisionList);
            return "modules/hzz/statistics/basic_data_stats_home";
        }

        model.addAttribute("divisionCode", Constants.ROOT_DIVISION_CODE);
        return "modules/hzz/statistics/basic_data_stats_main";
    }

    @RequestMapping(value= "goMain")
    public String goMain(String divisionCode, String divisionLevel, Model model) {
        model.addAttribute("divisionCode", divisionCode);
        model.addAttribute("divisionLevel", divisionLevel);
        return "modules/hzz/statistics/basic_data_stats_main";
    }

    @RequestMapping("goLocal")
    public String goLocal(String divisionLevel, String divisionCode, Model model) {
        model.addAttribute("divisionLevel", divisionLevel);
        model.addAttribute("divisionCode", divisionCode);
        return "modules/hzz/statistics/basic_data_stats_local";
    }

    @RequestMapping("goAllLevel")
    public String goAllLevel(String divisionLevel, String divisionCode, Model model) {
        model.addAttribute("divisionLevel", divisionLevel);
        model.addAttribute("divisionCode", divisionCode);
        return "modules/hzz/statistics/basic_data_stats_all_level";
    }

    @RequestMapping("listCityDivision")
    @ResponseBody
    public List<TSLDivisionEntity> listCityDivision() {
        return divisionService.listByParentCode(Constants.ROOT_DIVISION_CODE);
    }

    @RequestMapping(value = "localDataGrid", params = "datagrid")
//    @RequestMapping("localDataGrid")
    public void localDataGrid(String divisionCode, DataGrid dataGrid, HttpServletResponse response) {
        List<LocalBasicDataStatsVo> results = Lists.newArrayList();
        LocalBasicDataStatsVo result = basicDataStatsService.getLocalBasicDataStatsVo(divisionCode);
        results.add(result);
        dataGrid.setResults(results);
        dataGrid.setTotal(results.size());
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(value = "allLevelDataGrid", params = "datagrid")
//    @RequestMapping("allLevelDataGrid")
    public void allLevelDataGrid(String divisionCode, DataGrid dataGrid, HttpServletResponse response) {
        List<BaseBasicDataStatsVo> results = basicDataStatsService.listBasicDataStatsVo(divisionCode);
        dataGrid.setResults(results);
        dataGrid.setTotal(results.size());
        TagUtil.datagrid(response, dataGrid);
    }
}
