package com.saili.hzz.core.vo;

import com.saili.hzz.core.domain.BasicDataStatsDo;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.common.entity.IdEntity;

/***
 * 基础数据统计 view object 基类
 * @author: whuab_mc
 * @date: 2019-10-07 11:04:11:04
 * @version: V1.0
 */
public class BaseBasicDataStatsVo extends IdEntity {
    /**
     * 行政地区
     */
    private TSLDivisionEntity division;
    /**
     * 市级
     */
    private BasicDataStatsDo cityLevel;
    /**
     * 县级
     */
    private BasicDataStatsDo countyLevel;
    /**
     * 乡级
     */
    private BasicDataStatsDo townLevel;
    /**
     * 村级
     */
    private BasicDataStatsDo villageLevel;

    public BaseBasicDataStatsVo() {
    }

    public TSLDivisionEntity getDivision() {
        return division;
    }

    public void setDivision(TSLDivisionEntity division) {
        this.division = division;
    }

    public BasicDataStatsDo getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(BasicDataStatsDo cityLevel) {
        this.cityLevel = cityLevel;
    }

    public BasicDataStatsDo getCountyLevel() {
        return countyLevel;
    }

    public void setCountyLevel(BasicDataStatsDo countyLevel) {
        this.countyLevel = countyLevel;
    }

    public BasicDataStatsDo getTownLevel() {
        return townLevel;
    }

    public void setTownLevel(BasicDataStatsDo townLevel) {
        this.townLevel = townLevel;
    }

    public BasicDataStatsDo getVillageLevel() {
        return villageLevel;
    }

    public void setVillageLevel(BasicDataStatsDo villageLevel) {
        this.villageLevel = villageLevel;
    }
}
