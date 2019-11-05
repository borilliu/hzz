/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.saili.hzz.act.service;

import com.google.common.collect.Maps;
import com.saili.hzz.act.HandleStatusEnum;
import com.saili.hzz.core.domain.act.Act;
import com.saili.hzz.core.domain.act.params.ProcessStartParam;
import com.saili.hzz.core.util.ProcessDefCache;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import com.saili.hzz.core.util.ResourceUtil;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 流程定义相关Service
 *
 * @author ThinkGem
 * @version 2013-11-03
 */
@Service
@Transactional(readOnly = true)
public class ActTaskService {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;

    /**
     * 启动流程
     *
     * @param param
     * @return 流程实例ID
     */
    @Transactional(readOnly = false)
    public String startProcess(ProcessStartParam param) {
        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(param.getStarter());

        // 设置流程变量
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("report", true);
        variables.put("title", param.getTitle());

        // 启动流程
        ProcessInstance procIns = runtimeService.startProcessInstanceByKey(param.getProcDefKey(), param.getBusinessTable() + ":" + param.getBusinessId(), variables);

        return procIns.getId();
    }

    /**
     * 提交任务, 并保存意见
     *
     * @param act
     */
    @Transactional(readOnly = false)
    public void complete(Act act, Map<String, Object> variables) {
        // 添加意见
        if (StringUtils.isNotBlank(act.getProcInsId()) && StringUtils.isNotBlank(act.getComment())) {
            taskService.addComment(act.getTaskId(), act.getProcInsId(), act.getComment());
        }
        // 提交任务
        taskService.complete(act.getTaskId(), variables);
    }

    /**
     * 获取待办列表
     *
     * @return
     */
    public List<Act> todoList(Act act) {
        String userId = ResourceUtil.getSessionUser().getUserName();

        List<Act> result = new ArrayList<Act>();

        // =============== 已经签收的任务  ===============
        TaskQuery todoTaskQuery = taskService.createTaskQuery()
				.taskAssignee(userId)
				.active()
                .includeProcessVariables()
				.orderByTaskCreateTime()
				.desc();

        // 设置查询条件
        if (StringUtils.isNotBlank(act.getProcDefKey())) {
            todoTaskQuery.processDefinitionKey(act.getProcDefKey());
        }
        if (act.getBeginDate() != null) {
            todoTaskQuery.taskCreatedAfter(act.getBeginDate());
        }
        if (act.getEndDate() != null) {
            todoTaskQuery.taskCreatedBefore(act.getEndDate());
        }

        // 查询列表
        List<Task> todoList = todoTaskQuery.list();
        for (Task task : todoList) {
			result.add(newAct(task, HandleStatusEnum.ACCEPT.getCode()));
        }

        // =============== 等待签收的任务  ===============
        TaskQuery toClaimQuery = taskService.createTaskQuery()
				.taskCandidateUser(userId)
                .includeProcessVariables()
				.active()
				.orderByTaskCreateTime().desc();

        // 设置查询条件
        if (StringUtils.isNotBlank(act.getProcDefKey())) {
            toClaimQuery.processDefinitionKey(act.getProcDefKey());
        }
        if (act.getBeginDate() != null) {
            toClaimQuery.taskCreatedAfter(act.getBeginDate());
        }
        if (act.getEndDate() != null) {
            toClaimQuery.taskCreatedBefore(act.getEndDate());
        }

        // 查询列表
        List<Task> toClaimList = toClaimQuery.list();
        for (Task task : toClaimList) {
            result.add(newAct(task, HandleStatusEnum.UN_ACCEPT.getCode()));
        }
        return result;
    }

    /**
     * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
     * @return
     */
    public String getFormKey(String procDefId, String taskDefKey){
        String formKey = "";
        if (StringUtils.isNotBlank(procDefId)){
            if (StringUtils.isNotBlank(taskDefKey)){
                try{
                    formKey = formService.getTaskFormKey(procDefId, taskDefKey);
                }catch (Exception e) {
                    formKey = "";
                }
            }
            if (StringUtils.isBlank(formKey)){
                formKey = formService.getStartFormKey(procDefId);
            }
            if (StringUtils.isBlank(formKey)){
                formKey = "/404";
            }
        }
        return formKey;
    }

    /**
     * 获取流程实例对象
     * @param procInsId
     * @return
     */
    @Transactional(readOnly = false)
    public ProcessInstance getProcIns(String procInsId) {
        return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
    }

    private Act newAct(Task task, String status) {
		Act act = new Act();
		act.setId(task.getId());
		act.setTask(task);
		act.setTaskName(task.getAssignee());
		act.setTaskExecutionId(task.getExecutionId());
		act.setVars(task.getProcessVariables());
		act.setTaskVars(task.getTaskLocalVariables());
		act.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
		act.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//		act.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
		act.setStatus(status);
		return act;
	}
}
