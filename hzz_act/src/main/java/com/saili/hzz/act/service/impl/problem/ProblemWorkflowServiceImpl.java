package com.saili.hzz.act.service.impl.problem;

import com.saili.hzz.core.domain.act.Act;
import com.saili.hzz.core.domain.act.params.ProcessStartParam;
import com.saili.hzz.core.domain.act.problem.ProblemAct;
import com.saili.hzz.act.service.ActTaskService;
import com.saili.hzz.act.service.problem.ProblemWorkflowService;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import com.saili.hzz.core.vo.ProblemTaskVo;
import com.saili.hzz.core.constant.Constants;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import com.saili.hzz.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/***
 * @Title: 问题处理流程 service
 * @Description:
 * @author: whuab_mc
 * @date: 2019-08-23 15:05:15:05
 * @version: V1.0
 */
@Service
@Transactional(readOnly = true)
public class ProblemWorkflowServiceImpl implements ProblemWorkflowService {
    @Autowired
    private IdentityService identityService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected HistoryService historyService;

    @Autowired
    private SystemService systemService;
    @Autowired
    private ActTaskService actTaskService;

    /**
     * 根据流程处理节点，获取页面名称
     *
     * @param act
     * @return
     * @author whuab_mc
     * @date 2019-08-21 11:07:30
     */
    @Override
    public String getViewName(Act act) {
        String viewName = "";
        String nodeName = act.getTaskDefKey();
        switch (nodeName) {
            case Constants.Activiti.ProblemNode.HAMLET_RIVER_NODE_ID:
            case Constants.Activiti.ProblemNode.TOWN_RIVER_NODE_ID:
            case Constants.Activiti.ProblemNode.COUNTY_RIVER_NODE_ID:
            case Constants.Activiti.ProblemNode.CITY_RIVER_NODE_ID:
            case Constants.Activiti.ProblemNode.PROVINCE_RIVER_NODE_ID:
                viewName = "problem-proc-step21";
                break;
            default:
                viewName = "problem-proc-step22";
        }
        return viewName;
    }

    /**
     * 申请流程
     *
     * @param param
     * @return
     * @author whuab_mc
     * @date 2019-08-06 13:12:53
     */
    @Override
    @Transactional(readOnly = false)
    public void applyProcess(ProcessStartParam param) {
        TSLBaseRiverLakePatrolEvent event = (TSLBaseRiverLakePatrolEvent) param.getBusiness();

        // 申请发起
        if (StringUtils.isEmpty(event.getId())) {
            Serializable serializable = systemService.save(param.getBusiness());
            // 启动流程
            String procInsId = actTaskService.startProcess(param);
            // 把流程编号(流程id)设置上去
            event.setProcInsId(procInsId);
            systemService.saveOrUpdate(event);
        }
    }

    /**
     * 查询待办任务
     *
     * @param userName 用户的登录名
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public List<ProblemAct> findTodoTasks(String userName) {
        List<ProblemAct> results = new ArrayList<ProblemAct>();

        // 根据当前人的ID查询z
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userName);
        List<Task> tasks = taskQuery.list();

        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            Map<String, Object> varMap = taskService.getVariables(task.getId());
            if (null != varMap.get("rivers")) {
                String rivers = (String) varMap.get("rivers");
                List<String> riverList = Arrays.asList(rivers.split(","));
                if (!riverList.contains(userName)) {
                    continue;
                }
                Map<String, String> riverMap = riverList.stream().collect(Collectors.toMap(String::toString, Function.identity(), (key1, key2) -> key2));
                if (!riverMap.containsKey(userName)) {
                    continue;
                }
            }
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null) {
                continue;
            }
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }

            ProblemAct problem = new ProblemAct();
            problem.setId(task.getId());
            problem.setTask(task);
            problem.setTaskName(task.getAssignee());
            problem.setTaskExecutionId(task.getExecutionId());
            problem.setVars(task.getProcessVariables());
            problem.setTaskVars(task.getTaskLocalVariables());
            problem.setProcDef(getProcessDefinition(processInstance.getProcessDefinitionId()));
            problem.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
            results.add(problem);
        }

        return results;
    }

    /**
     * 查询待办任务
     *
     * @param userName 用户的登录名
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public List<ProblemTaskVo> listTodoProblem(String userName) {
        List<ProblemTaskVo> results = new ArrayList<ProblemTaskVo>();

        // 根据当前人的ID查询z
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userName);
        List<Task> tasks = taskQuery.list();

        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            Map<String, Object> varMap = taskService.getVariables(task.getId());
            if (null != varMap.get("rivers")) {
                String rivers = (String) varMap.get("rivers");
                List<String> riverList = Arrays.asList(rivers.split(","));
                if (!riverList.contains(userName)) {
                    continue;
                }
                Map<String, String> riverMap = riverList.stream().collect(Collectors.toMap(String::toString, Function.identity(), (key1, key2) -> key2));
                if (!riverMap.containsKey(userName)) {
                    continue;
                }
            }
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null) {
                continue;
            }
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }

            ProblemAct act = new ProblemAct();
            act.setId(task.getId());
            act.setTask(task);
            act.setTaskName(task.getAssignee());
            act.setTaskExecutionId(task.getExecutionId());
            act.setVars(task.getProcessVariables());
            act.setTaskVars(task.getTaskLocalVariables());
            act.setProcDef(getProcessDefinition(processInstance.getProcessDefinitionId()));
            act.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
            TSLBaseRiverLakePatrolEvent problem = systemService.findUniqueByProperty(TSLBaseRiverLakePatrolEvent.class, "id", act.getBusinessId());
            results.add(new ProblemTaskVo(act, problem));
        }

        return results;
    }

    /**
     * 查询流程定义对象
     *
     * @param processDefinitionId 流程定义ID
     * @return
     */
    protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }
}
