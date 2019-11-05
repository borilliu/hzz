package com.saili.hzz.act;

/**
 * @Title: 处理状态
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-20 14:08:14:08
 * @version: V1.0
 */
public enum HandleStatusEnum {
    UN_ACCEPT("1", "未签收"),
    ACCEPT("2", "已签收"),
    REFUSE_ACCEPT("3", "拒签收"),
    SUBMIT("4","已上报"),
    ASSIGN("5","已交办"),
    HANDLING("6","处理中"),
    HANDLED("7", "已处理"),
    END("8", "已结案");
    /***
     * 处理状态编码
     */
    private String code;
    /***
     * 处理状态名称
     */
    private String name;

    HandleStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
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
}
