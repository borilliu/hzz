package com.saili.hzz.core.domain;

import com.saili.hzz.core.constant.Constants;

import java.util.Map;

/***
 * 行政区划每个级别的地区数量
 * @author: whuab_mc
 * @date: 2019-10-10 09:58:9:58
 * @version: V1.0
 */
public class DivisionLevelAreaCountDo {
    /**
     * 省级地区数量
     */
    private int provinceCount;
    /**
     * 市级地区数量
     */
    private int cityCount;
    /**
     * 县级地区数量
     */
    private int countyCount;
    /**
     * 乡级地区数量
     */
    private int townCount;
    /**
     * 村级地区数量
     */
    private int villageCout;

    public DivisionLevelAreaCountDo() {
    }

    public DivisionLevelAreaCountDo(Map<String, Long> divisionLevelAreaCountMap) {
        for (Map.Entry<String, Long> entry : divisionLevelAreaCountMap.entrySet()) {
            switch (entry.getKey()) {
                case Constants.DivisionLevel.PROVINCE_LEVEL:
                    provinceCount = entry.getValue().intValue();
                    break;
                case Constants.DivisionLevel.CITY_LEVEL:
                    cityCount = entry.getValue().intValue();
                    break;
                case Constants.DivisionLevel.COUNTY_LEVEL:
                    countyCount = entry.getValue().intValue();
                    break;
                case Constants.DivisionLevel.TOWN_LEVEL:
                    townCount = entry.getValue().intValue();
                    break;
                case Constants.DivisionLevel.VILLAGE_LEVEL:
                    villageCout = entry.getValue().intValue();
                    break;
            }
        }
    }

    public int getProvinceCount() {
        return provinceCount;
    }

    public void setProvinceCount(int provinceCount) {
        this.provinceCount = provinceCount;
    }

    public int getCityCount() {
        return cityCount;
    }

    public void setCityCount(int cityCount) {
        this.cityCount = cityCount;
    }

    public int getCountyCount() {
        return countyCount;
    }

    public void setCountyCount(int countyCount) {
        this.countyCount = countyCount;
    }

    public int getTownCount() {
        return townCount;
    }

    public void setTownCount(int townCount) {
        this.townCount = townCount;
    }

    public int getVillageCout() {
        return villageCout;
    }

    public void setVillageCout(int villageCout) {
        this.villageCout = villageCout;
    }
}
