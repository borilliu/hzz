package com.saili.hzz.act.vo;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * 流程信息 vo
 * @author whuab_mc
 * @date 2019-07-23
 */
public class ActProcessVo {
    /**
     * 流程id
     */
    private String id;
    /**
     * 修成信息
     */
    private ProcessDefinition processDefinition;
    /**
     * 流程部署信息
     */
    private Deployment deployment;

    public ActProcessVo() {
    }

    public ActProcessVo(String id, ProcessDefinition processDefinition, Deployment deployment) {
        this.id = id;
        this.processDefinition = processDefinition;
        this.deployment = deployment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    public String getProcessId() {
        if (null != this.processDefinition) {
            return this.processDefinition.getId();
        }
        return null;
    }

    public String getDeploymentId() {
        if (null != deployment) {
            return deployment.getId();
        }
        return null;
    }
}
