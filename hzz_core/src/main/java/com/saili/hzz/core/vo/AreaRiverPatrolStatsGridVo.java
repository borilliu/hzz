package com.saili.hzz.core.vo;

/***
 * 地区巡河统计数据列表 vo
 * @author: whuab_mc
 * @date: 2019-09-11 11:26:11:26
 * @version: V1.0
 */
public class AreaRiverPatrolStatsGridVo {
    /**
     * 地区
     */
    private String areaName;
    /**
     * 应巡人数
     */
    private int target;
    /**
     * 已巡人数
     */
    private int value;
    /**
     * 完成率
     */
    private double passRate;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getPassRate() {
        return passRate;
    }

    public void setPassRate(double passRate) {
        this.passRate = passRate;
    }
}
