package com.saili.hzz.core.domain.problem.statistics;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/***
 * 一年问题处理完成次数变化趋势图
 * @author: whuab_mc
 * @date: 2019-10-11 11:42:11:42
 * @version: V1.0
 */
public class YearProblemHandledStatsDo {
    private String[] legendData;
    private String[] xAxisData;
    private List<Map<String, Object>> series;

    public YearProblemHandledStatsDo() {
    }

    public String[] getLegendData() {
        return legendData;
    }

    public void setLegendData(String[] legendData) {
        this.legendData = legendData;
    }

    public String[] getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(String[] xAxisData) {
        this.xAxisData = xAxisData;
    }

    public List<Map<String, Object>> getSeries() {
        if (null == series) {
            series = Lists.newArrayList();
        }
        return series;
    }

    public void setSeries(List<Map<String, Object>> series) {
        this.series = series;
    }
}
