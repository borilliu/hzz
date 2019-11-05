package com.saili.hzz.core.enums;

import com.saili.hzz.core.common.exception.BusinessException;

/**
 * 河湖名录 枚举类
 *
 * @author whuab
 */

public enum RiverLakeTypeEnum {

    RIVER("10", "河流", "river", "a"),
    LAKE("11", "湖泊", "lake", "b"),
    REACH("12", "河段", "reach", "c"),
    RESERVOIR("13", "水库", "reservoir", "d"),

    INTAKE("20", "取水口", "intake", "a1"),
    WATER_FUNCTION("21", "水功能区", "waterFunction", "b1"),
    SURVEY_STATION("22", "测站", "surveyStation", "c1"),
    SEWAGE_OUTLET("23", "排污口", "sewageOutlet", "d1");

    /**
     * 编码
     */
    private String code;
    /**
     * 中文名称
     */
    private String name;
    /**
     * 英文名称
     */
    private String ename;
    /**
     * 别名
     */
    private String alias;

    RiverLakeTypeEnum(String code, String name, String ename, String alias) {
        this.code = code;
        this.name = name;
        this.ename = ename;
        this.alias = alias;
    }

    /**
     * 根据河湖类型英文名称获取河湖类型code
     * @param code
     * @return
     */
    public static String getEnameByCode(String code) {
        switch (code) {
            case "10":
                return RIVER.ename;
            case "11":
                return LAKE.ename;
            case "12":
                return REACH.ename;
            case "13":
                return RESERVOIR.ename;
            default:
                throw new BusinessException("没有此河湖类型");
        }
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

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
