package com.saili.hzz.core.domain.problem.statistics;

import com.google.common.collect.Lists;

import java.util.List;

/***
 * 处理完成情况统计
 * @author: whuab_mc
 * @date: 2019-10-11 11:41:11:41
 * @version: V1.0
 */
public class ProblemExecuteStatsDo {

    private List<String> legendData;
    /**
     * 待处理
     */
    private List<Integer> pendingData;
    /**
     * 已处理
     */
    private List<Integer> processedData;
    /**
     * 完成率
     */
    private List<Double> completeRateData;

    public ProblemExecuteStatsDo() {
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

    public List<Integer> getPendingData() {
        if (null == pendingData) {
            pendingData = Lists.newArrayList();
        }
        return pendingData;
    }

    public void setPendingData(List<Integer> pendingData) {
        this.pendingData = pendingData;
    }

    public List<Integer> getProcessedData() {
        if (null == processedData) {
            processedData = Lists.newArrayList();
        }
        return processedData;
    }

    public void setProcessedData(List<Integer> processedData) {
        this.processedData = processedData;
    }

    public List<Double> getCompleteRateData() {
        if (null == completeRateData) {
            completeRateData = Lists.newArrayList();
        }
        return completeRateData;
    }

    public void setCompleteRateData(List<Double> completeRateData) {
        this.completeRateData = completeRateData;
    }
}
