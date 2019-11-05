package com.saili.hzz.backend.enums;

/**
 * 流程状态 枚举类
 *
 * @author: whuab_mc
 * @date: 2019-09-12 13:53:13:53
 * @version: V1.0
 */
public enum ActStatusEnum {
    START("0", "开始", "start"),
    RUNNING("1", "处理中", "running"),
    END("-1", "结束", "end");
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

    ActStatusEnum(String code, String name, String ename) {
        this.code = code;
        this.name = name;
        this.ename = ename;
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
}
