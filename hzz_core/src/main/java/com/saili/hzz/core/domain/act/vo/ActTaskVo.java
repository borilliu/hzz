package com.saili.hzz.core.domain.act.vo;

import com.saili.hzz.core.util.ProcessDefCache;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import java.util.Date;
import java.util.Map;

/***
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-20 12:26:12:26
 * @version: V1.0
 */
public class ActTaskVo {
    private static final long serialVersionUID = 1L;

    /**
     * 任务编号
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务定义Key（任务环节标识）
     */
    private String taskDefKey;
    /**
     * 流程实例ID
     */
    private String procInsId;
    /**
     * 流程实例ID
     */
    private String procInsName;
    /**
     * 流程实例ID
     */
    private int procInsVersion;
    /**
     * 任务执行人编号
     */
    private String assignee;
    /**
     * 任务创建时间
     */
    private Date taskCreateDate;
    private String taskExecutionId;
    private String title;
    /**
     * 状态
     */
    private String status;

    public ActTaskVo() {
    }

    public ActTaskVo(Task task, String status) {
        this.taskId = task.getId();
        this.taskName = task.getName();
        ProcessDefinition processDefinition = ProcessDefCache.get(task.getProcessDefinitionId());
        this.procInsId = processDefinition.getId();
        this.procInsName = processDefinition.getName();
        this.procInsVersion = processDefinition.getVersion();
        this.assignee = task.getAssignee();
        this.taskCreateDate = task.getCreateTime();
        this.taskExecutionId = task.getExecutionId();
        Map<String, Object> vars = task.getProcessVariables();
        this.title = vars.get("title").toString();
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getProcInsName() {
        return procInsName;
    }

    public void setProcInsName(String procInsName) {
        this.procInsName = procInsName;
    }

    public int getProcInsVersion() {
        return procInsVersion;
    }

    public void setProcInsVersion(int procInsVersion) {
        this.procInsVersion = procInsVersion;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getTaskCreateDate() {
        return taskCreateDate;
    }

    public void setTaskCreateDate(Date taskCreateDate) {
        this.taskCreateDate = taskCreateDate;
    }

    public String getTaskExecutionId() {
        return taskExecutionId;
    }

    public void setTaskExecutionId(String taskExecutionId) {
        this.taskExecutionId = taskExecutionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
