package com.saili.hzz.app.controller;

import com.saili.hzz.core.domain.act.params.ProcessStartParam;
import com.saili.hzz.act.service.problem.ProblemWorkflowService;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrol;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import com.saili.hzz.core.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * @Title:
 * @Description: 创建此controller时遇到一个坑，当此controller类名称定义为RiverLakeProtalAppController时，不能使用,不知道为什么
 * @author: whuab_mc
 * @date: 2019-08-06 19:39:19:39
 * @version: V1.0
 */
@Controller
@RequestMapping("/app/riverLakeProtal/")
public class RiverLakerProtalAppController {

    @Autowired
    private ProblemWorkflowService problemWorkflowService;

    /**
     * 开始巡河
     * @author whuab_mc
     * @date 2019-08-22 15:08:42
     * @param riverLakePatrol
     * @return
     */
    @RequestMapping("startProtal")
    @ResponseBody
    public String startProtal(TSLBaseRiverLakePatrol riverLakePatrol) {
        String result = "测试河长巡河后台！";
        return result;
    }

    /**
     *  提交问题
     * @author whuab_mc
     * @date 2019-08-22 15:07:40
     * @param problem
     * @return
     */
    @RequestMapping("doSubmitProblem")
    @ResponseBody
    public void doSubmitProblem(TSLBaseRiverLakePatrolEvent problem) {
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
