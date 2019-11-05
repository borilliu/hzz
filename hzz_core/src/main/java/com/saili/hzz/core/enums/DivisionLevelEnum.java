package com.saili.hzz.core.enums;

import com.google.common.collect.Lists;
import com.saili.hzz.core.common.exception.BusinessException;

import java.util.List;

/**
 * 行政区划级别 枚举类
 *
 * @author: whuab_mc
 * @date: 2019-10-08 15:50:15:50
 * @version: V1.0
 */
public enum DivisionLevelEnum {
    PROVINCE_LEVEL("province", "省级", 1, "1"),
    CITY_LEVEL("city", "市级", 2, "2"),
    COUNTY_LEVEL("county", "县级", 3, "3"),
    TOWN_LEVEL("town", "乡级", 4, "4"),
    VILLAGE_LEVEL("village", "村级", 5, "5");

    private String code;
    private String name;
    private int parentCout;
    private String level;

    DivisionLevelEnum(String code, String name, int parentCout, String level) {
        this.code = code;
        this.name = name;
        this.parentCout = parentCout;
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentCout() {
        return parentCout;
    }

    public void setParentCout(int parentCout) {
        this.parentCout = parentCout;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public static String getCodeByParentCount(int parentCout) {
        switch (parentCout) {
            case 1:
                return PROVINCE_LEVEL.code;
            case 2:
                return CITY_LEVEL.code;
            case 3:
                return COUNTY_LEVEL.code;
            case 4:
                return TOWN_LEVEL.code;
            case 5:
                return VILLAGE_LEVEL.code;
            default:
                throw new BusinessException("DivisionLevelEnum unknow parentCount: " + parentCout);
        }
    }

    public static String getNameByParentCount(int parentCout) {
        switch (parentCout) {
            case 1:
                return PROVINCE_LEVEL.name;
            case 2:
                return CITY_LEVEL.name;
            case 3:
                return COUNTY_LEVEL.name;
            case 4:
                return TOWN_LEVEL.name;
            case 5:
                return VILLAGE_LEVEL.name;
            default:
                throw new BusinessException("DivisionLevelEnum unknow parentCount: " + parentCout);
        }
    }

    /**
     *
     * @param level
     * @return
     */
    public static String getDivisionLevelCodeByLevel(String level) {
        switch (level) {
            case "1":
                return PROVINCE_LEVEL.code;
            case "2":
                return CITY_LEVEL.code;
            case "3":
                return COUNTY_LEVEL.code;
            case "4":
                return TOWN_LEVEL.code;
            case "5":
                return VILLAGE_LEVEL.code;
            default:
                throw new BusinessException("DivisionLevelEnum unknow level: " + level);
        }
    }

    /**
     * 获取所有行政区划级别
     * @return
     */
    public static List<DivisionLevelEnum> listDivisionLevel() {
        List<DivisionLevelEnum> results = Lists.newArrayList();
        results.add(PROVINCE_LEVEL);
        results.add(CITY_LEVEL);
        results.add(COUNTY_LEVEL);
        results.add(TOWN_LEVEL);
        results.add(VILLAGE_LEVEL);
        return results;
    }
}
