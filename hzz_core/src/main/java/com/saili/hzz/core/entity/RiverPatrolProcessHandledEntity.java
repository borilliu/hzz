package com.saili.hzz.core.entity;

import com.saili.hzz.core.domain.IProtalDo;
import com.saili.hzz.core.form.statistics.StatsForm;
import com.saili.hzz.core.common.entity.BaseEntity;
import com.saili.hzz.core.util.DateUtils;
import com.saili.hzz.core.util.StringUtil;

import java.util.Date;

/***
 * 已经处理的巡河流程节点记录
 *
 * @author: whuab_mc
 * @date: 2019-09-17 14:43:14:43
 * @version: V1.0
 */
public class RiverPatrolProcessHandledEntity extends BaseEntity implements IProtalDo {
    /**
     * 流程id
     */
    private String procInstId;
    /**
     * 任务节点key
     */
    private String taskDefKey;
    /**
     * 处理人id (对应数据库中的 handle_user_id 字段)
     */
    private RiverLakeChiefEntity riverLakeChief;
    /**
     * 处理时间
     */
    private Date handleDate;
    /**
     * 处理详情
     */
    private String handleDetail;

    /**
     * 对应的巡河事件
     */
    private TSLBaseRiverLakePatrolEvent riverLakePatrolEvent;

    public String keyProcInstIdDivisionMonth() {
        if (StringUtil.isEmpty(procInstId) || StringUtil.isEmpty(riverLakeChief.getDivisionCode()) || null == handleDate) {
            return null;
        }
        String key = this.procInstId + "_" + riverLakeChief.getDivisionCode() + "_" + DateUtils.date2Str(this.handleDate, DateUtils.date_sdf);
        return key;
    }

    public String keyProcInstIdDivisionMonthTest(StatsForm form) {
        String[] codes = riverLakeChief.getDivision().getParentCodes().split(",");
        String formDivisionCode = null;
        if (codes.length > 3) {
            formDivisionCode = codes[2];
        } else {
            formDivisionCode = riverLakeChief.getDivision().getCode();
        }


        if (StringUtil.isEmpty(procInstId) || StringUtil.isEmpty(riverLakeChief.getDivisionCode()) || null == handleDate) {
            return null;
        }
        String key = this.procInstId + "_" + riverLakeChief.getDivisionCode() + "_" + DateUtils.date2Str(this.handleDate, DateUtils.date_sdf);
        return key;
    }

    public RiverPatrolProcessHandledEntity() {
    }

    @Override
    public String getProcInstId() {
        return procInstId;
    }

    /**
     * 获取 执行时间
     *
     * @return
     */
    @Override
    public Date getExecuteDate() {
        return handleDate;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    @Override
    public RiverLakeChiefEntity getRiverLakeChief() {
        return riverLakeChief;
    }

    public void setRiverLakeChief(RiverLakeChiefEntity riverLakeChief) {
        this.riverLakeChief = riverLakeChief;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public String getHandleDetail() {
        return handleDetail;
    }

    public void setHandleDetail(String handleDetail) {
        this.handleDetail = handleDetail;
    }

    @Override
    public TSLBaseRiverLakePatrolEvent getRiverLakePatrolEvent() {
        return riverLakePatrolEvent;
    }

    public void setRiverLakePatrolEvent(TSLBaseRiverLakePatrolEvent riverLakePatrolEvent) {
        this.riverLakePatrolEvent = riverLakePatrolEvent;
    }
}
