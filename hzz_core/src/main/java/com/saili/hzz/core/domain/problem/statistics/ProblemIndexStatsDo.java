package com.saili.hzz.core.domain.problem.statistics;

/***
 * 各指标数量统计
 * 指标：类型总数、事件总数、已处理总数、待处理总数
 * @author: whuab_mc
 * @date: 2019-10-11 11:31:11:31
 * @version: V1.0
 */
public class ProblemIndexStatsDo {
    /**
     * 统计类型总数
     */
    private int statsTypeTotal;
    /**
     * 事件总数
     */
    private int eventTotal;
    /**
     * 已处理总数
     */
    private int processedTotal;
    /**
     * 待处理总数
     */
    private int pendingTotal;

    public ProblemIndexStatsDo() {
    }

    public int getStatsTypeTotal() {
        return statsTypeTotal;
    }

    public void setStatsTypeTotal(int statsTypeTotal) {
        this.statsTypeTotal = statsTypeTotal;
    }

    public int getEventTotal() {
        return eventTotal;
    }

    public void setEventTotal(int eventTotal) {
        this.eventTotal = eventTotal;
    }

    public int getProcessedTotal() {
        return processedTotal;
    }

    public void setProcessedTotal(int processedTotal) {
        this.processedTotal = processedTotal;
    }

    public int getPendingTotal() {
        return pendingTotal;
    }

    public void setPendingTotal(int pendingTotal) {
        this.pendingTotal = pendingTotal;
    }
}
