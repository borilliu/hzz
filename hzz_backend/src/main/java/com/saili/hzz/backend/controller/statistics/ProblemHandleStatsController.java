package com.saili.hzz.backend.controller.statistics;

import com.saili.hzz.core.form.statistics.ProblemHandleStatsForm;
import com.saili.hzz.backend.service.statistics.ProblemHandleStatsService;
import com.saili.hzz.core.vo.ProblemHandleStatsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 问题统计 controller
 * @author: whuab_mc
 * @date: 2019-10-11 13:23:13:23
 * @version: V1.0
 */
@Controller
@RequestMapping("/stats/problem/")
public class ProblemHandleStatsController {
    @Autowired
    private ProblemHandleStatsService problemHandleStatsService;

    @RequestMapping("goHome")
    public String goHome(@ModelAttribute("problemHandleStatsForm") ProblemHandleStatsForm form) {
        return "/modules/hzz/statistics/problemstats/problem_stats_home";
    }

    @RequestMapping("getStatsData")
    @ResponseBody
    public ProblemHandleStatsVo dataView(ProblemHandleStatsForm form) {
        ProblemHandleStatsVo result = problemHandleStatsService.statsProblemHandle(form);
        return result;
    }
}
