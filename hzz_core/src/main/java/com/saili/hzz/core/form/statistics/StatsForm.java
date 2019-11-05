package com.saili.hzz.core.form.statistics;

import com.saili.hzz.core.form.BaseForm;

/***
 *
 * @author: whuab_mc
 * @date: 2019-09-11 11:42:11:42
 * @version: V1.0
 */
public class StatsForm extends BaseForm {
    public static final String AREA_ANTYPE = "area";
    public static final String RIVER_ANTYPE = "river";

    public static final String CITY_MODULE_CODE = "city";
    public static final String COUNTY_MODULE_CODE = "county";

    /**
     * 模块编码 （county/city）
     */
    private String moduleCode;
    /**
     * 分析类型（按地区/河长）
     */
    private String antype;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getAntype() {
        return antype;
    }

    public void setAntype(String antype) {
        this.antype = antype;
    }
}
