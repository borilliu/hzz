package com.saili.hzz.core.vo;

import com.saili.hzz.core.util.StringUtil;

/***
 * 河长统计 vo
 * @author: whuab_mc
 * @date: 2019-09-18 10:06:10:06
 * @version: V1.0
 */
public class HzProtalStatsVo {
    /**
     * 地区
     */
    private String areaName;
    /**
     * 指标名称（职称/职位）
     */
    private String standName;
    /**
     * 月份
     */
    private String month;
    /**
     * 应巡人数
     */
    private double target;
    /**
     * 已巡人数
     */
    private double value;
    /**
     * 未巡人数
     */
    private double unValue;
    /**
     * 巡河完成率（%）
     */
    private Double passRate;


    public HzProtalStatsVo() {
    }

    public String areaMonthStandNameKey() {
        if (StringUtil.isEmpty(this.areaName) || StringUtil.isEmpty(this.month) || StringUtil.isEmpty(this.standName)) {
            return null;
        }
        return this.areaName + "_" + this.month + "_" + standName;
    }

    public String areaMonthKey() {
        if (StringUtil.isEmpty(this.areaName) || StringUtil.isEmpty(this.month)) {
            return null;
        }
        return this.areaName + "_" + this.month;
    }

    public String monthStandNameKey() {
        if (StringUtil.isEmpty(this.month) || StringUtil.isEmpty(this.standName)) {
            return null;
        }
        return this.month + "_" + standName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStandName() {
        return standName;
    }

    public void setStandName(String standName) {
        this.standName = standName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getUnValue() {
        return unValue;
    }

    public void setUnValue(double unValue) {
        this.unValue = unValue;
    }

    public Double getPassRate() {
        if (null != passRate) {
            return passRate;
        }
        return value / target * 100;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }
}
