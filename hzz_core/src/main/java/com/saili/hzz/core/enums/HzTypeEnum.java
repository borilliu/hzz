package com.saili.hzz.core.enums;

import com.saili.hzz.core.constant.Constants;

/**
 * 河长类型 枚举类
 * @author: whuab_mc
 * @date: 2019-10-07 16:14:16:14
 * @version: V1.0
 */
public enum HzTypeEnum {

    FIST_PRESIDENT_HZ("第一总河长", "1", 1),
    PRESIDENT_HZ("总河长", "2", 2),
    VICE_PRESIDENT_HZ("副总河长", "3", 3),
    HZ("河长", "4", 4),
    VICE_HZ("副河长", "5", 5);

    private String name;
    private String code;
    private int sort;

    HzTypeEnum(String name, String code, int sort) {
        this.name = name;
        this.code = code;
        this.sort = sort;
    }

    /**
     * 根据 code 获取枚举对象
     *
     * @param code
     * @return
     */
    public static HzTypeEnum getByCode(String code) {
        switch (code) {
            case Constants.HzType.FIST_PRESIDENT_HZ_CODE:
                return FIST_PRESIDENT_HZ;
            case Constants.HzType.PRESIDENT_HZ_CODE:
                return PRESIDENT_HZ;
            case Constants.HzType.VICE_PRESIDENT_HZ_CODE:
                return VICE_PRESIDENT_HZ;
            case Constants.HzType.HZ_CODE:
                return HZ;
            case Constants.HzType.VICE_HZ_CODE:
                return VICE_HZ;
            default:
                return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
