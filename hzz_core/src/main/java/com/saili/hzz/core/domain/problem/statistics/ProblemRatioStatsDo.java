package com.saili.hzz.core.domain.problem.statistics;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/***
 * 问题占比统计
 * @author: whuab_mc
 * @date: 2019-10-11 11:40:11:40
 * @version: V1.0
 */
public class ProblemRatioStatsDo {
    private List<String> legendData;
    private List<Map<String, Object>> seriesData;

    public ProblemRatioStatsDo() {
    }

    public List<String> getLegendData() {
        if (null == legendData) {
            legendData = Lists.newArrayList();
        }
        return legendData;
    }

    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }

    public List<Map<String, Object>> getSeriesData() {
        if (null == seriesData) {
            seriesData = Lists.newArrayList();
        }
        return seriesData;
    }

    public void setSeriesData(List<Map<String, Object>> seriesData) {
        this.seriesData = seriesData;
    }
}
