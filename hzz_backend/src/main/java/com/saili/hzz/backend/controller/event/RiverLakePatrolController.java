package com.saili.hzz.backend.controller.event;

import com.saili.hzz.core.domain.act.Act;
import com.saili.hzz.core.domain.act.params.ProcessStartParam;
import com.saili.hzz.core.domain.act.problem.ProblemAct;
import com.saili.hzz.act.service.ActTaskService;
import com.saili.hzz.act.service.problem.ProblemWorkflowService;
import com.saili.hzz.core.entity.*;
import com.saili.hzz.backend.enums.ActStatusEnum;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import com.saili.hzz.backend.service.event.RiverLakePatrolEventService;
import com.saili.hzz.backend.service.event.RiverLakePatrolService;
import com.saili.hzz.backend.service.event.RiverPatrolProcessHandledService;
import com.saili.hzz.core.vo.ProblemTaskVo;
import com.saili.hzz.core.constant.Constants;
import org.activiti.engine.TaskService;
import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.core.util.DateUtils;
import com.saili.hzz.core.util.ResourceUtil;
import com.saili.hzz.tag.core.easyui.TagUtil;
import com.saili.hzz.web.system.pojo.base.DictEntity;
import com.saili.hzz.web.system.pojo.base.TSUser;
import com.saili.hzz.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author whuab_mc
 * @version V1.0
 * @Title: Controller
 * @Description: 巡河管理
 * @date 2019-07-03 09:12:55
 */
@Controller
@RequestMapping("/riverLakePatrolController")
public class RiverLakePatrolController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RiverLakePatrolController.class);

    @Autowired
    private RiverLakePatrolService riverLakePatrolService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private RiverLakePatrolEventService riverLakePatrolEventService;
    @Autowired
    private ProblemWorkflowService problemWorkflowService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private RiverLakeChiefService riverLakeChiefService;
    @Autowired
    private RiverPatrolProcessHandledService riverPatrolProcessHandledService;

    @RequestMapping(params = "goAdd")
    public String goAdd() {
        return "modules/hzz/slRiverLake/slRiverPatrolAdd";
    }

    @RequestMapping(params = {"list"})
    public String list() {
        return "modules/hzz/slRiverLake/slRiverPatrolList";
    }

    @RequestMapping(params = "todoList")
    public String todoList() {
        return "modules/act/actTaskList";
    }

    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(TSLBaseRiverLakePatrol riverLakePatrol) {
        AjaxJson j = new AjaxJson();

        String message = "巡查记录添加成功";
        try {
            int result = riverLakePatrolService.save(riverLakePatrol);
        } catch (Exception e) {
            e.printStackTrace();
            message = "巡查记录添加失败";
        }

        j.setMsg(message);
        j.setObj(riverLakePatrol);
        return j;
    }

    /**
     * 申请单保存/修改
     * 开始
     *
     * @param problem
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "start")
    @ResponseBody
    public AjaxJson start(TSLBaseRiverLakePatrolEvent problem, Model model, RedirectAttributes redirectAttributes) {
        String message = null;
        AjaxJson j = new AjaxJson();
        ProcessStartParam param = new ProcessStartParam();
        param.setProcDefKey(Constants.Activiti.PD_PROBLEM[0]);
        param.setBusinessId(Constants.Activiti.PD_PROBLEM[1]);
        param.setBusinessId(problem.getId());
        param.setTitle(problem.getName());
        param.setStarter(problem.getTsSubmitUser().getId());
        param.setBusiness(problem);
        problemWorkflowService.applyProcess(param);
        message = "提交审批成功";
        j.setMsg(message);
        return j;
    }

    /**
     * 获取待办列表
     *
     * @param act
     * @return
     */
    @RequestMapping(params = "dataGrid")
    public void taskList(Act act, HttpServletResponse response, DataGrid dataGrid) throws Exception {
        List<Act> list = actTaskService.todoList(act);
        dataGrid.setTotal(list.size());
        dataGrid.setResults(list);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 获取待办列表
     *
     * @return
     */
    @RequestMapping(params = "taskList")
    public void taskList(HttpServletResponse response, DataGrid dataGrid) {
        try {
            TSUser user = ResourceUtil.getSessionUser();
            RiverLakeChiefEntity riverLakeChief = riverLakeChiefService.getByUser(user);
            List<ProblemAct> results = problemWorkflowService.findTodoTasks(riverLakeChief.getId());
            dataGrid.setTotal(results.size());
            dataGrid.setResults(results);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取待办问题清单
     *
     * @return
     */
    @RequestMapping(value = "problemList", method = RequestMethod.GET)
    public void problemList(HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUser();
        RiverLakeChiefEntity river = riverLakeChiefService.getByUser(user);
        List<ProblemTaskVo> voList = problemWorkflowService.listTodoProblem(river.getId());
        dataGrid.setTotal(voList.size());
        dataGrid.setResults(voList);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 路由到对应的处理页
     */
    @RequestMapping(value = "form")
    public String routeHandleView(Act act, HttpServletRequest request, HttpServletResponse response, Model model) {
        // 获取流程实例对象
        if (act.getProcInsId() != null) {
            act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
        }
        String viewName = problemWorkflowService.getViewName(act);
        model.addAttribute("act", act);
        model.addAttribute("taskId", act.getTaskId());
        return "modules/hzz/problem/" + viewName;
    }

    /**
     * 签收任务
     */
    @RequestMapping(value = "task/claim/{id}")
    @ResponseBody
    public String claim(@PathVariable("id") String taskId) {
        String message = "签收成功！";
        AjaxJson j = new AjaxJson();
        try {
            TSUser user = ResourceUtil.getSessionUser();
            RiverLakeChiefEntity riverLakeChief = riverLakeChiefService.getByUser(user);
            taskService.claim(taskId, riverLakeChief.getId());
            j.setSuccess(true);
        } catch (Exception e) {
            logger.info(e.getMessage());
            message = "签收失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j.getJsonStr();
    }

    /**
     * 上报
     *
     * @param
     * @return
     */
    @RequestMapping(value = "task/submit/{id}")
    @ResponseBody
    public String submit(@PathVariable("id") String taskId, boolean report) {
        String message = "上报成功！";
        AjaxJson j = new AjaxJson();
        try {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("report", true);
            // 注意此处要放在taskService调用complete方法之前执行！
            // 因在调用complete后，任务id会改变，如再用原来的额任务id查询会查询不到信息报空指针异常
            riverPatrolProcessHandledService.save(taskId, null);
            taskService.complete(taskId, variables);
            j.setSuccess(true);
        } catch (Exception e) {
            logger.error("error on complete task {}, report={}", new Object[]{taskId, report, e});
            message = "上报失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j.getJsonStr();
    }

    /**
     * 交办
     *
     * @param
     * @return
     */
    @RequestMapping(value = "task/dispatch/{id}")
    @ResponseBody
    public String dispatch(@PathVariable("id") String taskId, String rivers, String reason, boolean locallevel) {
        String message = "交办成功！";
        AjaxJson j = new AjaxJson();
        try {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("report", false);
            variables.put("locallevel", locallevel);
            variables.put("rivers", rivers);
            variables.put("reason", reason);
            // 注意此处要放在taskService调用complete方法之前执行！
            // 因在调用complete后，任务id会改变，如再用原来的额任务id查询会查询不到信息报空指针异常
            riverPatrolProcessHandledService.save(taskId, reason);
            taskService.complete(taskId, variables);
            j.setSuccess(true);
        } catch (Exception e) {
            logger.error("error on complete task {}, report={}, reason={}", new Object[]{taskId, false, reason, e});
            message = "交办失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j.getJsonStr();
    }

    /**
     * 河长处理
     *
     * @param
     * @return
     */
    @RequestMapping(value = "task/handled")
    @ResponseBody
    public String handled(String taskId, String procInsId, String content) {
        String message = "已处理完成！";
        AjaxJson j = new AjaxJson();
        try {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("content", content);
            // 注意此处要放在taskService调用complete方法之前执行！
            // 因在调用complete后，任务id会改变，如再用原来的额任务id查询会查询不到信息报空指针异常
            riverPatrolProcessHandledService.save(taskId, content);
            taskService.complete(taskId, variables);
            riverLakePatrolEventService.updateStatus(procInsId, ActStatusEnum.END.getCode());
            j.setSuccess(true);
        } catch (Exception e) {
            logger.error("error on complete task {}, report={}, content={}", new Object[]{taskId, false, content, e});
            message = "处理失败！";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j.getJsonStr();
    }

    /**
     * 获取待办列表
     *
     * @return
     */
    @RequestMapping(params = "test")
    public void test(Act act, HttpServletResponse response, Model model, DataGrid dataGrid) throws Exception {
		/*TSLBaseRiverLakePatrolEvent problem = new TSLBaseRiverLakePatrolEvent();
		problem.setMemo("问题处理流程");
		TSLBaseRiverLakePatrol baseRiverLakePatrol = new TSLBaseRiverLakePatrol();
		baseRiverLakePatrol.setId("4028819668179aa4016817ada7910");
		problem.setTslBaseRiverLakePatrol(baseRiverLakePatrol);
		TSLBaseRiverLake baseRiverLake = new TSLBaseRiverLake();
		baseRiverLake.setCode("A02");
		problem.setTslBaseRiverLake(baseRiverLake);
		problem.setSourceType("10");
		problem.setQuestType("10");
		problem.setEventDate(new Date());
		problem.setEventLng("117.159062");
		problem.setEventLat("34.228785");
		problem.setEventAddress("徐州市金山桥开发区蟠桃山路19号江苏赛立科技有限公司");
		problem.setName("漂浮物");
		problem.setCode("20190816001");
		problem.setMemo("有一平方的漂浮物");
		TSUser user = new TSUser();
		user.setId("8a8ab0b246dc81120146dc8181950052");
		problem.setTsSubmitUser(user);
		problem.setEventStatus("25");
		TSLDivisionEntity division = new TSLDivisionEntity();
		division.setDivisionCode("A01A01");
		problem.setTslDivision(division);
		problem.setLid(3423);
		riverLakePatrolEventService.save(problem);*/

        TSLBaseRiverLakePatrolEvent problem = new TSLBaseRiverLakePatrolEvent();
        problem.setCode(DateUtils.yyyymmddhhmmss.format(new Date()));
        List<DictEntity> list = systemService.queryDict(null, "questType", null);
        DictEntity dict = (DictEntity) list.stream().collect(Collectors.groupingBy(DictEntity::getTypecode)).get("10").get(0);
        problem.setName(dict.getTypename());
        TSLBaseRiverLake baseRiverLake = new TSLBaseRiverLake();
        baseRiverLake.setCode("A01");
        problem.setTslBaseRiverLake(baseRiverLake);
        // 11: 微信平台
        problem.setSourceType("11");
        // 10: 漂浮物
        problem.setQuestType("10");
        // 上报事件
        problem.setEventDate(new Date());
        problem.setEventLng("117.159062");
        problem.setEventLat("34.228785");
        problem.setEventAddress("徐州市金山桥开发区蟠桃山路19号江苏赛立科技有限公司");
        // 问题描述
        problem.setMemo("有一平方的漂浮物");
        TSUser user = new TSUser();
        user.setId("8a8ab0b246dc81120146dc8181950052");
        user.setUserName("admin");
        user.setUserType("1");
        problem.setTsSubmitUser(user);
        TSLDivisionEntity division = new TSLDivisionEntity();
        division.setCode("A01A01");
        problem.setTslDivision(division);

        ProcessStartParam param = new ProcessStartParam();
        param.setProcDefKey(Constants.Activiti.PD_PROBLEM[0]);
        param.setBusinessId(Constants.Activiti.PD_PROBLEM[1]);
        param.setBusinessId(problem.getId());
        param.setTitle(problem.getName());
        param.setStarter(problem.getTsSubmitUser().getId());
        param.setBusiness(problem);
        problemWorkflowService.applyProcess(param);
    }
}
