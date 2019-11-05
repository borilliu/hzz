package com.saili.hzz.core.vo;

import com.saili.hzz.core.domain.problem.statistics.ProblemExecuteStatsDo;
import com.saili.hzz.core.domain.problem.statistics.ProblemIndexStatsDo;
import com.saili.hzz.core.domain.problem.statistics.ProblemRatioStatsDo;
import com.saili.hzz.core.domain.problem.statistics.YearProblemHandledStatsDo;
import com.saili.hzz.core.entity.TSLDivisionEntity;

/***
 * 问题处理统计 vo
 * @author: whuab_mc
 * @date: 2019-10-11 10:58:10:58
 * @version: V1.0
 */
public class ProblemHandleStatsVo {
    /**
     * 地区
     */
    private TSLDivisionEntity area;
    /**
     * 查询日期
     */
    private String searchDate;
    /**
     * 统计类型总数
     */
    private String statsType;
    /**
     * 各指标数量统计
     * 指标：类型/地区总数、事件总数、已处理总数、待处理总数
     */
    private ProblemIndexStatsDo problemIndexStatsDo;
    /**
     * 巡河问题占比统计
     */
    private ProblemRatioStatsDo problemRatioStatsDo;
    /**
     * 巡河处理完成情况统计
     */
    private ProblemExecuteStatsDo problemExecuteStatsDo;
    /**
     * 近一年巡河问题处理完成次数变化趋势图
     */
    private YearProblemHandledStatsDo yearProblemHandledStatsDo;

    public ProblemHandleStatsVo() {
    }

    public TSLDivisionEntity getArea() {
        return area;
    }

    public void setArea(TSLDivisionEntity area) {
        this.area = area;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getStatsType() {
        return statsType;
    }

    public void setStatsType(String statsType) {
        this.statsType = statsType;
    }

    public ProblemIndexStatsDo getProblemIndexStatsDo() {
        return problemIndexStatsDo;
    }

    public void setProblemIndexStatsDo(ProblemIndexStatsDo problemIndexStatsDo) {
        this.problemIndexStatsDo = problemIndexStatsDo;
    }

    public ProblemRatioStatsDo getProblemRatioStatsDo() {
        return problemRatioStatsDo;
    }

    public void setProblemRatioStatsDo(ProblemRatioStatsDo problemRatioStatsDo) {
        this.problemRatioStatsDo = problemRatioStatsDo;
    }

    public ProblemExecuteStatsDo getProblemExecuteStatsDo() {
        return problemExecuteStatsDo;
    }

    public void setProblemExecuteStatsDo(ProblemExecuteStatsDo problemExecuteStatsDo) {
        this.problemExecuteStatsDo = problemExecuteStatsDo;
    }

    public YearProblemHandledStatsDo getYearProblemHandledStatsDo() {
        return yearProblemHandledStatsDo;
    }

    public void setYearProblemHandledStatsDo(YearProblemHandledStatsDo yearProblemHandledStatsDo) {
        this.yearProblemHandledStatsDo = yearProblemHandledStatsDo;
    }
}
