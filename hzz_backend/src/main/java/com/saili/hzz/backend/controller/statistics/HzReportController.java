package com.saili.hzz.backend.controller.statistics;

import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.backend.service.statistics.HzReportService;
import com.saili.hzz.core.vo.HzReportVo;
import com.saili.hzz.core.constant.Constants;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.DataGrid;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import com.saili.hzz.tag.core.easyui.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 河长统计报表 controller
 * @author: whuab_mc
 * @date: 2019-10-06 10:38:10:38
 * @version: V1.0
 */
@Controller
@RequestMapping("/hz/report/")
public class HzReportController {
    Logger logger = LoggerFactory.getLogger(HzReportController.class);
    @Autowired
    private DivisionService divisionService;
    @Autowired
    private HzReportService hzReportService;

    @RequestMapping(value="getAreaTree",method ={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public AjaxJson getTreeDemoData(TSLDivisionEntity division, HttpServletResponse response, HttpServletRequest request ){
        AjaxJson j = new AjaxJson();
        try{
            List<TSLDivisionEntity> divisionList = new ArrayList<TSLDivisionEntity>();
            divisionList = divisionService.listContainParentCode(Constants.ROOT_DIVISION_CODE);
            List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
            Map<String,Object> map = null;
            for (TSLDivisionEntity entity : divisionList) {
                map = new HashMap<String,Object>();
                map.put("chkDisabled",false);
                map.put("click", true);
                map.put("id", entity.getCode());
                map.put("name", entity.getName());
                map.put("nocheck", false);
                map.put("struct","TREE");
                map.put("title",entity.getName());
                if (entity.getParent() != null) {
                    map.put("parentId",entity.getParent().getCode());
                }else {
                    map.put("parentId","0");
                }
                dataList.add(map);
            }
            j.setObj(dataList);
        }catch(Exception e){
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 首页
     */
    @RequestMapping(value = "page/home")
    public ModelAndView homePage(HttpServletRequest request) {
        return new ModelAndView("modules/hzz/statistics/hz_report");
    }

    /**
     * 主页面
     */
    @RequestMapping(value = "page/main")
    public ModelAndView mainPage(HttpServletRequest request) {
        String divisionCode = request.getParameter("divisionCode");
        request.setAttribute("divisionCode", divisionCode);
        return new ModelAndView("modules/hzz/statistics/hz_report_main");
    }

    /**
     * 数据列表
     */
    @RequestMapping(value = "datagrid", params = "datagrid")
    public void dataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String divisionCode = request.getParameter("divisionCode");
        if (StringUtils.isBlank(divisionCode)) {
            divisionCode = Constants.ROOT_DIVISION_CODE;
        }
        List<HzReportVo> voList = hzReportService.listByDivisionCode(divisionCode);
        dataGrid.setResults(voList);
        dataGrid.setTotal(voList.size());
        TagUtil.datagrid(response, dataGrid);
    }
}
