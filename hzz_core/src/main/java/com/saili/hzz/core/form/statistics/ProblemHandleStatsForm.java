package com.saili.hzz.core.form.statistics;

import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.form.BaseForm;

/***
 * 问题处理统计查询条件 form
 * @author: whuab_mc
 * @date: 2019-10-11 13:28:13:28
 * @version: V1.0
 */
public class ProblemHandleStatsForm extends BaseForm {
    /**
     * 统计类型：问题类型
     */
    public static final String PROBLEM_TYPE_STATS_TYPE = "problemType";
    /**
     * 统计类型：行政地区
     */
    public static final String AREA_STATS_TYPE = "area";
    /**
     * 统计类型
     */
    private String statsType;
    /**
     * 查询条件：当前选定的地区
     */
    private TSLDivisionEntity area;

    public ProblemHandleStatsForm() {
    }

    public String getStatsType() {
        return statsType;
    }

    public void setStatsType(String statsType) {
        this.statsType = statsType;
    }

    public TSLDivisionEntity getArea() {
        return area;
    }

    public void setArea(TSLDivisionEntity area) {
        this.area = area;
    }
}
