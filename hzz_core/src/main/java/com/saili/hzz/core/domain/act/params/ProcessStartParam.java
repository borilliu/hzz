package com.saili.hzz.core.domain.act.params;

/***
 * @Title: 流程启动参数
 * @Description:
 * @author: whuab_mc
 * @date: 2019-09-05 14:12:14:12
 * @version: V1.0
 */
public class ProcessStartParam<T> {
    /***
     * 流程定义KEY
     */
    private String procDefKey;
    /***
     * 业务表表名
     */
    private String businessTable;
    /***
     * 业务表编号
     */
    private String businessId;
    /***
     * 流程标题，显示在待办任务标题
     */
    private String title;
    /***
     * 流程启动者
     */
    private String starter;
    /***
     * 业务对象
     */
    private T business;

    public ProcessStartParam() {
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public String getBusinessTable() {
        return businessTable;
    }

    public void setBusinessTable(String businessTable) {
        this.businessTable = businessTable;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public T getBusiness() {
        return business;
    }

    public void setBusiness(T business) {
        this.business = business;
    }
}
