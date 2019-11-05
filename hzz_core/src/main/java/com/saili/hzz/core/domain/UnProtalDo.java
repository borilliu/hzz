package com.saili.hzz.core.domain;

import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import org.activiti.engine.task.Task;

import java.util.Date;

/***
 * 未巡查 domain
 * @author: whuab_mc
 * @date: 2019-09-26 11:32:11:32
 * @version: V1.0
 */
public class UnProtalDo implements IProtalDo {
    private Task task;
    private RiverLakeChiefEntity riverLakeChief;
    private TSLBaseRiverLakePatrolEvent riverLakePatrolEvent;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * 获取 流程id
     *
     * @return
     */
    @Override
    public String getProcInstId() {
        return task.getProcessInstanceId();
    }

    /**
     * 获取 执行时间
     *
     * @return
     */
    @Override
    public Date getExecuteDate() {
        return task.getCreateTime();
    }

    @Override
    public RiverLakeChiefEntity getRiverLakeChief() {
        return riverLakeChief;
    }

    public void setRiverLakeChief(RiverLakeChiefEntity riverLakeChief) {
        this.riverLakeChief = riverLakeChief;
    }

    @Override
    public TSLBaseRiverLakePatrolEvent getRiverLakePatrolEvent() {
        return riverLakePatrolEvent;
    }

    public void setRiverLakePatrolEvent(TSLBaseRiverLakePatrolEvent riverLakePatrolEvent) {
        this.riverLakePatrolEvent = riverLakePatrolEvent;
    }
}
